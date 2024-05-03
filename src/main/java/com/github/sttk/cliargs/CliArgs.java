/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import com.github.sttk.cliargs.annotation.Opt;
import com.github.sttk.exception.ReasonedException;
import java.nio.file.Path;
import java.util.List;

/**
 * Provides the static methods to parse and print command line arguments.
 */
public class CliArgs {

  /// Exception reasons on parsing command line arguments.

  /**
   * Is implemented by records that indicate exception reasons, and provides a
   * method to retrieve an option name.
   */
  public interface InvalidOption {
    /**
     * Gets the option name.
     *
     * @return  The option name.
     */
    String option();
  }

  /**
   * Is the exception reason which indicates that an invalid character is found
   * in an option.
   *
   * @param option  An option name.
   */
  public record OptionHasInvalidChar(String option)
    implements InvalidOption {}

  /**
   * Is the exception reason which indicates that there is no configuration
   * about the input option.
   *
   * @param option  An option name.
   */
  public record UnconfiguredOption(String option)
    implements InvalidOption {}

  /**
   * Is the exception reason which indicates that an option is input with no
   * option argument though its option configuration requires option argument
   * ({@code .hasArg == true}).
   *
   * @param option  An option name.
   * @param storeKey  A store key.
   */
  public record OptionNeedsArg(String option, String storeKey)
    implements InvalidOption {}

  /**
   * Is the exception reason which indicates that an option is input with an
   * option argument though its option configuration does not accept option
   * arguments ({@code .hasArg == false}).
   *
   * @param option  An option name.
   * @param storeKey  A store key.
   * @param optArg  An option argument.
   */
  public record OptionTakesNoArg(String option, String storeKey, String optArg)
    implements InvalidOption {}

  /**
   * Is the exception reason which indicates that an option is input with an
   * option argument multiple times though its option configuration specifies
   * the option is not an array ({@code .isArray == false}).
   *
   * @param option  An option name.
   * @param storeKey  A store key.
   * @param optArg  An option argument.
   */
  public record OptionIsNotArray(String option, String storeKey, String optArg)
    implements InvalidOption {}

  /**
   * Is the exception reason which indicates that an option argument is failed
   * to convert the specified type value.
   *
   * @param optArg  An option argument.
   * @param option  An option name.
   * @param storeKey  A store key.
   */
  public record FailToConvertOptionArg(
    String optArg, String option, String storeKey
  ) implements InvalidOption {}

  /// Exception reasones for option configurations.

  /**
   * Is the exception reason which indicates that a store key in an option
   * configuration is duplicated another among all option configurations.
   *
   * @param storeKey  A store key.
   */
  public record StoreKeyIsDuplicated(String storeKey) {}

  /**
   * Is the exception reason which indicates that an option configuration
   * contradicts that the option is an array ({@code .isArray == true})
   * though it has no option argument ({@code .hasArg == false}).
   *
   * @param storeKey  A store key.
   */
  public record ConfigIsArrayButHasNoArg(String storeKey) {}

  /**
   * Is the exception reason which indicates that an option configuration
   * contradicts that the option is not an array ({@code .isArray == false})
   * but default value ({@code .defaults} is an array.
   *
   * @param storeKey  A store key.
   */
  public record ConfigIsNotArrayButDefaultsIsArray(String storeKey) {}

  /**
   * Is the exception reason which indicates that an opiton configuration
   * contradicts that the option has default value(s)
   * ({@code .defaults != null}) though it has no option argument
   * ({@code .hasArg == false}).
   *
   * @param storeKey  A store key.
   */
  public record ConfigHasDefaultsButHasNoArg(String storeKey) {}

  /**
   * Is the exception reason which indicates that an option name in
   * {@code .names} field is duplicated another among all option
   * configurations.
   *
   * @param storeKey  A store key.
   * @param option  An option name.
   */
  public record OptionNameIsDuplicated(String storeKey, String option) {}

  /**
   * Is the exception reason which indicates that default value(s) defined in
   * {@link Opt} annotation is failed to convert to the specified type value.
   *
   * @param storeKey  A store key.
   * @param optArg  An option argument to be converted.
   */
  public record FailToConvertDefaultsInOptAnnotation(
    String storeKey, String optArg
  ) {}

  /**
   * Is the exception reason which indicates that a type of a field of the
   * option store is neither a boolean, a number, a string, or an array of
   * numbers or strings.
   *
   * @param type  The field type of the option store object.
   * @param field  The field name of the option store object.
   */
  public record IllegalOptionType(Class<?> type, String field) {}

  /**
   * Is the exception reason which indicates that it is failed to set option
   * arguments (including those from default values) to a field of an option
   * store.
   *
   * @param field  The field name of the option store object.
   * @param type  The field type of the option store object.
   * @param optArgs  The option arguments.
   */
  public record FailToSetOptionStoreField(
    String field, Class<?> type, List<?> optArgs
  ) {}

  ///

  /** The command name. */
  private final String cmd;

  /** The command line arguments. */
  private final String[] args;

  /**
   * Is the constructor that takes a command name and command line arguments.
   *
   * @param cmd  A command name.
   * @param args  Command line arguments.
   */
  public CliArgs(String cmd, String ...args) {
    this.cmd = cmd;
    this.args = args;
  }

