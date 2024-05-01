/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import com.github.sttk.exception.ReasonedException;

/**
 * Is the record class that contains the result of parsing methods.
 *
 * @param cmd  A {@link Cmd} object.
 * @param optCfgs  An array of {@link OptCfg} objects.
 * @param exception  An exception object that occured while parsing.
 */
public record Result(Cmd cmd, OptCfg[] optCfgs, ReasonedException exception) {

  /**
   * Throws the exception that occured while parseing command line arguments.
   * If no exception occured, this method returns the {@link Cmd} object.
   *
   * @return The {@link Cmd} object.
   * @throws ReasonedException If failed to parsing command line arguments.
   */
  public Cmd cmdOrThrow() throws ReasonedException {
    if (exception() != null) {
      throw exception();
    }
    return cmd();
  }
}
