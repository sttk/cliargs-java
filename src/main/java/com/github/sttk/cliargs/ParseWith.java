/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static com.github.sttk.cliargs.Parse.CmdArgCollector;
import static com.github.sttk.cliargs.Parse.OptArgCollector;
import static com.github.sttk.cliargs.Parse.OptArgNeeder;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import com.github.sttk.cliargs.CliArgs.StoreKeyIsDuplicated;
import com.github.sttk.cliargs.CliArgs.ConfigIsArrayButHasNoArg;
import com.github.sttk.cliargs.CliArgs.ConfigHasDefaultsButHasNoArg;
import com.github.sttk.cliargs.CliArgs.OptionNameIsDuplicated;
import com.github.sttk.cliargs.CliArgs.UnconfiguredOption;
import com.github.sttk.cliargs.CliArgs.OptionTakesNoArg;
import com.github.sttk.cliargs.CliArgs.OptionNeedsArg;
import com.github.sttk.cliargs.CliArgs.OptionIsNotArray;
import com.github.sttk.cliargs.CliArgs.FailToConvertOptionArg;
import com.github.sttk.exception.ReasonedException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

interface ParseWith {

  static Result parse(OptCfg[] optCfgs, String cmdName, String ...cliArgs) {
    cmdName = Path.of(cmdName).getFileName().toString();

    var opts = new HashMap<String, List<?>>();

    final var cfgMap = new HashMap<String, Integer>();
    boolean hasAnyOpt = false;

    final String anyOption = "*";

    for (int i = 0, n = optCfgs.length; i < n; i++) {
      var cfg = optCfgs[i];

      var storeKey = cfg.storeKey;
      if (isEmpty(storeKey)) {
        continue;
      }

      if (Objects.equals(anyOption, storeKey)) {
        hasAnyOpt = true;
        continue;
      }

      if (opts.containsKey(storeKey)) {
        var reason = new StoreKeyIsDuplicated(storeKey);
        var exc = new ReasonedException(reason);
        var cmd = new Cmd(cmdName, emptyList(), emptyMap());
        return new Result(cmd, optCfgs, exc);
      }
      opts.put(storeKey, null); // To make opts contain this store key

      if (!cfg.hasArg) {
        if (cfg.isArray) {
          var reason = new ConfigIsArrayButHasNoArg(storeKey);
          var exc = new ReasonedException(reason);
          var cmd = new Cmd(cmdName, emptyList(), emptyMap());
          return new Result(cmd, optCfgs, exc);
        }
        if (! isEmpty(cfg.defaults)) {
          var reason = new ConfigHasDefaultsButHasNoArg(storeKey);
          var exc = new ReasonedException(reason);
          var cmd = new Cmd(cmdName, emptyList(), emptyMap());
          return new Result(cmd, optCfgs, exc);
        }
      }

      for (var nm : cfg.names) {
        if (cfgMap.containsKey(nm)) {
          var reason = new OptionNameIsDuplicated(storeKey, nm);
          var exc = new ReasonedException(reason);
          var cmd = new Cmd(cmdName, emptyList(), emptyMap());
          return new Result(cmd, optCfgs, exc);
        }
        cfgMap.put(nm, i);
      }
    }
    final boolean HAS_ANY_OPT = hasAnyOpt;

    OptArgNeeder needArgs = opt -> {
      var i = cfgMap.get(opt);
      if (i != null) {
        return optCfgs[i].hasArg;
      }
      return false;
    };

    var args = new ArrayList<String>();
    opts.clear();

    CmdArgCollector collectArgs = str -> {
      args.add(str);
    };
    OptArgCollector collectOpts = (name, arg) -> {
      var i = cfgMap.get(name);
      if (i == null) {
        if (! HAS_ANY_OPT) {
          var reason = new UnconfiguredOption(name);
          throw new ReasonedException(reason);
        }

        @SuppressWarnings("unchecked")
        var list = (List<Object>)opts.get(name);
        if (list == null) {
          list = new ArrayList<>();
          opts.put(name, list);
        }
        if (arg != null) {
          list.add(arg);
        }
        return;
      }

      var cfg = optCfgs[i];
      var storeKey = cfg.storeKey;

      if (! cfg.hasArg) {
        if (arg != null) {
          var reason = new OptionTakesNoArg(name, storeKey, arg);
          throw new ReasonedException(reason);
        }
      } else {
        if (arg == null) {
          var reason = new OptionNeedsArg(name, storeKey);
          throw new ReasonedException(reason);
        }
      }

      @SuppressWarnings("unchecked")
      var list = (List<Object>)opts.get(storeKey);
      if (! cfg.isArray) {
        int n = ((list == null) ? 0 : list.size()) + ((arg == null) ? 0 : 1);
        if (n > 1) {
          var reason = new OptionIsNotArray(name, storeKey, arg);
          throw new ReasonedException(reason);
        }
      }

      if (list == null) {
        list = new ArrayList<>();
        opts.put(storeKey, list);
      }
      if (cfg.converter != null) {
        try {
          list.add(cfg.converter.convert(arg, name, storeKey));
        } catch (ReasonedException e) {
          throw e;
        } catch (Exception e) {
          var reason = new FailToConvertOptionArg(arg, name, storeKey);
          throw new ReasonedException(reason, e);
        }
      } else {
        if (arg != null) {
          list.add(arg);
        }
      }
    };

    ReasonedException exc = null;
    try {
      Parse.parseArgs(cliArgs, collectArgs, collectOpts, needArgs);
    } catch (ReasonedException e) {
      exc = e;
    }

    for (var cfg : optCfgs) {
      if (! isEmpty(cfg.defaults)) {
        @SuppressWarnings("unchecked")
        var list = (List<Object>)opts.get(cfg.storeKey);
        if (list == null) {
          list = new ArrayList<>();
          opts.put(cfg.storeKey, list);
        }
        if (list.isEmpty()) {
          list.addAll(cfg.defaults);
        }
      }
    }

    var cmd = new Cmd(cmdName, args, opts);
    return new Result(cmd, optCfgs, exc);
  }
}
