/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.convert;

import com.github.sttk.cliargs.CliArgs.InvalidOption;
import com.github.sttk.exception.ReasonedException;

/**
 * Is the converter that converts the given option argument string to a long
 * value.
 */
public class LongConverter implements Converter<Long> {

  /**
   * Creates a new instance of this class.
   */
  public LongConverter() {}

  /**
   * Is the exception reason which indicates that the option argument is
   * invalid format as an long value.
   *
   * @param optArg  The option argument string.
   * @param option  The option name.
   * @param storeKey  The store key.
   */
  public record InvalidLongFormat(
    String optArg, String option, String storeKey
  ) implements InvalidOption {}

  /**
   * Converts the given string to the typed option argument.
   *
   * @param optArg  The string to be validated.
   * @param option  The option name.
   * @param storeKey  The store key.
   * @return A converted value.
   * @throws ReasonedException  If failed to convert by the follow reasons:
   *  <ul>
   *   <li>{@link LongConverter.InvalidLongFormat} …
   *     The give string is invalid format as a long.</li>
   *  </ul>
   */
  public Long convert(
    String optArg, String option, String storeKey
  ) throws ReasonedException {
    try {
      return Long.valueOf(optArg);
    } catch (Exception e) {
      var reason = new InvalidLongFormat(optArg, option, storeKey);
      throw new ReasonedException(reason, e);
    }
  }
}
