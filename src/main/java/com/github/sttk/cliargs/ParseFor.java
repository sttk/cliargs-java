/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Base.isEmpty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.github.sttk.cliargs.annotations.Opt;
import com.github.sttk.cliargs.validators.Validator;
import com.github.sttk.cliargs.validators.ShortValidator;
import com.github.sttk.cliargs.validators.IntegerValidator;
import com.github.sttk.cliargs.validators.LongValidator;
import com.github.sttk.cliargs.validators.FloatValidator;
import com.github.sttk.cliargs.validators.DoubleValidator;
import com.github.sttk.cliargs.validators.BigIntegerValidator;
import com.github.sttk.cliargs.validators.BigDecimalValidator;
import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;
import com.github.sttk.cliargs.exceptions.FailToSetOptionStoreField;
import com.github.sttk.cliargs.exceptions.BadFieldType;

interface ParseFor {

  static OptCfg[] makeOptCfgsFor(Object optStore) {
    var list = new ArrayList<OptCfg>();

    Class<?> cls = optStore.getClass();
    while (cls != null) {
      for (var fld : cls.getDeclaredFields()) {
        list.add(OptCfgFactory.create(fld, optStore));
      }

      cls = cls.getSuperclass();
    }

    return list.toArray(new OptCfg[list.size()]);
  }

  static void setOptionStoreFieldValues(
    final Object optStore, final OptCfg[] cfgs, final Map<String, List<String>> opts
  ) throws FailToSetOptionStoreField {
    for (var cfg : cfgs) {
      var optArgs = opts.get(cfg.storeKey);
      try {
        OptCfgFactory.setOptionStoreFieldValue(optStore, cfg.field, optArgs);
      } catch (Exception e) {
        throw new FailToSetOptionStoreField(cfg.storeKey, cfg.field.getType(), optArgs, e);
      }
    }
  }
}

interface OptCfgFactory {

  static OptCfg create(final Field fld, final Object optStore) {
    String cfg, desc, arg;
    var annotation = fld.getAnnotation(Opt.class);
    if (annotation != null) {
      cfg = annotation.cfg();
      desc = annotation.desc();
      arg = annotation.arg();
    } else {
      cfg = "";
      desc = "";
      arg = "";
    }

    final var storeKey = fld.getName();

    var type = fld.getType();
    final var hasArg = !(type.equals(boolean.class) || type.equals(Boolean.class));
    final var isArray = type.isArray();

    if (isArray) {
      type = type.getComponentType();
    }

    var namesAndDefaults = cfg.split("=", 2);
    var names = parseNames(namesAndDefaults[0]);

    Optional<List<String>> defaults = (namesAndDefaults.length < 2) ? Optional.empty() :
      Optional.of(parseDefaults(namesAndDefaults[1], type, storeKey));

    var validator = findValidator(type);

    return new OptCfg(storeKey, names, hasArg, isArray, defaults, desc, arg, validator, fld);
  }

  private static List<String> parseNames(String namesStr) {
    if (isEmpty(namesStr)) {
      return Collections.emptyList();
    }
    return List.of(namesStr.split(","));
  }

  private static List<String> parseDefaults(String defaultsStr, Class<?> type, String storeKey) {
    int len = defaultsStr.length();

    String[] defaults;
    if (defaultsStr.endsWith("]")) {
      if (defaultsStr.startsWith("[")) {
        if (len > 2) {
          defaults = defaultsStr.substring(1, len-1).split(",");
        } else {
          defaults = new String[0];
        }
      } else if (len >= 3 && defaultsStr.charAt(1) == '[') {
        if (len > 3) {
          var sep = defaultsStr.substring(0, 1);
          defaults = defaultsStr.substring(2, len-1).split("\\" + sep); // Escape because String#split takes a regexp string.
        } else {
          defaults = new String[0];
        }
      } else {
        defaults = new String[]{defaultsStr};
      }
    } else {
      defaults = new String[]{defaultsStr};
    }

    return List.of(defaults);
  }

  private static Validator findValidator(Class<?> type) {
    if (type.equals(int.class) || type.equals(Integer.class)) {
      return new IntegerValidator();
    } else if (type.equals(double.class) || type.equals(Double.class)) {
      return new DoubleValidator();
    } else if (type.equals(long.class) || type.equals(Long.class)) {
      return new LongValidator();
    } else if (type.equals(BigDecimal.class)) {
      return new BigDecimalValidator();
    } else if (type.equals(BigInteger.class)) {
      return new BigIntegerValidator();
    } else if (type.equals(float.class) || type.equals(Float.class)) {
      return new FloatValidator();
    } else if (type.equals(short.class) || type.equals(Short.class)) {
      return new ShortValidator();
    } else {
      return null;
    }
  }

  static void setOptionStoreFieldValue(Object optStore, Field fld, List<String> optArgs)
    throws Exception
  {
    if (optArgs == null) {
      return;
    }

    var type = fld.getType();

    if (type == boolean.class || type == Boolean.class) {
      fld.setAccessible(true);
      fld.set(optStore, true);
      return;
    }
  
    int n = optArgs.size();

    if (type.isArray()) {
      type = type.getComponentType();

      if (type.equals(int.class)) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Integer.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Integer.class)) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Integer.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(String.class)) {
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
          arr[i] = optArgs.get(i);
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(double.class)) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Double.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Double.class)) {
        Double[] arr = new Double[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Double.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(long.class)) {
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Long.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Long.class)) {
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Long.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(BigDecimal.class)) {
        BigDecimal[] arr = new BigDecimal[n];
        for (int i = 0; i < n; i++) {
          arr[i] = new BigDecimal(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(BigInteger.class)) {
        BigInteger[] arr = new BigInteger[n];
        for (int i = 0; i < n; i++) {
          arr[i] = new BigInteger(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(float.class)) {
        float[] arr = new float[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Float.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Float.class)) {
        Float[] arr = new Float[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Float.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(short.class)) {
        short[] arr = new short[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Short.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Short.class)) {
        Short[] arr = new Short[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Short.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(byte.class)) {
        byte[] arr = new byte[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Byte.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else if (type.equals(Byte.class)) {
        Byte[] arr = new Byte[n];
        for (int i = 0; i < n; i++) {
          arr[i] = Byte.valueOf(optArgs.get(i));
        }
        fld.setAccessible(true);
        fld.set(optStore, arr);
      } else {
        throw new BadFieldType(fld.getName(), type);
      }
    } else if (n > 0) {
      if (type.equals(int.class) || type.equals(Integer.class)) {
        int val = Integer.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(String.class)) {
        fld.setAccessible(true);
        fld.set(optStore, optArgs.get(0));
      } else if (type.equals(double.class) || type.equals(Double.class)) {
        double val = Double.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(long.class) || type.equals(Long.class)) {
        long val = Long.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(BigDecimal.class)) {
        BigDecimal val = new BigDecimal(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(BigInteger.class)) {
        BigInteger val = new BigInteger(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(float.class) || type.equals(Float.class)) {
        float val = Float.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(short.class) || type.equals(Short.class)) {
        short val = Short.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else if (type.equals(byte.class) || type.equals(Byte.class)) {
        byte val = Byte.valueOf(optArgs.get(0));
        fld.setAccessible(true);
        fld.set(optStore, val);
      } else {
        throw new BadFieldType(fld.getName(), type);
      }
    }
  }
}
