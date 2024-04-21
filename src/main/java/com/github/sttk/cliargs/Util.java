/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import java.util.List;

interface Util {

  static String orEmpty(String value) {
    return (value != null) ? value : "";
  }

  static boolean isEmpty(String value) {
    return (value == null || value.isEmpty());
  }

  static boolean isEmpty(String[] array) {
    return (array == null || array.length == 0);
  }

  static boolean isEmpty(List<?> list) {
    return (list == null || list.isEmpty());
  }
}
