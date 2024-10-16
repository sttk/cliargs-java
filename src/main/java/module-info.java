/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */

/**
 * Defines the APIs for operating command line arguments.
 */
module com.github.sttk.cliargs {
  exports com.github.sttk.cliargs;
  exports com.github.sttk.cliargs.annotations;
  exports com.github.sttk.cliargs.exceptions;
  exports com.github.sttk.cliargs.validators;
  requires transitive com.github.sttk.linebreak;
}
