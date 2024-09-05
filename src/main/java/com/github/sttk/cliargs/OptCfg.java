/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static com.github.sttk.cliargs.Base.isEmpty;
import static com.github.sttk.cliargs.Base.isBlank;

import com.github.sttk.cliargs.annotations.Opt;
import com.github.sttk.cliargs.validators.Validator;
import com.github.sttk.cliargs.exceptions.InvalidOption;
import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;
import com.github.sttk.cliargs.exceptions.FailToSetOptionStoreField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Represents an option configuration for how to parse command line arguments.
 *
 * And this is also used when creating the help text for command line arguments.
 */
public class OptCfg {

  /**
   * Is the key to store option value(s) in the option map in a `Cmd` instance.
   * <p>
   * If this key is not specified or empty, the first element of the `names` field is used
   * instead.
   */
  public final String storeKey;

  /**
   * Is the vector for specifying the option name and the aliases.
   * <p>
   * The order of the `names` in this array are used in a help text.
   */
  public final List<String> names;

  /**
   * Is the flag which allow the option to take option arguments.
   */
  public final boolean hasArg;

  /**
   * Is the flag which allow the option to take multiple option arguments.
   */
  public final boolean isArray;

  /**
   * Is the {@link Optional} object of a string list to specify default value(s) for when the
   * command option is not given in command line arguments.
   * <p>
   * If this value is <i>empty</i> of the {@link Optional}, the default value(s) is not specified.
   */
  public final Optional<List<String>> defaults;

  /**
   * Is the string field to set the description of the option which is used in a help text.
   */
  public final String desc;

  /**
   * Is the field to set a display string of the option argument(s) in a help text.
   * <p>
   * An example of the display is like: {@code -o, --option <value>}.
   */
  public final String argInHelp;

  /**
   * Is the functional interface to validate the option argument(s).
   * <p>
   * If the option argument is invalid, this method throws a {@link OptionArgIsInvalid} exception.
   */
  public final Validator validator;

  final Field field;

  /**
   * Returns the content string of this instance.
   *
   * @return  The content string of this instance.
   */
  public String toString() {
    return new StringJoiner(", ", "OptCfg{", "}")
      .add("storeKey=" + this.storeKey)
      .add("names=" + this.names)
      .add("hasArg=" + this.hasArg)
      .add("isArray=" + this.isArray)
      .add("defaults=" + this.defaults)
      .add("desc=" + this.desc)
      .add("argInHelp=" + this.argInHelp)
      .toString();
  }

  OptCfg(
    String storeKey,
    List<String> names,
    boolean hasArg,
    boolean isArray,
    Optional<List<String>> defaults,
    String desc,
    String argInHelp,
    Validator validator,
    Field field
  ) {
    this.storeKey = storeKey;
    this.names = names;
    this.hasArg = hasArg;
    this.isArray = isArray;
    this.defaults = defaults;
    this.desc = desc;
    this.argInHelp = argInHelp;
    this.validator = validator;
    this.field = field;
  }

  /**
   * Is the constructor that takes the all field values as parameters.
   * <p>
   * If {@code storeKey} is empty, it is set to first element of {@code names}.
   * If {@code names} is empty or it's first element is empty, the first element is set to the
   * value of {@code storeKey}.
   * <p>
   * If {@code type} is not null but {@code hasArg} is false, {@code hasArg} is set to true
   * forcely.
   * Also, {@code type} is not null but {@code converter} is null, {@code converter} is set to a
   * converter which convert a string to a specified type value.
   *
   * @param storeKey  The store key.
   * @param names  The option name and aliases.
   * @param hasArg  True, if this option can take option arguments.
   * @param isArray  True, if this option can take one or multiple option arguments.
   * @param defaults  The default value(s).
   * @param desc  The description of the option.
   * @param argInHelp  The display of the option argument.
   * @param validator The validator to validate the option argument.
   */
  public OptCfg(
    String storeKey,
    List<String> names,
    boolean hasArg,
    boolean isArray,
    List<String> defaults,
    String desc,
    String argInHelp,
    Validator validator
  ) {
    var init = new Init();
    init.storeKey = storeKey;
    init.names = names;
    init.hasArg = hasArg;
    init.isArray = isArray;
    init.defaults = defaults;
    init.desc = desc;
    init.argInHelp = argInHelp;
    init.validator = validator;

    fillMissing(init);

    this.storeKey = init.storeKey;
    this.names = unmodifiableList(init.names);
    this.hasArg = init.hasArg;
    this.isArray = init.isArray;
    if (init.defaults == null) {
      this.defaults = Optional.empty();
    } else {
      this.defaults = Optional.of(unmodifiableList(init.defaults));
    }
    this.desc = init.desc;
    this.argInHelp = init.argInHelp;
    this.validator = validator;
    this.field = null;
  }

  /**
   * Is the constructor that takes the variadic parameters of {@link Param} which can be specified
   * like named parameters.
   * <p>
   * If {@code storeKey} is empty, it is set to first element of {@code names}.
   * If {@code names} is empty or it's first element is empty, the first element is set to the
   * value of {@code storeKey}.
   * <p>
   * If {@code type} is not null but {@code hasArg} is false, {@code hasArg} is set to true
   * forcely.
   * Also, {@code type} is not null but {@code converter} is null, {@code converter} is set to a
   * converter which convert a string to a specified type value.
   *
   * @param params  The variadic parameters of {@link Param}.
   */
  public OptCfg(Param ...params) {
    var init = new Init();
    for (var param : params) {
      param.setTo(init);
    }

    fillMissing(init);

    this.storeKey = init.storeKey;
    this.names = unmodifiableList(init.names);
    this.hasArg = init.hasArg;
    this.isArray = init.isArray;
    if (init.defaults == null) {
      this.defaults = Optional.empty();
    } else {
      this.defaults = Optional.of(unmodifiableList(init.defaults));
    }
    this.desc = init.desc;
    this.argInHelp = init.argInHelp;
    this.validator = init.validator;
    this.field = null;
  }