  /**
   * Parses command line arguments without option configurations.
   * <p>
   * This method divides command line arguments to command arguments, which are
   * not associated with any options, and options, of which each has a name and
   * option arguments.
   * If an option appears multiple times in command line arguments, the option
   * has multiple option arguments.
   * Options are divided to long format options and short format options.
   * <p>
   * A long format option starts which {@code "--"} and follows multiple
   * characters which consists of alphabets, numbers, and {@code '-'}.
   * (A character immediately after the heading {@code "--"} allows only an
   * alphabets.)
   * <p>
   * A short format option starts with {@code "-"} and follows single character
   * which is an alphabet.
   * Multiple short options can be combined into one argument.
   * (For example {@code -a -b -c} can be combined into {@code -abc}.)
   * Moreover, a short option can be followed by {@code "="} and its option
   * argument.
   * In case of combined short options, only the last short option can take an
   * option argument.
   * (For example, {@code -abc=3} is equal to {@code -a -b -c=3}.)
   *
   * @return A {@link Result} object that contains the parsed result.
   */
  public Result parse() {
    return Parse.parse(this.cmd, this.args);
  }

  /**
   * Parses command line arguments with option configurations.
   * <p>
   * This method divides command line arguments to command arguments and
   * options.
   * And an option consists of a name and an option argument.
   * Options are divided to long format options and short format options.
   * About long/short format options, since they are same with {@link parse}
   * method, see the comment of that method.
   * <p>
   * This method basically allows only options declared in option
   * configurations.
   * When an option in command line arguments matches one of the {@code names}
   * in an option configuration, the option is registered into {@code Cmd} with
   * {@code storeKey}.
   * If both {@code hasArg} and {@code isArray} are true, the option can have
   * one or multiple option arguments, and if {@code hasArg} is true and
   * {@code isArray} is false, the option can have only one option argument,
   * otherwise the option cannot have option arguments.
   * If {@code defaults} is specified and no option argument is give in command
   * line arguments, the option argument(s) is set to the value(s) of
   * {@code defaults}.
   * If {@code converter} is specified and option argument(s) is given in
   * command line arguments, the value or each of option argument(s) are
   * converted with the {@code converter}.
   * If {@code type} is specified but {@code converter} is not specified,
   * a converter which converts a string to the specified type value is set.
   * If {@code postparser} is specified, it processes option arguments
   * (including those set from default values) after parsing.
   * <p>
   * If options not declared in option configurations are given in command line
   * arguments, this method basically throws a {@link ReasonedException} with
   * the reason {@code UnconfiguredOption}.
   * However, if you want to allow other options, add an option configuration
   * of which {@code storeKey} or the first element of {@code names} is
   * {@code "*"}.
   *
   * @param optCfgs  An array of {@link OptCfg} objects.
   * @return  A {@link Result} object that contains the parsed result.
   */
  public Result parseWith(OptCfg[] optCfgs) {
    return ParseWith.parse(optCfgs, this.cmd, this.args);
  }

  /**
   * Parses command line arguments and sets their values to the option store
   * which is the second argument of this method.
   * This method divides command line arguments to command arguments and
   * options, then sets the options to the option store, and returns the
   * command arguments with the generated option configurations.
   * <p>
   * The configurations of options are determined by types and annotations of
   * fields of the option store.
   * If the type is a boolean, the option takes no argument.
   * If the type is a number or a string, the option can takes single option
   * argument, therefore it can appear once in command line arguments.
   * If the type is an array, the option can takes multiple option arguments,
   * therefore it can appear multiple times in command line arguments.
   * <p>
   * The annotation type is @{@link Opt} and it can have annotation elements:
   * {@code cfg}, {@code desc}, and {@code arg}.
   * The {@code cfg} element can be specified names and default values(s) of
   * an option.
   * It has a special format like {@code cfg="foo-bar,f=123"}.
   * The first part of the element value is option names, which are separated
   * by commas, and ends with {@code "="} mark or end of string.
   * If the option name is empty or no {@code cfg} element value, the option's
   * name becomes same with the field name of the option store.
   * <p>
   * The string after the {@code "="} mark is default value(s).
   * If the type of the option is a boolean, the string after {@code "="} mark
   * is ignored because a boolean option takes no option argument.
   * If the type of the option is a number or a string, the whole string after
   * {@code "="} mark is default value(s).
   * If the type of the option is an array, the string after {@code "="} mark
   * have to be rounded by square brackets and separate the elements with
   * commas, like {@code [elem1,elem2,elem3]}.
   * The element separator can be used other than a comma by putting the
   * separator chracter before the open square bracket, like
   * {@code :[elem1:elem2:elem3]}.
   * It's useful when some array elements include commas.
   * <p>
   * <b>NOTE:</b> A default value of an empty array in {@code cfg} annotation
   * element is {@code []}, like {@code cfg="name=[]"}, it doesn't
   * represent an array which contains only one empty string.
   * If you want to specify an array which contains only one empty string,
   * write nothing after "=" mark, like {@code cfg="name="}.
   *
   * @param options  An option store object.
   * @return A {@link Result} object that contains the parsed result.
   */
  public Result parseFor(Object options) {
    OptCfg[] cfgs;
    try {
      cfgs = ParseFor.makeOptCfgsFor(options);
    } catch (ReasonedException e) {
      var cmdName = Path.of(this.cmd).getFileName().toString();
      var cmd = new Cmd(cmdName, emptyList(), emptyMap());
      return new Result(cmd, null, e);
    }

    return ParseWith.parse(cfgs, this.cmd, this.args);
  }

  /**
   * Makes an {@link OptCfg} array from the fields of the option store with the
   * annnotation @{@link Opt}.
   * <p>
   * About the process for {@link OptCfg} using {@link Opt} annotation, see
   * the comment of {@link parseFor} method.
   *
   * @param options  An option store object.
   * @return An {@link OptCfg} array.
   * @throws ReasonedException  If it is failed to create OptCfg objects from
   *     the fields of the option store object.
   */
  public static OptCfg[] makeOptCfgsFor(
    Object options
  ) throws ReasonedException {
    return ParseFor.makeOptCfgsFor(options);
  }
}
