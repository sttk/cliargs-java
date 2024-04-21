/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

/**
 * Provides the static methods to parse and print command line arguments.
 */
public class CliArgs {

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
   * which is {@code "-"} and follows single character which is an alphabet.
   * Multiple short options can be combined into one argument.
   * (For example {@code -a -b -c} can be combined into {@code -abc}.)
   * Moreover, a short option can be followed by {@code "="} and its option
   * argument.
   * In case of combined short options, only the last short option can take an
   * option argument.
   * (For example, {@code -abc=3} is equal to {@code -a -b -c=3}.)
   *
   * @return A {@link Cmd} object that contains the parsed result.
   */
  public Result parse() {
    return Parse.parse(this.cmd, this.args);
  }
}
