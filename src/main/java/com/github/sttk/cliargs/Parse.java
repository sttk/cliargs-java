/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Base.CollectArgs;
import static com.github.sttk.cliargs.Base.CollectOpts;
import static com.github.sttk.cliargs.Base.TakeOptArgs;
import static com.github.sttk.cliargs.Base.parseArgs;

import com.github.sttk.cliargs.exceptions.InvalidOption;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

class Parse {

  List<String> args = new ArrayList<>();
  Map<String, List<String>> opts = new HashMap<>();
  boolean isAfterNonOpt;

  Parse(boolean isAfterNonOpt) {
    this.isAfterNonOpt = isAfterNonOpt;
  }

  void parse(List<String> osArgs) throws InvalidOption {
    CollectArgs collectArgs = arg -> this.args.add(arg);

    CollectOpts collectOpts = (name, option) -> {
      var lst = this.opts.computeIfAbsent(name, k -> new ArrayList<String>());
      if (option.isPresent()) {
        lst.add(option.get());
      }
    };

    TakeOptArgs takeOptArgs = s -> false;

    parseArgs(osArgs, collectArgs, collectOpts, takeOptArgs, false, this.isAfterNonOpt);
  }

  Optional<Cmd> parseUntilSubCmd(List<String> osArgs) throws InvalidOption {
    CollectArgs collectArgs = arg -> {};

    CollectOpts collectOpts = (name, option) -> {
      var lst = this.opts.computeIfAbsent(name, k -> new ArrayList<String>());
      if (option.isPresent()) {
        lst.add(option.get());
      }
    };

    TakeOptArgs takeOptArgs = s -> false;

    var idx = parseArgs(osArgs, collectArgs, collectOpts, takeOptArgs, true, this.isAfterNonOpt);
    if (idx.isPresent()) {
      boolean isAfterNonOpt = (idx.get() < 0);
      int i = Math.abs(idx.get());
      int n = osArgs.size();
      return Optional.of(new Cmd(osArgs.get(i), osArgs.subList(i + 1, n), isAfterNonOpt));
    }

    return Optional.empty();
  }
}
