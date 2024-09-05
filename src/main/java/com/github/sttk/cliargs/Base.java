/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import com.github.sttk.cliargs.exceptions.InvalidOption;
import com.github.sttk.cliargs.exceptions.OptionContainsInvalidChar;
import java.util.List;
import java.util.Optional;

interface Base {

  @FunctionalInterface
  static interface CollectArgs {
    void exec(String arg) throws InvalidOption;
  }

  @FunctionalInterface
  static interface CollectOpts {
    void exec(String name, Optional<String> arg) throws InvalidOption;
  }

  @FunctionalInterface
  static interface TakeOptArgs {
    boolean check(String option);
  }

  // The returned index is the index of sub command if untilFirstArg is true.
  // And this index is negative if after an argument '--'.
  static Optional<Integer> parseArgs(
    List<String> osArgs,
    CollectArgs collectArgs,
    CollectOpts collectOpts,
    TakeOptArgs takeOptArgs,
    boolean untilFirstArg,
    boolean isAfterNonOpt
  ) throws InvalidOption {

    String prevOptTakingArgs = "";
    Optional<InvalidOption> firstExc = Optional.empty();

    L0: for (int iArg = 0, nArg = osArgs.size(); iArg < nArg; iArg++) {
      var arg = osArgs.get(iArg);

      if (isAfterNonOpt) {
        if (untilFirstArg) {
          if (firstExc.isPresent()) {
            throw firstExc.get();
          }
          return Optional.of(-iArg); // NOTICE negative if after '--'
        }
        collectArgs.exec(arg);
      } else if (! prevOptTakingArgs.isEmpty()) {
        try {
          collectOpts.exec(prevOptTakingArgs, Optional.of(arg));
          prevOptTakingArgs = "";
        } catch (InvalidOption e) {
          prevOptTakingArgs = "";
          if (firstExc.isEmpty()) {
            firstExc = Optional.of(e);
          }
          continue L0;
        }
      } else if (arg.startsWith("--")) {
        if (arg.length() == 2) {
          isAfterNonOpt = true;
          continue L0;
        }
        arg = arg.substring(2);

        int i = 0, len = arg.length();
        while (i < len) {
          int cp = arg.codePointAt(i);
          int cw = Character.charCount(cp);

          if (i > 0) {
            if (cp == 0x3d) { // '='
              try {
                var nm = arg.substring(0, i);
                var a = arg.substring(i + cw);
                collectOpts.exec(nm, Optional.of(a));
              } catch (InvalidOption e) {
                if (firstExc.isEmpty()) {
                  firstExc = Optional.of(e);
                }
                continue L0;
              }
              break;
            }
            if (! isAllowedCodePoint(cp)) {
              if (firstExc.isEmpty()) {
                var e = new OptionContainsInvalidChar(arg);
                firstExc = Optional.of(e);
              }
              continue L0;
            }
          } else {
            if (! isAllowedFirstCodePoint(cp)) {
              if (firstExc.isEmpty()) {
                var e = new OptionContainsInvalidChar(arg);
                firstExc = Optional.of(e);
              }
              continue L0;
            }
          }
          i += cw;
        }

        if (i == len) {
          if (takeOptArgs.check(arg) && iArg < nArg - 1) {
            prevOptTakingArgs = arg;
            continue L0;
          }
          try {
            collectOpts.exec(arg, Optional.empty());
          } catch (InvalidOption e) {
            if (firstExc.isEmpty()) {
              firstExc = Optional.of(e);
            }
            continue L0;
          }
        }
      } else if (arg.startsWith("-")) {
        if (arg.length() == 1) {
          if (untilFirstArg) {
            if (firstExc.isPresent()) {
              throw firstExc.get();
            }
            return Optional.of(iArg);
          }
          collectArgs.exec(arg);
          continue L0;
        }
        arg = arg.substring(1);

        String name = "";
        int i = 0, len = arg.length();
        while (i < len) {
          int cp = arg.codePointAt(i);
          int cw = Character.charCount(cp);

          if (i > 0) {
            if (cp == 0x3d) { // '='
              if (! isEmpty(name)) {
                try {
                  collectOpts.exec(name, Optional.of(arg.substring(i + cw)));
                } catch (InvalidOption e) {
                  if (firstExc.isEmpty()) {
                    firstExc = Optional.of(e);
                  }
                }
              }
              continue L0;
            }
            if (! isEmpty(name)) {
              try {
                collectOpts.exec(name, Optional.empty());
              } catch (InvalidOption e) {
                if (firstExc.isEmpty()) {
                  firstExc = Optional.of(e);
                }
              }
            }
          }
          if (! isAllowedFirstCodePoint(cp)) {
            if (firstExc.isEmpty()) {
              var e = new OptionContainsInvalidChar(Character.toString(cp));
              firstExc = Optional.of(e);
            }
            name = "";
          } else {
            name = Character.toString(cp);
          }
          i += cw;
        }

        if (i == len && ! isEmpty(name)) {
          if (takeOptArgs.check(name) && iArg < nArg - 1) {
            prevOptTakingArgs = name;
          } else {
            try {
              collectOpts.exec(name, Optional.empty());
            } catch (InvalidOption e) {
              if (firstExc.isEmpty()) {
                firstExc = Optional.of(e);
              }
              continue L0;
            }
          }
        }
      } else {
        if (untilFirstArg) {
          if (!firstExc.isEmpty()) {
            throw firstExc.get();
          }
          return Optional.of(iArg);
        }
        collectArgs.exec(arg);
      }
    }

    if (firstExc.isPresent()) {
      throw firstExc.get();
    }
    return Optional.empty();
  }

  static boolean isAllowedFirstCodePoint(int cp) {
    if (0x41 <= cp && cp <= 0x5a) { // A-Z
      return true;
    } else if (0x61 <= cp && cp <= 0x7a) { // a-z
      return true;
    } else {
      return false;
    }
  }

  static boolean isAllowedCodePoint(int cp) {
    if (cp == 0x2d) { // -
      return true;
    } else if (0x30 <= cp && cp <= 0x39) { // 0-9
      return true;
    } else if (0x41 <= cp && cp <= 0x5a) { // A-Z
      return true;
    } else if (0x61 <= cp && cp <= 0x7a) { // a-z
      return true;
    } else {
      return false;
    }
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

  static boolean isBlank(String value) {
    return (value == null || value.isBlank());
  }
}