  private void fillMissing(Init init) {
    if (init.storeKey == null) {
      init.storeKey = "";
    }

    if (init.names == null) {
      init.names = emptyList();
    }

    if (init.desc == null) {
      init.desc = "";
    }

    if (init.argInHelp == null) {
      init.argInHelp = "";
    }
  }

  private static class Init {
    String storeKey;
    List<String> names;
    boolean hasArg;
    boolean isArray;
    List<String> defaults;
    String desc;
    String argInHelp;
    Validator validator;
  }

  /**
   * Is the functional interface for the constructor parameters like named parameters.
   */
  @FunctionalInterface
  public static interface Param {
    /**
     * Sets a field value of {@link Init} object that has same fields with {@link OptCfg} object
     * and is used to initialized it.
     *
     * @param init  An object to initialize {@link OptCfg} object.
     */
    void setTo(Object init);

    /**
     * Is the static method to set the {@code storeKey} field like a named parameter.
     *
     * @param storeKey  The value of the {@code storeKey} field.
     * @return  The {@link Param} object to set the {@code storeKey} field of the {@link OptCfg}
     *   instance.
     */
    static Param storeKey(String storeKey) {
      return init -> Init.class.cast(init).storeKey = storeKey;
    }

    /**
     * Is the static method to set the {@code names} field like a named parameter.
     *
     * @param names  The string array of the {@code names} field.
     * @return  The {@link names} object to set the {@code names} field of the {@link OptCfg}
     *   instance.
     */
    static Param names(String ...names) {
      return init -> Init.class.cast(init).names = List.of(names);
    }

    /**
     * Is the static method to set the {@code names} field like a named parameter.
     *
     * @param names  The string list of the {@code names} field.
     * @return  The {@link names} object to set the {@code names} field of the {@link OptCfg}
     *   instance.
     */
    static Param names(List<String> names) {
      return init -> Init.class.cast(init).names = names;
    }

    /**
     * Is the static method to set the {@code hasArg} field like a named parameter.
     *
     * @param hasArg  The value of the {@code hasArg} field.
     * @return  The {@link Param} object to set the {@code hasArg} field of the {@link OptCfg}
     *   instance.
     */
    static Param hasArg(boolean hasArg) {
      return init -> Init.class.cast(init).hasArg = hasArg;
    }

    /**
     * Is the static method to set the {@code isArray} field like a named parameter.
     *
     * @param isArray  The value of the {@code isArray} field.
     * @return  The {@link Param} object to set the {@code isArray} field of the {@link OptCfg}
     *   instance.
     */
    static Param isArray(boolean isArray) {
      return init -> Init.class.cast(init).isArray = isArray;
    }

    /**
     * Is the static method to set the {@code defaults} field like a named parameter.
     *
     * @param defaults  The string array of the {@code defaults} field.
     * @return  The {@link Param} object to set the {@code defaults} field of the {@link OptCfg}
     *   instance.
     */
    static Param defaults(String ...defaults) {
      return init -> Init.class.cast(init).defaults = List.of(defaults);
    }

    /**
     * Is the static method to set the {@code defaults} field like a named parameter.
     *
     * @param defaults  The string list of the {@code defaults} field.
     * @return  The {@link Param} object to set the {@code defaults} field of the {@link OptCfg}
     *   instance.
     */
    static Param defaults(List<String> defaults) {
      return init -> Init.class.cast(init).defaults = defaults;
    }

    /**
     * Is the static method to set the {@code desc} field like a named parameter.
     *
     * @param desc  The value of the {@code desc} field.
     * @return  The {@link Param} object to set the {@code desc} field of the {@link OptCfg}
     *   instance.
     */
    static Param desc(String desc) {
      return init -> Init.class.cast(init).desc = desc;
    }

    /**
     * Is the static method to set the {@code argInHelp} field like a named parameter.
     *
     * @param argInHelp  The value of the {@code argInHelp} field.
     * @return  The {@link Param} object to set the {@code argInHelp} field of the {@link OptCfg}
     *   instance.
     */
    static Param argInHelp(String argInHelp) {
      return init -> Init.class.cast(init).argInHelp = argInHelp;
    }

    /**
     * Is the static method to set the {@code validator} field like a named parameter.
     *
     * @param validator  A {@link Validator} object.
     * @return  The {@link Param} object to set the {@code validator} field of the {@link OptCfg}
     *   instance.
     */
    static Param validator(Validator validator) {
      return init -> Init.class.cast(init).validator = validator;
    }
  }

  /**
   * Is the functional interface to process option arguments when the option has been parsed.
   */
  @FunctionalInterface
  public interface OnParsed {
    /**
     * Processes the option arguments.
     *
     * @param optArgs  The list of the option arguments.
     * @throws FailToSetOptionStoreField  If failed to set the field value of the option store.
     */
    void process(List<String> optArgs) throws FailToSetOptionStoreField;
  }

  /**
   * Makes an {@link OptCfg} array from the fields opt the option store with the annotation
   * {@link Opt}.
   * <p>
   * About the process for {@link OptCfg} using {@link Opt} annotation, see the comment of
   * {@link Cmd#parseFor} method.
   *
   * @param optStore An option store object.
   * @return  An {@link OptCfg} array.
   */
  public static OptCfg[] makeOptCfgsFor(Object optStore) {
    return ParseFor.makeOptCfgsFor(optStore);
  }
}
