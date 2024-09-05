/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

/**
 * Is the abstract class of the exception that provides a method to retrieve an option name.
 */
public abstract class InvalidOption extends Exception {
  private static final long serialVersionUID = -7690564759126409678L;

  /**
   * Constructs an instance of this class.
   */
  protected InvalidOption() {}

  /**
   * Constructs an instance of this class.
   *
   * @param cause  The cause of this exception.
   */
  protected InvalidOption(Throwable cause) {
    super(cause);
  }

  /**
   * Gets the option name that caused this exception.
   *
   * @return  The option name.
   */
  public abstract String option();
}
