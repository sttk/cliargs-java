/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.emptyList;

import com.github.sttk.cliargs.convert.Converter;
import com.github.sttk.cliargs.convert.ByteConverter;
import com.github.sttk.cliargs.convert.ShortConverter;
import com.github.sttk.cliargs.convert.IntConverter;
import com.github.sttk.cliargs.convert.LongConverter;
import com.github.sttk.cliargs.convert.FloatConverter;
import com.github.sttk.cliargs.convert.DoubleConverter;
import com.github.sttk.cliargs.convert.BigIntConverter;
import com.github.sttk.cliargs.convert.BigDecimalConverter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

import com.github.sttk.exception.ReasonedException;

/**
 * Is the class that represents an option configuration.
 * An option configuration consists of fields: {@code storeKey}, {@code names},
 * {@code hasArg}, {@code isArray}, {@code type}, {@code defaults},
 * {@code desc}, {@code argInHelp}, and {@code converter}.
 * <p>
 * This class can be instantiated by the constructor with variadic arguments
 * of {@link NamedParam} like named parameters, for example:
 * <pre>{@code
 *  import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
 *
 *    var optCfg = new OptCfg(names("foo-bar", "f"), type(Integer.class));
 * }</pre>
 */
public class OptCfg {

  /**
   * Is the key string to store option value(s) in the option map.
   * If this key is not specified or empty, the first element of {@code names}
   * field is used instead.
   */
  public final String storeKey;

  /**
   * Is the option name and aliases.
   * This is the string list and the order of the names is used in a help text.
   */
  public final List<String> names;

  /**
   * Is the flag which indicates whether allowing the option to take option
   * arguments.
   */
  public final boolean hasArg;

  /**
   * Is the flag which indicates whether allowing the option to take multiple
   * option arguments.
   */
  public final boolean isArray;

  /**
   * Is the class which indicates the type of the option value or the element
   * of the option values.
   */
  public final Class<?> type;

  /**
   * Is the default value(s) if the option takes the option argument(s) but
   * it is not given in command line arguments.
   */
  public final List<?> defaults;

  /**
   * Is the description of the option.
   * This is used in a help text.
   */
  public final String desc;

  /**
   * Is the display of the option argument in a help text.
   */
  public final String argInHelp;

  /**
   * Is the converter to convert an option argument string from command line
   * arguments to the specified type value.
   */
  public final Converter<?> converter;

  /**
   * Is the function interface that is executed after parsing command line
   * arguments.
   */
  public final Postparser<?> postparser;

  /**
   * Is the constructor that takes the all field values as parameters.
   * <p>
   * If {@code storeKey} is empty, it is set to first element of {@code names}.
   * If {@code names} is empty or it's first element is empty, the first
   * element is set to the value of {@code storeKey}.
   * 
   * <p>
   * If {@code type} is not null but {@code hasArg} is false, {@code hasArg} is
   * set to true forcely.
   * Also, {@code type} is not null but {@code converter} is null,
   * {@code converter} is set to a converter which convert a string to a
   * specified type value.
   *
   * @param <T> The type of the option argument value.
   *
   * @param storeKey  The store key.
   * @param names  The option name and aliases.
   * @param hasArg  True, if this option can take option arguments.
   * @param isArray  True, if this option can take one or multiple option
   *     arguments.
   * @param type  The type of the option value.
   * @param defaults  The default value(s).
   * @param desc  The description of the option.
   * @param argInHelp  The display of the option argument.
   * @param converter  The {@link Converter} object to convert the option
   *     argument string to the specified type value.
   * @param postparser  The {@link Postparser} object that is executed after
   *     parsing command line arguments.
   */
  public <T> OptCfg(
    String storeKey,
    List<String> names,
    boolean hasArg,
    boolean isArray,
    Class<T> type,
    List<T> defaults,
    String desc,
    String argInHelp,
    Converter<T> converter,
    Postparser<T> postparser
  ) {
    var init = new Init<T>();
    init.storeKey = storeKey;
    init.names = unmodifiableList(names);
    init.hasArg = hasArg;
    init.isArray = isArray;
    init.type = type;
    init.defaults = unmodifiableList(defaults);
    init.desc = desc;
    init.argInHelp = argInHelp;
    init.converter = converter;
    init.postparser = postparser;

    fillmissing(init);

    this.storeKey = init.storeKey;
    this.names = unmodifiableList(init.names);
    this.hasArg = init.hasArg;
    this.isArray = init.isArray;
    this.type = init.type;
    this.defaults = unmodifiableList(init.defaults);
    this.desc = init.desc;
    this.argInHelp = init.argInHelp;
    this.converter = init.converter;
    this.postparser = init.postparser;
  }

