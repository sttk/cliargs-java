/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import com.github.sttk.reasonedexception.ReasonedException;
import com.github.sttk.cliargs.unicode.Ascii;
import com.github.sttk.cliargs.CliArgs.OptionHasInvalidChar;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

interface Parse {

  @FunctionalInterface
  static interface CmdArgCollector {
    void collect(String arg);
  }

  @FunctionalInterface
  static interface OptArgCollector {
    void collect(String name, String arg) throws ReasonedException;

    default void collect(String name) throws ReasonedException {
      collect(name, null);
    }
  }

  @FunctionalInterface
  static interface OptArgNeeder {
    boolean isNeeded(String opt);
  }

  static Result parse(String cmdName, String[] cliArgs) {
    var args = new ArrayList<String>();
    var opts = new HashMap<String, List<?>>();

    CmdArgCollector collectArgs = s -> args.add(s);

    OptArgCollector collectOpts = (name, a) -> {
      @SuppressWarnings("unchecked")
      var arr = (List<Object>) opts.get(name);
      if (arr == null) {
        arr = new ArrayList<>();
        opts.put(name, arr);
      }
      if (a != null) {
        arr.add(a);
      }
    };

    OptArgNeeder _false = s -> false;

    ReasonedException exc = null;
    try {
      parseArgs(cliArgs, collectArgs, collectOpts, _false);
    } catch (ReasonedException e) {
      exc = e;
    }

    cmdName = Path.of(cmdName).getFileName().toString();
    var cmd = new Cmd(cmdName, args, opts);
    return new Result(cmd, null, exc);
  }

  static void parseArgs(
    String[] cliArgs,
    CmdArgCollector collectArgs,
    OptArgCollector collectOpts,
    OptArgNeeder needArgs
  ) throws ReasonedException {

    boolean isNonOpt = false;
    String prevOptTakingArgs = "";
    ReasonedException firstExc = null;

    L0: for (int iArg = 0, nArg = cliArgs.length; iArg < nArg; iArg++) {
      var arg = cliArgs[iArg];

      if (isNonOpt) {
        collectArgs.collect(arg);

      } else if (! isEmpty(prevOptTakingArgs)) {
        try {
          collectOpts.collect(prevOptTakingArgs, arg);
        } catch (ReasonedException e) {
          if (firstExc == null) {
            firstExc = e;
          }
          continue L0;
        }
        prevOptTakingArgs = "";

      } else if (arg.startsWith("--")) {
        if (arg.length() == 2) {
          isNonOpt = true;
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
                collectOpts.collect(nm, a);
              } catch (ReasonedException e) {
                if (firstExc == null) {
                  firstExc = e;
                }
                continue L0;
              }
              break;
            }

            if (! Ascii.isAlNumMarks(cp)) {
              if (firstExc == null) {
                var reason = new OptionHasInvalidChar(arg);
                firstExc = new ReasonedException(reason);
              }
              continue L0;
            }
          } else {
            if (! Ascii.isAlphabets(cp)) {
              if (firstExc == null) {
                var reason = new OptionHasInvalidChar(arg);
                firstExc = new ReasonedException(reason);
              }
              continue L0;
            }
          }

          i += cw;
        }

        if (i == len) {
          if (needArgs.isNeeded(arg) && iArg < nArg - 1) {
            prevOptTakingArgs = arg;
            continue L0;
          }

          try {
            collectOpts.collect(arg);
          } catch (ReasonedException e) {
            if (firstExc == null) {
              firstExc = e;
            }
            continue L0;
          }
        }

      } else if (arg.startsWith("-")) {
        if (arg.length() == 1) {
          collectArgs.collect(arg);
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
                  collectOpts.collect(name, arg.substring(i + cw));
                } catch (ReasonedException e) {
                  if (firstExc == null) {
                    firstExc = e;
                  }
                }
              }
              continue L0;
            }

            if (! isEmpty(name)) {
              try {
                collectOpts.collect(name);
              } catch (ReasonedException e) {
                if (firstExc == null) {
                  firstExc = e;
                }
              }
            }
          }

          if (! Ascii.isAlphabets(cp)) {
            if (firstExc == null) {
              var reason = new OptionHasInvalidChar(Character.toString(cp));
              firstExc = new ReasonedException(reason);
            }
            name = "";
          } else {
            name = Character.toString(cp);
          }

          i += cw;
        }

        if (i == len && ! isEmpty(name)) {
          if (needArgs.isNeeded(name) && iArg < nArg - 1) {
            prevOptTakingArgs = name;
          } else {
            try {
              collectOpts.collect(name);
            } catch (ReasonedException e) {
              if (firstExc == null) {
                firstExc = e;
              }
              continue L0;
            }
          }
        }

      } else {
        collectArgs.collect(arg);
      }
    }

    if (firstExc != null) {
      throw firstExc;
    }
  }
}
