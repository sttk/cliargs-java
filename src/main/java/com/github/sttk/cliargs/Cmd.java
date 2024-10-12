/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import com.github.sttk.cliargs.annotations.Opt;
import com.github.sttk.cliargs.exceptions.FailToSetOptionStoreField;
import com.github.sttk.cliargs.exceptions.InvalidOption;
import com.github.sttk.cliargs.exceptions.UnconfiguredOption;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Parses command line arguments and stores them.
 * <p>
 * The results of parsing are stored by separating into command name, command arguments, options,
 * and option arguments.
 */
public class Cmd {

  private final String name;
  private List<String> args = Collections.emptyList();
  private Map<String, List<String>> opts = Collections.emptyMap();
  private List<OptCfg> cfgs = Collections.emptyList();;

  private final List<String> osArgs;
  private final boolean isAfterNonOpt;

  /**
   * Constructs an instance of this class with command line arguments.
   *
   * @param name  The command name.
   * @param osArgs  The command line arguments.
   */
  public Cmd(String name, String ...osArgs) {
    this.name = Path.of(name).getFileName().toString();
    this.osArgs = List.of(osArgs);
    this.isAfterNonOpt = false;
  }

  Cmd(String name, List<String> osArgs, boolean isAfterNonOpt) {
    this.name = name;
    this.osArgs = osArgs;
    this.isAfterNonOpt = isAfterNonOpt;
  }

  /**
   * Returns the command name.
   * <p>
   * This name is the base name extracted from the command path string, which is the first
   * element of the command line arguments.
   *
   * @return  The command name.
   */
  public String name() {
    return this.name;
  }

  /**
   * Returns the command arguments.
   *
   * @return  The command arguments.
   */
  public List<String> args() {
    return this.args;
  }

  /**
   * Checks whether an option with the specified name exists.
   *
   * @param name  The option name.
   * @return  True, if the option exists.
   */
  public boolean hasOpt(String name) {
    return this.opts.containsKey(name);
  }

