/*
 * ParseFor class.
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import com.github.sttk.cliargs.CliArgs.IllegalOptionType;
import com.github.sttk.cliargs.CliArgs.FailToSetOptionStoreField;
import com.github.sttk.cliargs.CliArgs.FailToConvertDefaultsInOptAnnotation;
import com.github.sttk.cliargs.annotation.Opt;
import com.github.sttk.cliargs.convert.Converter;
import com.github.sttk.cliargs.OptCfg.Postparser;
import com.github.sttk.exception.ReasonedException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

interface ParseFor {

  static OptCfg[] makeOptCfgsFor(Object options) throws ReasonedException {
    var list = new ArrayList<OptCfg>();

    Class<?> cls = options.getClass();
    while (cls != null) {
      for (var fld : cls.getDeclaredFields()) {
        var annotation = fld.getAnnotation(Opt.class);
        if (annotation == null) {
          continue;
        }

        var type = fld.getType();
        var hasArg = !((type == boolean.class) || (type == Boolean.class));
        var isArray = type.isArray();
        if (isArray) {
          type = type.getComponentType();
        }

        list.add(OptCfgFactory.create(type, hasArg, isArray, fld, options));
      }

      cls = cls.getSuperclass();
    }

    return list.toArray(new OptCfg[list.size()]);
  }
}

interface OptCfgFactory {

  static <T> OptCfg create(
    Class<T> type, boolean hasArg, boolean isArray, Field fld, Object options
  ) throws ReasonedException {
    var annotation = fld.getAnnotation(Opt.class);
    var cfg = annotation.cfg();
    var desc = annotation.desc();
    var arg = annotation.arg();

    var storeKey = fld.getName();

    Converter<T> converter = OptCfg.findConverter(type);
    if (converter == null) {
      if (hasArg && type != String.class) {
        throw new ReasonedException(new IllegalOptionType(type, storeKey));
      }
    }

    Postparser<T> postparser = optArgs -> {
      try {
        setOptionStoreFieldValue(options, fld, optArgs, type, hasArg, isArray);
      } catch (Exception e) {
        var reason = new FailToSetOptionStoreField(storeKey, type, optArgs);
        throw new ReasonedException(reason, e);
      }
    };

    var namesAndDefs = cfg.split("=", 2);
    var names = parseNames(namesAndDefs[0], storeKey);
    var defs = parseDefaults(namesAndDefs, converter, storeKey);

    return new OptCfg(
      storeKey, names, hasArg, isArray, type, defs, desc, arg,
      converter, postparser
    );
  }

  private static List<String> parseNames(String namesStr, String storeKey) {
    if (isEmpty(namesStr)) {
      return List.of(storeKey);
    }
    return List.of(namesStr.split(","));
  }

  private static <T> List<T> parseDefaults(
    String[] namesAndDefaults, Converter<T> converter, String storeKey
  ) throws ReasonedException {
    if (namesAndDefaults.length < 2) {
      return null;
    }

    var str = namesAndDefaults[1];
    int len = str.length();

    String[] arr = null;
    if (str.endsWith("]")) {
      if (str.startsWith("[")) {
        if (len > 2) {
          arr = str.substring(1, len-1).split(",");
        } else {
          arr = new String[0];
        }
      } else if (str.charAt(1) == '[') {
        if (len > 3) {
          var sep = str.substring(0, 1);
          arr = str.substring(2, len-1).split("\\" + sep); // Escape because String#split takes a regexp string.
        } else {
          arr = new String[0];
        }
      } else {
        arr = new String[]{str};
      }
    } else {
      arr = new String[]{str};
    }

    var lst = new ArrayList<T>();
    if (converter != null) {
      for (var s : arr) {
        try {
          lst.add(converter.convert(s, storeKey, storeKey));
        } catch (Exception e) {
          var reason = new FailToConvertDefaultsInOptAnnotation(storeKey, str);
          throw new ReasonedException(reason, e);
        }
      }
    } else {
      for (var s : arr) {
        @SuppressWarnings("unchecked")
        T t = (T) s;
        lst.add(t);
      }
    }

    return lst;
  }

  private static <T> void setOptionStoreFieldValue(
    Object options, Field fld, List<T> optArgs, Class<T> type,
    boolean hasArg, boolean isArray
  ) throws Exception {
    if (optArgs == null) {
      return;
    }

    if (type == boolean.class || type == Boolean.class) {
      fld.setAccessible(true);
      fld.set(options, true);
      return;
    }

    if (! isArray) {
      fld.setAccessible(true);
      fld.set(options, optArgs.get(0));
      return;
    }

    int n = optArgs.size();
    if (type == int.class) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Integer.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else if (type == double.class) {
      double[] arr = new double[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Double.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else if (type == long.class) {
      long[] arr = new long[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Long.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else if (type == float.class) {
      float[] arr = new float[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Float.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else if (type == short.class) {
      short[] arr = new short[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Short.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else if (type == byte.class) {
      byte[] arr = new byte[n];
      for (int i = 0; i < n; i++) {
        arr[i] = Byte.class.cast(optArgs.get(i));
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    } else {
      @SuppressWarnings("unchecked")
      T[] arr = (T[]) java.lang.reflect.Array.newInstance(type, n);
      for (int i = 0; i < n; i++) {
        arr[i] = optArgs.get(i);
      }
      fld.setAccessible(true);
      fld.set(options, arr);
    }
  }
}
