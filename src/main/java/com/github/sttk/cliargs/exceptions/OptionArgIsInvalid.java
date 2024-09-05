/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that the option argument is invalidated by the validator in the
 * option configuration.
 */
public class OptionArgIsInvalid extends InvalidOption {
  private static final long serialVersionUID = 6659739907396766995L;

  /** The store key of the option configuration that caused this exception. */
  public final String storeKey;

  /** The option name that caused this exception. */
  public final String option;

  /** The option argument that was validated. */
  public final String optArg;

  /** The details for the invalidation. */
  public final String details;

  /**
   * Constructs an instance of this class.
   *
   * @param storeKey  The store key of the option configuration that caused this exception.
   * @param option  The option name that caused this exception.
   * @param optArg  The option argument that as validated.
   * @param details  The details for the invalidation.
   * @param cause  The cause of this exception.
   */
  public OptionArgIsInvalid(
    final String storeKey, String option, String optArg, String details, Throwable cause
  ) {
    super(cause);
    this.storeKey = storeKey;
    this.option = option;
    this.optArg = optArg;
    this.details = details;
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
    j.add("storeKey:" + this.storeKey);
    j.add("option:" + this.option);
    j.add("optArg:" + this.optArg);
    j.add("details:" + this.details);
    j.add("cause:" + getCause().toString());
    return j.toString();
  }
}
