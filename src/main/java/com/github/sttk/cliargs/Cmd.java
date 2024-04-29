/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import java.util.List;
import java.util.Map;

/**
 * Is the class which contains a command name, command arguments, and option
 * arguments that are parsed from command line arguments without
 * configurations.
 * And this provides methods to check if they are specified and to option them.
 */
public class Cmd {
  private final String name;
  private final List<String> args;
  private final Map<String, List<?>> opts;

  /**
   * Is the constructor which takes the fields of this class.
   *
   * @param name A command name.
   * @param args Command arguments.
   * @param opts Mappings between option names and option arguments.
   */
  public Cmd(String name, List<String> args, Map<String, List<?>> opts) {
    this.name = name;
    this.args = unmodifiableList(args);
    this.opts = unmodifiableMap(opts);
  }

  /**
   * Gets the command name.
   *
   * @return The command name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the command arguments.
   *
   * @return The command arguments.
   */
  public List<String> getArgs() {
    return this.args;
  }

  /**
   * Checks if the option is specified in command line arguments.
   *
   * @param name The option name.
   * @return True, if the option is specified.
   */
  public boolean hasOpt(String name) {
    return this.opts.containsKey(name);
  }

  /**
   * Gets the first option argument of the specified named option.
   * <p>
   * If there is no option argument for the specified option, this method
   * returns null.
   *
   * @param <T>  The type of of option argument value.
   *
   * @param name The option name.
   * @return The first option argument of the specified option.
   */
  public <T> T getOptArg(String name) {
    var list = this.opts.get(name);
    if (isEmpty(list)) {
      return null;
    }
    @SuppressWarnings("unchecked")
    T t = (T) list.get(0);
    return t;
  }

  /**
   * Gets the option arguments of the specified named option.
   * <p>
   * If there is no option argument for the specified option, this method
   * returns an empty list.
   *
   * @param <T>  The type of of option argument list.
   *
   * @param name The option name.
   * @return The option arguments of the specified option.
   */
  public <T extends List> T getOptArgs(String name) {
    var list = this.opts.get(name);
    if (isEmpty(list)) {
      @SuppressWarnings("unchecked")
      T t = (T) emptyList();
      return t;
    }
    @SuppressWarnings("unchecked")
    T t = (T) unmodifiableList(list);
    return t;
  }
}
