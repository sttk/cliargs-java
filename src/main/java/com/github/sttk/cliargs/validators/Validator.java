/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.validators;

import com.github.sttk.cliargs.Cmd;
import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;

/**
 * Is the interface which provides the method declaration to validate an option argument.
 */
@FunctionalInterface
public interface Validator {

  /**
   * Validates an option argument string whether it is valid format of the specified type, etc.
   *
   * @param storeKey  The store key used to store in {@link Cmd} instance.
   * @param name  The option name.
   * @param arg  The option argument string in command line arguments.
   * @throws OptionArgIsInvalid  If the option argument is invalid.
   */
  void validate(String storeKey, String name, String arg) throws OptionArgIsInvalid;
}
