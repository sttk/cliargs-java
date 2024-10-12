/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that an option configuration contradicts that the default
 * arguments is not empty though it does not take option arguments.
 */
public class ConfigHasDefaultsButHasNoArg extends InvalidOption {
  private static final long serialVersionUID = 6199876526299375001L;

  /** The store key of the option configuration that caused this exception. */
  public final String storeKey;

  /** The first name of the option configuration. */
  public final String name;

  /**
   * Constructs an instance of this class.
   *
   * @param storeKey  The store key of the option configuration that caused this exception.
   * @param name  The first name of the option configuration.
   */
  public ConfigHasDefaultsButHasNoArg(final String storeKey, final String name) {
    this.storeKey = storeKey;
    this.name = name;
  }

  /**
   * Gets the option name that caused this exception.
   *
   * @return  The option name.
   */
  @Override
  public String option() {
    return this.name;
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
    j.add("name:" + this.name);
    return j.toString();
  }
}
