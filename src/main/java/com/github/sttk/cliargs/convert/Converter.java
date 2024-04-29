/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.convert;

import com.github.sttk.cliargs.CliArgs.InvalidOption;
import com.github.sttk.reasonedexception.ReasonedException;

/**
 * Is the functional interface to convert a given string to the typed option
 * argument.
 *
 * @param <T>  The type of the option argument.
 */
@FunctionalInterface
public interface Converter<T> {

  /**
   * Converts the given string to the typed option argument.
   *
   * @param optArg  The string to be validated.
   * @param option  The option name.
   * @param storeKey  The store key.
   * @return  A converted value.
   * @throws Exception  If failed to validate the option argument.
   */
  T convert(String optArg, String option, String storeKey) throws Exception;
}