  /**
   * Returns the option argument with the specified name.
   * <p>
   * If the option has multiple arguments, this method returns the first argument.
   * If the option is a boolean flag, this method returns <i>empty</i> of {@link Optional}.
   * If the option is not specified in the command line arguments, the return value of this method
   * is <i>empty</i> of {@link Optional}.
   *
   * @param name  The option name.
   * @return  An {@link Optional} object which may contain the first option argument.
   */
  public Optional<String> optArg(String name) {
    var list = this.opts.get(name);
    if (list == null || list.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(list.get(0));
  }

  /**
   * Returns the option arguments with the specified name.
   * <p>
   * If the option has one or multiple arguments, this method returns an array of the arguments.
   * If the option is a boolean flag, this method returns an {@link Optional} including an empty
   * list.
   * If the option is not specified in the command line arguments, the return value of this method
   * is <i>empty</i> of {@link Optional}.
   *
   * @param name  The option name.
   * @return  An {@link Optional} object which may contain the option arguments.
   */
  public Optional<List<String>> optArgs(String name) {
    var list = this.opts.get(name);
    if (list == null) {
      return Optional.empty();
    }
    return Optional.of(list);
  }

  /**
   * Returns the option configurations which was used to parse command line arguments.
   *
   * @return  The option configurations.
   */
  public List<OptCfg> optCfgs() {
    return this.cfgs;
  }

  /**
   * Returns a {@code String} object representing the content of this object.
   *
   * @return A stirng representation of the content of this object.
   */
  @Override
  public String toString() {
    return new StringJoiner(", ", "Cmd{", "}")
      .add("name=" + this.name)
      .add("args=" + this.args)
      .add("opts=" + this.opts)
      .toString();
  }

  /**
   * Parses command line arguments without configurations.
   * <p>
   * This method divides command line arguments into options and command arguments based on
   * simple rules that are almost the same as POSIX &amp; GNU:
   * arguments staring with {@code -} or {@code --} are treated as options, and others are treated
   * as command arguments.
   * <p>
   * If an {@code =} is found within an option, the part before the {@code =} is treated as the
   * option name, and the part after the {@code =} is treated as the option argument.
   * Options starting with {@code --} are long options and option starting with {@code -} are short
   * options.
   * Multiple short options can be concatenated into a single command line argument.
   * If an argument is exactly {@code --}, all subsequent arguments are treated as command
   * arguments.
   * <p>
   * The results of parsing are stored into this {@code Cmd} instance.
   * If it is failed to parsing, a {@link InvalidOption} exception is thrown.
   *
   * @throws InvalidOption  If failed to parsing command line arguments.
   */
  public void parse() throws InvalidOption {
    var parser = new Parse(this.isAfterNonOpt);
    try {
      parser.parse(this.osArgs);
    } finally {
      this.args = unmodifiableList(parser.args);

      for (var ent : parser.opts.entrySet()) {
        ent.setValue(unmodifiableList(ent.getValue()));
      }
      this.opts = unmodifiableMap(parser.opts);
    }
  }

  /**
   * Parses command line arguments without configurations but stops parsing when encountering
   * first command argument.
   *
   * This method creates and returns a new {@code Cmd} instance that holds the command line
   * arguments starting from the first command argument.
   *
   * This method parses command line arguments in the same way as the {@link Cmd#parse} method,
   * except that it only parses the command line arguments before the first command argument.
   *
   * @return A {@code Cmd} instance which holds commnd line arguments after a sub command
   *   (optional).
   * @throws InvalidOption  If failed to parsing command line arguments.
   */
  public Optional<Cmd> parseUntilSubCmd() throws InvalidOption {
    var parser = new Parse(this.isAfterNonOpt);
    try {
      return parser.parseUntilSubCmd(this.osArgs);
    } finally {
      this.args = unmodifiableList(parser.args);

      for (var ent : parser.opts.entrySet()) {
        ent.setValue(unmodifiableList(ent.getValue()));
      }
      this.opts = unmodifiableMap(parser.opts);
    }
  }

  /**
   * Parses command line arguments with option configurations.
   *
   * This method divides command line arguments to command arguments and options.
   * And an option consists of a name and an option argument.
   * Options are divided to long format options and short format options.
   * About long/short format options, since they are same with {@code parse} method, see the
   * comment of that method.
   *
   * This method allows only options declared in option configurations, basically.
   * An option configuration has fields: {@code storeKey}, {@code names}, {@code hasArg},
   * {@code isArray}, {@code defaults}, {@code desc}, {@code argInHelp}, and {@code validator}.
   *
   * When an option matches one of the {@code names} in the option configurations, the option is
   * registered into {@code Cmd} with {@code storeKey}.
   * If both {@code hasArg} and {@code isArray} are true, the option can have one or multiple
   * option arguments, and if {@code hasArg} is true and {@code isArray} is false, the option can
   * have only one option argument, otherwise the option cannot have option arguments.
   * If {@code defaults} field is specified and no option value is given in command line arguments,
   * the value of {@code defaults} is set as the option arguments.
   *
   * If options not declared in option configurations are given in command line arguments, this
   * method basicaly throws {@link UnconfiguredOption} exception.
   * However, if you want to allow other ooptions, add an option configuration of which
   * {@code storeKey} or the first element of {@code names} is {@code "*"}.
   *
   * The option configurations used to parsing are set into the {@link Cmd} instance, and it can be
   * retrieved with its method: {@link Cmd#optCfgs}.
   *
   * @param optCfgs The array of the option configurations.
   * @throws InvalidOption  If failed to parsing command line arguments.
   */
  public void parseWith(OptCfg[] optCfgs) throws InvalidOption {
    var parser = new ParseWith(this.isAfterNonOpt, false);
    try {
      parser.parseArgsWith(this.osArgs, optCfgs);
    } finally {
      this.args = unmodifiableList(parser.args);

      for (var ent : parser.opts.entrySet()) {
        ent.setValue(unmodifiableList(ent.getValue()));
      }
      this.opts = unmodifiableMap(parser.opts);

      this.cfgs = List.of(optCfgs);
    }
  }

  /**
   * Parses command line arguments with option configurations but stops parsing when encountering
   * first command argument.
   *
   * This method creates and returns a new {@code Cmd} instance that holds the command line
   * arguments starting from the first command argument.
   *
   * This method parses command line arguments in the same way as the {@link Cmd#parseWith} method,
   * except that it only parses the command line arguments before the first command argument.
   *
   * The option configurations used to parsing are set into the {@link Cmd} instance, and it can be
   * retrieved with its method: {@link Cmd#optCfgs}.
   *
   * @param optCfgs The array of the option configurations.
   * @return A {@code Cmd} instance which holds commnd line arguments after a sub command
   *   (optional).
   * @throws InvalidOption  If failed to parsing command line arguments.
   */
  public Optional<Cmd> parseUntilSubCmdWith(OptCfg[] optCfgs) throws InvalidOption {
    var parser = new ParseWith(this.isAfterNonOpt, true);
    try {
      var idx = parser.parseArgsWith(this.osArgs, optCfgs);
      if (idx.isPresent()) {
        boolean isAfterNonOpt = (idx.get() < 0);
        int i = Math.abs(idx.get());
        int n = this.osArgs.size();
        return Optional.of(
          new Cmd(this.osArgs.get(i), this.osArgs.subList(i + 1, n), isAfterNonOpt));
      }
      return Optional.empty();
    } finally {
      this.args = unmodifiableList(parser.args);

      for (var ent : parser.opts.entrySet()) {
        ent.setValue(unmodifiableList(ent.getValue()));
      }
      this.opts = unmodifiableMap(parser.opts);

      this.cfgs = List.of(optCfgs);
    }
  }

  /**
   * Parses command line arguments and set their option values to the fields of the option store
   * which is passed as the argument of this method.
   * <p>
   * This method divides command line arguments to command arguments and options, then sets each
   * option value to a corresponding field of the option store.
   * <p>
   * Within this method, an array of {@link OptCfg} is made from the fields of the option store.
   * This {@link OptCfg} array is set into this {@link Cmd} instance.
   * If you want to access this option configurations, get them by {@link Cmd#optCfgs} method.
   * <p>
   * An option configuration corresponding to each field of an option store is determined by its
   * type and {@link Opt} field annotation.
   * If the type is bool, the option takes no argument.
   * If the type is integer, floating point number or string, the option can takes single option
   * argument, therefore it can appear once in command line arguments.
   * If the type is an array, the option can takes multiple option arguments, therefore it can
   * appear multiple times in command line arguments.
   * <p>
   * A {@link Opt} field annotation can have the following pairs of name and value: one is {@code
   * cfg} to specify {@code names} and {@code defaults} fields of {@link OptCfg} struct, another
   * is {@code desc} to specify {@code desc} field, and yet another is {@code arg} to specify
   * {@code argInHelp} field.
   * <p>
   * The format of {@code cfg} is like {@code cfg="f,foo=123"}.
   * the left side of the equal sign is the option name(s), and the right side is the default
   * value(s).
   * If there is no equal sign, it is determined that only the option name is specified.
   * If you want to specify multiple option names, separate them with commas.
   * If you want to specify multiple default values, separate them with commas and round them
   * with square brackets, like {@code [1,2,3]}.
   * If you want to use your favorite character as a separator, you can use it by putting it
   * on the left side of the open square bracket, like {@code /[1/2/3]}.
   * <p>
   * NOTE: A default value of empty string array option in a field attribute is {@code []}, like
   * {@code @Opt(cfg="=[]")}, but it doesn't represent an array which contains only one empty
   * string.
   * If you want to specify an array which contains only one empty string, write nothing after
   * {@code =} symbol, like {@code Opt(cfg="=")}.
   *
   * @param optStore  An object store.
   * @throws InvalidOption  If failed to parsing command line arguments.
   * @throws FailToSetOptionStoreField  If failed to set a field value of an object store.
   */
  public void parseFor(Object optStore) throws InvalidOption, FailToSetOptionStoreField {
    var cfgs = OptCfg.makeOptCfgsFor(optStore);
    parseWith(cfgs);
    ParseFor.setOptionStoreFieldValues(optStore, cfgs, this.opts);
  }

  /**
   * Parses command line arguments until the first command argument and set their option values
   * to the option store which is passed as an argument.
   *
   * This method creates and returns a new {@link Cmd} instance that holds the command line
   * arguments starting from the first command argument.
   *
   * This method parses command line arguments in the same way as the {@link Cmd#parseFor} method,
   * except that it only parses the command line argments before the first command argument.
   *
   * @param optStore  An object store.
   * @return A {@code Cmd} instance which holds commnd line arguments after a sub command
   *   (optional).
   * @throws InvalidOption  If failed to parsing command line arguments.
   * @throws FailToSetOptionStoreField  If failed to set a field value of an object store.
   */
  public Optional<Cmd> parseUntilSubCmdFor(Object optStore)
    throws InvalidOption, FailToSetOptionStoreField
  {
    var cfgs = OptCfg.makeOptCfgsFor(optStore);
    var optional = parseUntilSubCmdWith(cfgs);
    ParseFor.setOptionStoreFieldValues(optStore, cfgs, this.opts);
    return optional;
  }
}