  /**
   * Is the constructor that takes the variadic parameters of
   * {@link NamedParam} which can be specified like named parameters.
   * <p>
   * If {@code storeKey} is empty, it is set to first element of {@code names}.
   * If {@code names} is empty or it's first element is empty, the first
   * element is set to the value of {@code storeKey}.
   * <p>
   * If {@code type} is not null but {@code hasArg} is false, {@code hasArg} is
   * set to true forcely.
   * Also, {@code type} is not null but {@code converter} is null,
   * {@code converter} is set to a converter which convert a string to a
   * specified type value.
   *
   * @param <T> The type of the option argument value.
   *
   * @param params  The variadic parameters of {@link NamedParam}.
   */
  @SafeVarargs
  public <T> OptCfg(NamedParam<T> ...params) {
    var init = new Init<T>();
    for (var param : params) {
      param.setTo(init);
    }

    fillmissing(init);

    this.storeKey = init.storeKey;
    this.names = unmodifiableList(init.names);
    this.hasArg = init.hasArg;
    this.isArray = init.isArray;
    this.type = init.type;
    this.defaults = unmodifiableList(init.defaults);
    this.desc = init.desc;
    this.argInHelp = init.argInHelp;
    this.converter = init.converter;
    this.postparser = init.postparser;
  }

  @SuppressWarnings("unchecked")
  private void fillmissing(Init<?> init) {
    if (isEmpty(init.storeKey)) {
      if (! isEmpty(init.names)) {
        init.storeKey = init.names.get(0);
      }
    } else {
      if (isEmpty(init.names)) {
        init.names = new ArrayList<String>();
        init.names.add(init.storeKey);
      } else if (isEmpty(init.names.get(0))) {
        init.names = new ArrayList<String>(init.names);
        init.names.set(0, init.storeKey);
      }
    }

    if (init.type != null && ! init.hasArg) {
      init.hasArg = true;
    }

    if (init.type != null && init.converter == null) {
      var type = init.type;
      if (type.equals(int.class) || type.equals(Integer.class)) {
        init.converter = (Converter)new IntConverter();
      } else if (type.equals(double.class) || type.equals(Double.class)) {
        init.converter = (Converter)new DoubleConverter();
      } else if (type.equals(long.class) || type.equals(Long.class)) {
        init.converter = (Converter)new LongConverter();
      } else if (type.equals(BigDecimal.class)) {
        init.converter = (Converter)new BigDecimalConverter();
      } else if (type.equals(BigInteger.class)) {
        init.converter = (Converter)new BigIntConverter();
      } else if (type.equals(float.class) || type.equals(Float.class)) {
        init.converter = (Converter)new FloatConverter();
      } else if (type.equals(short.class) || type.equals(Short.class)) {
        init.converter = (Converter)new ShortConverter();
      } else if (type.equals(byte.class) || type.equals(Byte.class)) {
        init.converter = (Converter)new ByteConverter();
      }
    }
  }

  private static class Init<T> {
    String storeKey;
    List<String> names = emptyList();
    boolean hasArg;
    boolean isArray;
    List<T> defaults = emptyList();
    Class<T> type;
    String desc;
    String argInHelp;
    Converter<T> converter;
    Postparser<T> postparser;
  }

  /**
   * Is the functional interface for the constructor parameters like named
   * parameters.
   *
   * @param <T> The type of the option argument value.
   */
  @SuppressWarnings("unchecked")
  @FunctionalInterface
  public interface NamedParam<T> {
    /**
     * Sets a field value of {@link Init} object that has same fields with
     * {@link OptCfg} object and is used to initialized it.
     *
     * @param init  An object to initialize {@link OptCfg} object.
     */
    void setTo(Object init);

    /**
     * Is the static method to set the {@code storeKey} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param storeKey  The value of the {@code storeKey} field.
     * @return  The {@link NamedParam} object for {@code storeKey} field.
     */
    static <T> NamedParam<T> storeKey(String storeKey) {
      return init -> ((Init<T>)init).storeKey = storeKey;
    }

