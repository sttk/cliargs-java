/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.exceptions;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Is the exception which indicates that it is failed to set option arguments (including those
 * from default values) to a field of an option store.
 */
public class FailToSetOptionStoreField extends Exception {
  private static final long serialVersionUID = -126807064740672333L;

  /** The field name in the option store. */
  public final String field;

  /** The class of the field in the option store. */
  public final Class<?> type;

  /** The option arguments that occurs this exception. */
  @SuppressWarnings("serial")
  public final List<String> optArgs;

  /**
   * Constructs an instance of this class.
   *
   * @param field  The field name of the option store object.
   * @param type  The field type of the option store object.
   * @param optArgs  The option arguments.
   * @param cause  The cause exception.
   */
  public FailToSetOptionStoreField(
    String field, Class<?> type, List<String> optArgs, Exception cause
  ) {
    super(cause);
    this.field = field;
    this.type = type;
    this.optArgs = Collections.unmodifiableList(optArgs);
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
    j.add("optArgs:" + this.optArgs.toString());
    j.add("cause:" + String.valueOf(this.getCause()));
    return j.toString();
  }
}
