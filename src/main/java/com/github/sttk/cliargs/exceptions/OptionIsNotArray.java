/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that the option is supposed to take one argument in the
 * configuration, but multiple arguments are specified.
 */
public class OptionIsNotArray extends InvalidOption {
  private static final long serialVersionUID = 685832743145948330L;

  /** The option name that caused this exception. */
  public final String option;

  /** The store key of the specified option in the configuration. */
  public final String storeKey;

  /**
   * Constructs an instance of this class.
   *
   * @param option  The option name that caused this exception.
   * @param storeKey  The store key of the specified option in the configuration.
   */
  public OptionIsNotArray(final String option, final String storeKey) {
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