    /**
     * Is the static method to set the {@code names} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param names  The string array of the {@code names} field.
     * @return  The {@link NamedParam} object for {@code storeKey} field.
     */
    @SuppressWarnings("unchecked")
    static <T> NamedParam<T> names(String ...names) {
      return init -> ((Init<T>)init).names = List.of(names);
    }

    /**
     * Is the static method to set the {@code names} field like a named
     * parameter.
     * 
     * @param <T> The type of the option argument value.
     *
     * @param list  The string list of the {@code names} field.
     * @return  The {@link NamedParam} object for {@code storeKey} field.
     */
    @SuppressWarnings("unchecked")
    static <T> NamedParam<T> names(List<String> list) {
      return init -> ((Init<T>)init).names = list;
    }

    /**
     * Is the static method to set the {@code hasArg} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param hasArg  The value of the {@code hasArg} field.
     * @return  The {@link NamedParam} object for {@code hasArg} field.
     */
    static <T> NamedParam<T> hasArg(boolean hasArg) {
      return init -> ((Init<T>)init).hasArg = hasArg;
    }

    /**
     * Is the static method to set the {@code isArray} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param isArray  The value of the {@code isArray} field.
     * @return  The {@link NamedParam} object for {@code isArray} field.
     */
    static <T> NamedParam<T> isArray(boolean isArray) {
      return init -> ((Init<T>)init).isArray = isArray;
    }

    /**
     * Is the static method to set the {@code type} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param type  The value of the {@code type} field.
     * @return  The {@link NamedParam} object for {@code type} field.
     */
    static <T> NamedParam<T> type(Class<T> type) {
      return init -> ((Init<T>)init).type = type;
    }

    /**
     * Is the static method to set the {@code defaults} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param defaults  The value of the {@code defaults} field.
     * @return  The {@link NamedParam} object for {@code defaults} field.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    static <T> NamedParam<T> defaults(T ...defaults) {
      return init -> ((Init<T>)init).defaults = List.of(defaults);
    }

    /**
     * Is the static method to set the {@code defaults} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param defaults  The value of the {@code defaults} field.
     * @return  The {@link NamedParam} object for {@code defaults} field.
     */
    static <T> NamedParam<T> defaults(List<T> defaults) {
      return init -> ((Init<T>)init).defaults = defaults;
    }

    /**
     * Is the static method to set the {@code desc} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param desc  The value of the {@code desc} field.
     * @return  The {@link NamedParam} object for {@code defaults} field.
     */
    static <T> NamedParam<T> desc(String desc) {
      return init -> ((Init<T>)init).desc = desc;
    }

    /**
     * Is the static method to set the {@code argInHelp} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param argInHelp  The value of the {@code argInHelp} field.
     * @return  The {@link NamedParam} object for {@code argInHelp} field.
     */
    static <T> NamedParam<T> argInHelp(String argInHelp) {
      return init -> ((Init<T>)init).argInHelp = argInHelp;
    }

    /**
     * Is the static method to set the {@code converter} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param converter  The value of the {@code converter} field.
     * @return  The {@link NamedParam} object for {@code converter} field.
     */
    static <T> NamedParam<T> converter(Converter<T> converter) {
      return init -> ((Init<T>)init).converter = converter;
    }

    /**
     * Is the static method to set the {@code postparser} field like a named
     * parameter.
     *
     * @param <T> The type of the option argument value.
     *
     * @param postparser  The value of the {@code postparser} field.
     * @return  The {@link NamedParam} object for {@code postparser} field.
     */
    static <T> NamedParam<T> postparser(Postparser<T> postparser) {
      return init -> ((Init<T>)init).postparser = postparser;
    }
  }

  /**
   * Is the functional interface to process option arguments after parsing
   * command line arguments.
   *
   * @param <T> The type of the option argument value.
   */
  @SuppressWarnings("unchecked")
  @FunctionalInterface
  public interface Postparser<T> {
    /**
     * Processes the option arguments.
     *
     * @param optArgs  The variadic parameters of the option arguments.
     * @throws ReasonedException  If an abnormality occurs during processing.
     */
    void process(List<T> optArgs) throws ReasonedException;
  }
}
