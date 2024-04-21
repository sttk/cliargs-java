/*
 * Ascii class.
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.cliargs.unicode;

public interface Ascii {

  static boolean isAlNumMarks(int codepoint) {
    if (codepoint == 0x2d) { // -
      return true;
    } else if (0x30 <= codepoint && codepoint <= 0x39) { // 0-9
      return true;
    } else if (0x41 <= codepoint && codepoint <= 0x5a) { // A-Z
      return true;
    } else if (0x61 <= codepoint && codepoint <= 0x7a) { // a-z
      return true;
    } else {
      return false;
    }
  }

  static boolean isAlphabets(int codepoint) {
    if (0x41 <= codepoint && codepoint <= 0x5a) { // A-Z
      return true;
    } else if (0x61 <= codepoint && codepoint <= 0x7a) { // a-z
      return true;
    } else {
      return false;
    }
  }
}
