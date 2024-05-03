package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import static com.github.sttk.cliargs.OptCfg.Postparser;
import com.github.sttk.cliargs.convert.ByteConverter;
import com.github.sttk.cliargs.convert.ShortConverter;
import com.github.sttk.cliargs.convert.IntConverter;
import com.github.sttk.cliargs.convert.LongConverter;
import com.github.sttk.cliargs.convert.FloatConverter;
import com.github.sttk.cliargs.convert.DoubleConverter;
import com.github.sttk.exception.ReasonedException;
import java.util.List;
import java.time.OffsetDateTime;

@SuppressWarnings("missing-explicit-ctor")
public class OptCfgTest {

  @Test
  void testNormalConstructor_full() {
    var converter = new IntConverter();
    Postparser<Integer> postparser = i -> {};

    var optCfg = new OptCfg(
      "FooBar",
      List.of("foo-bar", "f"),
      true,
      true,
      Integer.class,
      List.of(123, 45),
      "The option description",
      "<num>",
      converter,
      postparser
    );

    assertThat(optCfg.storeKey).isEqualTo("FooBar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isTrue();
    assertThat(optCfg.type).isEqualTo(Integer.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(123);
    assertThat(optCfg.defaults.get(1)).isEqualTo(45);
    assertThat(optCfg.desc).isEqualTo("The option description");
    assertThat(optCfg.argInHelp).isEqualTo("<num>");
    assertThat(optCfg.converter).isEqualTo(converter);
    assertThat(optCfg.postparser).isEqualTo(postparser);
  }

  @Test
  void testNormalConstructor_null() {
    var converter = new IntConverter();
    Postparser<Integer> postparser = i -> {};

    var optCfg = new OptCfg(
      null,
      null,
      false,
      false,
      null,
      null,
      null,
      null,
      null,
      null
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNoNamedParam() {
    var optCfg = new OptCfg();

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_storeKey() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(storeKey("FooBar"));

    assertThat(optCfg.storeKey).isEqualTo("FooBar");
    assertThat(optCfg.names).containsExactly("FooBar");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_names() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(names("foo-bar", "f"));

    assertThat(optCfg.storeKey).isEqualTo("foo-bar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_nameList() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(names(List.of("foo-bar", "f")));

    assertThat(optCfg.storeKey).isEqualTo("foo-bar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_storeKeyAndNames() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(names("foo-bar", "f"), storeKey("FooBar"));

    assertThat(optCfg.storeKey).isEqualTo("FooBar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_storeKeyIsEmpty() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(storeKey(""), names("foo-bar", "f"));

    assertThat(optCfg.storeKey).isEqualTo("foo-bar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_firstElemOfNamesIsEmpty() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(storeKey("FooBar"), names("", "f"));

    assertThat(optCfg.storeKey).isEqualTo("FooBar");
    assertThat(optCfg.names).containsExactly("FooBar", "f");
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_hasArg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(hasArg(true));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_isArray() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(isArray(true));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isTrue();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_byte() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(byte.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(byte.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(ByteConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Byte() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Byte.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Byte.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(ByteConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_short() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(short.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(short.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(ShortConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Short() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Short.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Short.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(ShortConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_int() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(int.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(int.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(IntConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Integer() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Integer.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Integer.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(IntConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_long() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(long.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(long.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(LongConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Long() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Long.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Long.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(LongConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_float() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(float.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(float.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(FloatConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Float() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Float.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Float.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(FloatConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_double() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(double.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(double.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(DoubleConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Double() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Double.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Double.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(DoubleConverter.class);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_boolean() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(boolean.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(boolean.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_Boolean() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(Boolean.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Boolean.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_type_String() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(String.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(String.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNameParam_type_OffsetDateTime() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(type(OffsetDateTime.class));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(OffsetDateTime.class);
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_defaults() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(defaults(123, "45"));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(123);
    assertThat(optCfg.defaults.get(1)).isEqualTo("45");
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_defaultList() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(defaults(List.of(123, "45")));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(123);
    assertThat(optCfg.defaults.get(1)).isEqualTo("45");
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_desc() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(desc("option desc"));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isEqualTo("option desc");
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_argInHelp() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(argInHelp("<num>"));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isEqualTo("<num>");
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_converter() {
    var c = new LongConverter();

    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(converter(c));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isEqualTo(c);
    assertThat(optCfg.postparser).isNull();
  }

  @Test
  void testConstructor_withNamedParam_postparser() {
    Postparser<String> p = s -> {};

    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(postparser(p));

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isFalse();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isNull();
    assertThat(optCfg.defaults).isNull();
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isNull();
    assertThat(optCfg.postparser).isEqualTo(p);
  }

  @Test
  void testConstructor_withNamedParam_full() {
    var c = new IntConverter();
    Postparser<Integer> p = i -> {};

    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      desc("The option description"),
      isArray(true),
      names("foo-bar", "f"),
      storeKey("FooBar"),
      converter(c),
      postparser(p),
      hasArg(true),
      argInHelp("<num>"),
      type(int.class),
      defaults(123, 45)
    );

    assertThat(optCfg.storeKey).isEqualTo("FooBar");
    assertThat(optCfg.names).containsExactly("foo-bar", "f");
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isTrue();
    assertThat(optCfg.type).isEqualTo(int.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(123);
    assertThat(optCfg.defaults.get(1)).isEqualTo(45);
    assertThat(optCfg.desc).isEqualTo("The option description");
    assertThat(optCfg.argInHelp).isEqualTo("<num>");
    assertThat(optCfg.converter).isEqualTo(c);
    assertThat(optCfg.postparser).isEqualTo(p);
  }
}
