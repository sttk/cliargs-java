/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.StringJoiner;

/**
 * Is the exception which indicates that a type of a field of an option store is neither a boolean,
 * a number, a string, nor an array of numbers or strings.
 */
public class BadFieldType extends Exception {
  private static final long serialVersionUID = -1502067596131493623L;

  /** The field name in the option store. */
  public final String field;

  /** The type of the field in the option store. */
  public final Class<?> type;

  /**
   * Constructs an instance of this class.
   *
   * @param field  The field name in the option store.
   * @param type  The class of the field in the option store.
   */
  public BadFieldType(final String field, final Class<?> type) {
    this.field = field;
    this.type = type;
  }

  /**
   * Returns a string which represents the content of this exception.
   *
   * @return  The exception message.
   */
  @Override
  public String getMessage() {
    var j = new StringJoiner(",", getClass().getSimpleName() + "{", "}");
    j.add("field:" + this.field);
    j.add("type:" + this.type.getName());
    return j.toString();
  }
}

