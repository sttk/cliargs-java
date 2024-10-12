/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that there is no configuration about the input option.
 */
public class UnconfiguredOption extends InvalidOption {
  private static final long serialVersionUID = 1826741289807730404L;

  /** The option name that caused this exception. */
  public final String option;

  /**
   * Constructs an instance of this class.
   *
   * @param option  The option name that caused this exception.
   */
  public UnconfiguredOption(final String option) {
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
