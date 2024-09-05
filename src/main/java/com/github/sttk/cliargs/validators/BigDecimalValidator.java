/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.validators;

import java.math.BigDecimal;
import com.github.sttk.cliargs.Cmd;
import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;

/**
 * Is the validator class which provides the method to validate an option argument string as a
 * {@link BigDecimal}.
 */
public class BigDecimalValidator implements Validator {
  /**
   * The default constructor.
   */
  public BigDecimalValidator() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(String storeKey, String name, String arg) throws OptionArgIsInvalid {
    try {
      new BigDecimal(arg);
    } catch (Exception e) {
      throw new OptionArgIsInvalid(storeKey, name, arg, "invalid BigDecimal", e);
    }
  }
}
