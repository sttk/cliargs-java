/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that an invalid character is found in an option.
 */
public class OptionContainsInvalidChar extends InvalidOption {
  private static final long serialVersionUID = -7538950613249783600L;

  /** The option name that caused this exception. */
  public final String option;

  /**
   * Constructs an instance of this class.
   *
   * @param option  The option name that caused this exception.
   */
  public OptionContainsInvalidChar(final String option) {
    this.option = option;
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
    return j.toString();
  }
}
