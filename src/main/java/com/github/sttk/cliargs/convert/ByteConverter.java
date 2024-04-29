/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.convert;

import com.github.sttk.cliargs.CliArgs.InvalidOption;
import com.github.sttk.reasonedexception.ReasonedException;

/**
 * Is the converter that converts the given option argument string to a byte
 * value.
 */
public class ByteConverter implements Converter<Byte> {

  /**
   * Creates a new instance of this class.
   */
  public ByteConverter() {}

  /**
   * Is the exception reason which indicates that the option argument is
   * invalid format as a byte.
   *
   * @param optArg  The option argument string.
   * @param option  The option name.
   * @param storeKey  The store key.
   */
  public record InvalidByteFormat(
    String optArg, String option, String storeKey
  ) implements InvalidOption {}

  /**
   * Converts the given string to the typed option argument.
   *
   * @param optArg  The string to be validated.
   * @param option  The option name.
   * @param storeKey  The store key.
   * @return  A converted value.
   * @throws ReasonedException  If failed to convert by the follow reasons:
   *  <ul>
   *   <li>{@link ByteConverter.InvalidByteFormat} …
   *     The give string is invalid format as a byte.</li>
   *  </ul>
   */
  public Byte convert(
    String optArg, String option, String storeKey
  ) throws ReasonedException {
    try {
      return Byte.valueOf(optArg);
    } catch (Exception e) {
      var reason = new InvalidByteFormat(optArg, option, storeKey);
      throw new ReasonedException(reason, e);
    }
  }
}
