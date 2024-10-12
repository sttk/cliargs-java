/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Base.isEmpty;
import static com.github.sttk.cliargs.Base.CollectArgs;
import static com.github.sttk.cliargs.Base.CollectOpts;
import static com.github.sttk.cliargs.Base.TakeOptArgs;
import static com.github.sttk.cliargs.Base.parseArgs;
import static java.util.Collections.emptyList;

import com.github.sttk.cliargs.exceptions.InvalidOption;
import com.github.sttk.cliargs.exceptions.StoreKeyIsDuplicated;
import com.github.sttk.cliargs.exceptions.ConfigIsArrayButHasNoArg;
import com.github.sttk.cliargs.exceptions.ConfigHasDefaultsButHasNoArg;
import com.github.sttk.cliargs.exceptions.OptionNameIsDuplicated;
import com.github.sttk.cliargs.exceptions.OptionTakesNoArg;
import com.github.sttk.cliargs.exceptions.OptionIsNotArray;
import com.github.sttk.cliargs.exceptions.OptionNeedsArg;
import com.github.sttk.cliargs.exceptions.UnconfiguredOption;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

class ParseWith {

  List<String> args = new ArrayList<>();
  Map<String, List<String>> opts = new HashMap<>();

  boolean isAfterNonOpt;
  boolean untilFirstArg;

  ParseWith(boolean isAfterNonOpt, boolean untilFirstArg) {
    this.isAfterNonOpt = isAfterNonOpt;
    this.untilFirstArg = untilFirstArg;
  }

  Optional<Integer> parseArgsWith(List<String> osArgs, OptCfg[] optCfgs) throws InvalidOption {
    final var self = this;

    final var optSet = new HashSet<String>();
    final var cfgMap = new HashMap<String, Integer>();

    final String anyOption = "*";
    boolean hasAnyOpt = false;

    for (int i = 0, n = optCfgs.length; i < n; i++) {
      var cfg = optCfgs[i];

      var names = cfg.names.stream().filter(nm -> !isEmpty(nm)).collect(Collectors.toList());

      var storeKey = cfg.storeKey;
      if (isEmpty(storeKey) && !isEmpty(names)) {
        storeKey = names.get(0);
      }

      if (isEmpty(storeKey)) {
        continue;
      }

      if (anyOption.equals(storeKey)) {
        hasAnyOpt = true;
        continue;
      }

      var firstName = isEmpty(names) ? storeKey : names.get(0);

      if (optSet.contains(storeKey)) {
        throw new StoreKeyIsDuplicated(storeKey, firstName);
      }
      optSet.add(storeKey);

      if (!cfg.hasArg) {
        if (cfg.isArray) {
          throw new ConfigIsArrayButHasNoArg(storeKey, firstName);
        }
        if (cfg.defaults.isPresent()) {
          var defaults = cfg.defaults.get();
          if (! isEmpty(defaults)) {
            throw new ConfigHasDefaultsButHasNoArg(storeKey, firstName);
          }
        }
      }

      if (isEmpty(names)) {
        cfgMap.put(firstName, i);
      } else {
        for (var name : names) {
          if (cfgMap.containsKey(name)) {
            throw new OptionNameIsDuplicated(storeKey, name);
          }
          cfgMap.put(name, i);
        }
      }
    }
    final boolean has_any_opt = hasAnyOpt;

    final TakeOptArgs takeOptArgs = opt -> {
      Integer i = cfgMap.get(opt);
      if (i != null) {
        return optCfgs[i].hasArg;
      }
      return false;
    };

    final CollectArgs collectArgs = arg -> {
      self.args.add(arg);
    };

    final CollectOpts collectOpts = (name, arg) -> {
      Integer i = cfgMap.get(name);
      if (i != null) {
        var cfg = optCfgs[i];

        var storeKey = cfg.storeKey;
        if (isEmpty(storeKey)) {
          storeKey = cfg.names.stream().filter(nm -> !isEmpty(nm)).findFirst().orElse("");
        }

        if (arg.isPresent()) {
          if (! cfg.hasArg) {
            throw new OptionTakesNoArg(name, storeKey);
          }

          var lst = self.opts.get(storeKey);
          if (lst != null) {
            if (! lst.isEmpty()) {
              if (! cfg.isArray) {
                throw new OptionIsNotArray(name, storeKey);
              }
            }

            if (cfg.validator != null) {
              cfg.validator.validate(storeKey, name, arg.get());
            }
            lst.add(arg.get());
          } else {
            if (cfg.validator != null) {
              cfg.validator.validate(storeKey, name, arg.get());
            }

            lst = new ArrayList<>();
            self.opts.put(storeKey, lst);
            lst.add(arg.get());
          }
        } else {
          if (cfg.hasArg) {
            throw new OptionNeedsArg(name, storeKey);
          }
          self.opts.put(storeKey, emptyList());
        }
      } else {
        if (!has_any_opt) {
          throw new UnconfiguredOption(name);
        }

        if (arg.isPresent()) {
          var lst = self.opts.get(name);
          if (lst == null) {
            lst = new ArrayList<String>();
            self.opts.put(name, lst);
          }
          lst.add(arg.get());
        } else {
          self.opts.put(name, emptyList());
        }
      }
    };

    var idx = parseArgs(osArgs, collectArgs, collectOpts, takeOptArgs,
      this.untilFirstArg, this.isAfterNonOpt);

    for (int i = 0, nn = optCfgs.length; i < nn; i++) {
      var cfg = optCfgs[i];

      var storeKey = cfg.storeKey;
      if (isEmpty(storeKey)) {
        storeKey = cfg.names.stream().filter(nm -> !isEmpty(nm)).findFirst().orElse("");
      }

      if (isEmpty(storeKey)) {
        continue;
      }

      if (anyOption.equals(storeKey)) {
        continue;
      }

      var lst = self.opts.get(storeKey);
      if (lst == null) {
        var defs = cfg.defaults;
        if (defs.isPresent()) {
          self.opts.put(storeKey, defs.get());
        }
      }
    }

    return idx;
  }
}
