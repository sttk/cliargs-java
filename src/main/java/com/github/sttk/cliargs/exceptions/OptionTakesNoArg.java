/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that the option is not supposed to take an argument in
 * configuration, but an argument is specified.
 */
public class OptionTakesNoArg extends InvalidOption {
  private static final long serialVersionUID = -1293107355753972433L;

  /** The option name that caused this exception. */
  public final String option;

  /** The store key of the specified option in the cnfiguration. */
  public final String storeKey;

  /**
   * Constructs an instance of this class.
   *
   * @param option  The option name that caused this exception.
   * @param storeKey  The store key of the specified option in the configuration.
   */
  public OptionTakesNoArg(final String option, final String storeKey) {
    this.option = option;
    this.storeKey = storeKey;
  }

  /**
   * Gets the option name that caused this exception.
   *
   * @return  The option name.
   */
  @Override
  public String option() {
    return this.option;
  }

  /**
   * Returns a string which represents the content of this exception.
   *
   * @return  The exception message.
   */
  @Override
  public String getMessage() {
    var j = new StringJoiner(",", getClass().getSimpleName() + "{", "}");
    j.add("option:" + this.option);
    j.add("storeKey:" + this.storeKey);
    return j.toString();
  }
}
