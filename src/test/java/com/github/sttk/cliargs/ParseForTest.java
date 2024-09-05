package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static com.github.sttk.cliargs.OptCfg.makeOptCfgsFor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.github.sttk.cliargs.annotations.Opt;
import com.github.sttk.cliargs.exceptions.FailToSetOptionStoreField;
import com.github.sttk.cliargs.exceptions.BadFieldType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

@SuppressWarnings("missing-explicit-ctor")
public class ParseForTest {

  @Nested
  class TestsNoAnnotation {
    class NoAnnotationOptions {
      boolean boolVal;
      String strObj;
      byte byteVal;
      short shortVal;
      int intVal;
      long longVal;
      float floatVal;
      double doubleVal;
      Byte byteObj;
      Short shortObj;
      Integer intObj;
      Long longObj;
      Float floatObj;
      Double doubleObj;
      BigInteger bigIntObj;
      BigDecimal bigDeclObj;
      String[] strObjArr;
      byte[] byteArr;
      short[] shortArr;
      int[] intArr;
      long[] longArr;
      float[] floatArr;
      double[] doubleArr;
      Byte[] byteObjArr;
      Short[] shortObjArr;
      Integer[] intObjArr;
      Long[] longObjArr;
      Float[] floatObjArr;
      Double[] doubleObjArr;
      BigInteger[] bigIntObjArr;
      BigDecimal[] bigDeclObjArr;
    }

    @Test
    void testCreateInstance() {
      var store = new NoAnnotationOptions();
      assertThat(store.boolVal).isFalse();
      assertThat(store.strObj).isNull();
      assertThat(store.byteVal).isEqualTo((byte)0);
      assertThat(store.shortVal).isEqualTo((short)0);
      assertThat(store.intVal).isEqualTo(0);
      assertThat(store.longVal).isEqualTo((long)0);
      assertThat(store.floatVal).isEqualTo((float)0);
      assertThat(store.doubleVal).isEqualTo((double)0);
      assertThat(store.byteObj).isNull();
      assertThat(store.shortObj).isNull();
      assertThat(store.intObj).isNull();
      assertThat(store.longObj).isNull();
      assertThat(store.floatObj).isNull();
      assertThat(store.doubleObj).isNull();
      assertThat(store.bigIntObj).isNull();
      assertThat(store.bigDeclObj).isNull();
      assertThat(store.strObjArr).isNull();
      assertThat(store.byteArr).isNull();
      assertThat(store.shortArr).isNull();
      assertThat(store.intArr).isNull();
      assertThat(store.longArr).isNull();
      assertThat(store.floatArr).isNull();
      assertThat(store.doubleArr).isNull();
      assertThat(store.byteObjArr).isNull();
      assertThat(store.shortObjArr).isNull();
      assertThat(store.intObjArr).isNull();
      assertThat(store.longObjArr).isNull();
      assertThat(store.floatObjArr).isNull();
      assertThat(store.doubleObjArr).isNull();
      assertThat(store.bigIntObjArr).isNull();
      assertThat(store.bigDeclObjArr).isNull();
    }

    @Test
    void testMakeOptCfgsForOptStore() {
      var store = new NoAnnotationOptions();
      var cfgs = makeOptCfgsFor(store);
      assertThat(cfgs).hasSize(31);

      var cfg = cfgs[0];
      assertThat(cfg.storeKey).isEqualTo("boolVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[1];
      assertThat(cfg.storeKey).isEqualTo("strObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[2];
      assertThat(cfg.storeKey).isEqualTo("byteVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[3];
      assertThat(cfg.storeKey).isEqualTo("shortVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[4];
      assertThat(cfg.storeKey).isEqualTo("intVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[5];
      assertThat(cfg.storeKey).isEqualTo("longVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[6];
      assertThat(cfg.storeKey).isEqualTo("floatVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[7];
      assertThat(cfg.storeKey).isEqualTo("doubleVal");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[8];
      assertThat(cfg.storeKey).isEqualTo("byteObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[9];
      assertThat(cfg.storeKey).isEqualTo("shortObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[10];
      assertThat(cfg.storeKey).isEqualTo("intObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[11];
      assertThat(cfg.storeKey).isEqualTo("longObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[12];
      assertThat(cfg.storeKey).isEqualTo("floatObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[13];
      assertThat(cfg.storeKey).isEqualTo("doubleObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[14];
      assertThat(cfg.storeKey).isEqualTo("bigIntObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[15];
      assertThat(cfg.storeKey).isEqualTo("bigDeclObj");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[16];
      assertThat(cfg.storeKey).isEqualTo("strObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[17];
      assertThat(cfg.storeKey).isEqualTo("byteArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[18];
      assertThat(cfg.storeKey).isEqualTo("shortArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[19];
      assertThat(cfg.storeKey).isEqualTo("intArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[20];
      assertThat(cfg.storeKey).isEqualTo("longArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[21];
      assertThat(cfg.storeKey).isEqualTo("floatArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[22];
      assertThat(cfg.storeKey).isEqualTo("doubleArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[23];
      assertThat(cfg.storeKey).isEqualTo("byteObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[24];
      assertThat(cfg.storeKey).isEqualTo("shortObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[25];
      assertThat(cfg.storeKey).isEqualTo("intObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[26];
      assertThat(cfg.storeKey).isEqualTo("longObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[27];
      assertThat(cfg.storeKey).isEqualTo("floatObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[28];
      assertThat(cfg.storeKey).isEqualTo("doubleObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[29];
      assertThat(cfg.storeKey).isEqualTo("bigIntObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[30];
      assertThat(cfg.storeKey).isEqualTo("bigDeclObjArr");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
    }

    @Test
    void testSetOptionStoreFieldValues_noOptValues() {
      var store = new NoAnnotationOptions();
      var cfgs = makeOptCfgsFor(store);
      var opts = new HashMap<String, List<String>>();

      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(store.boolVal).isFalse();
      assertThat(store.strObj).isNull();
      assertThat(store.byteVal).isEqualTo((byte)0);
      assertThat(store.shortVal).isEqualTo((short)0);
      assertThat(store.intVal).isEqualTo(0);
      assertThat(store.longVal).isEqualTo(0L);
      assertThat(store.floatVal).isEqualTo(0.0f);
      assertThat(store.doubleVal).isEqualTo(0.0);
      assertThat(store.byteObj).isNull();
      assertThat(store.shortObj).isNull();
      assertThat(store.intObj).isNull();
      assertThat(store.longObj).isNull();
      assertThat(store.floatObj).isNull();
      assertThat(store.doubleObj).isNull();
      assertThat(store.bigIntObj).isNull();
      assertThat(store.bigDeclObj).isNull();
      assertThat(store.strObjArr).isNull();
      assertThat(store.byteArr).isNull();
      assertThat(store.shortArr).isNull();
      assertThat(store.intArr).isNull();
      assertThat(store.longArr).isNull();
      assertThat(store.floatArr).isNull();
      assertThat(store.doubleArr).isNull();
      assertThat(store.byteObjArr).isNull();
      assertThat(store.shortObjArr).isNull();
      assertThat(store.intObjArr).isNull();
      assertThat(store.longObjArr).isNull();
      assertThat(store.floatObjArr).isNull();
      assertThat(store.doubleObjArr).isNull();
      assertThat(store.bigIntObjArr).isNull();
      assertThat(store.bigDeclObjArr).isNull();
    }

    @Test
    void testSetOptionStoreFieldValues_withOptValues() {
      var store = new NoAnnotationOptions();
      var cfgs = makeOptCfgsFor(store);

      var opts = new HashMap<String, List<String>>();
      opts.put("boolVal", List.of());
      opts.put("strObj", List.of("ABC"));
      opts.put("byteVal", List.of("12"));
      opts.put("shortVal", List.of("123"));
      opts.put("intVal", List.of("123"));
      opts.put("longVal", List.of("123"));
      opts.put("floatVal", List.of("1.23"));
      opts.put("doubleVal", List.of("1.23"));
      opts.put("byteObj", List.of("12"));
      opts.put("shortObj", List.of("123"));
      opts.put("intObj", List.of("123"));
      opts.put("longObj", List.of("123"));
      opts.put("floatObj", List.of("1.23"));
      opts.put("doubleObj", List.of("1.23"));
      opts.put("bigIntObj", List.of("123"));
      opts.put("bigDeclObj", List.of("123"));
      opts.put("strObjArr", List.of("ABC", "DEF"));
      opts.put("byteArr", List.of("123", "-123"));
      opts.put("shortArr", List.of("123", "456"));
      opts.put("intArr", List.of("123", "456"));
      opts.put("longArr", List.of("123", "456"));
      opts.put("floatArr", List.of("1.23", "4.56"));
      opts.put("doubleArr", List.of("1.23", "4.56"));
      opts.put("byteObjArr", List.of("123", "-123"));
      opts.put("shortObjArr", List.of("123", "456"));
      opts.put("intObjArr", List.of("123", "456"));
      opts.put("longObjArr", List.of("123", "456"));
      opts.put("floatObjArr", List.of("1.23", "4.56"));
      opts.put("doubleObjArr", List.of("1.23", "4.56"));
      opts.put("bigIntObjArr", List.of("123", "456"));
      opts.put("bigDeclObjArr", List.of("123", "456"));

      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        assertThat(store.boolVal).isTrue();
        assertThat(store.strObj).isEqualTo("ABC");
        assertThat(store.byteVal).isEqualTo((byte)12);
        assertThat(store.shortVal).isEqualTo((short)123);
        assertThat(store.intVal).isEqualTo(123);
        assertThat(store.longVal).isEqualTo(123L);
        assertThat(store.floatVal).isEqualTo(1.23f);
        assertThat(store.doubleVal).isEqualTo(1.23);
        assertThat(store.byteObj).isEqualTo((byte)12);
        assertThat(store.shortObj).isEqualTo((short)123);
        assertThat(store.intObj).isEqualTo(123);
        assertThat(store.longObj).isEqualTo(123L);
        assertThat(store.floatObj).isEqualTo(1.23f);
        assertThat(store.doubleObj).isEqualTo(1.23);
        assertThat(store.bigIntObj).isEqualTo(new BigInteger("123"));
        assertThat(store.bigDeclObj).isEqualTo(new BigDecimal("123"));
        assertThat(store.strObjArr).containsExactly("ABC", "DEF");
        assertThat(store.byteArr).containsExactly((byte)123, (byte)-123);
        assertThat(store.shortArr).containsExactly((short)123, (short)456);
        assertThat(store.intArr).containsExactly(123, 456);
        assertThat(store.longArr).containsExactly(123L, 456L);
        assertThat(store.floatArr).containsExactly(1.23f, 4.56f);
        assertThat(store.doubleArr).containsExactly(1.23, 4.56);
        assertThat(store.byteObjArr).containsExactly((byte)123, (byte)-123);
        assertThat(store.shortObjArr).containsExactly((short)123, (short)456);
        assertThat(store.intObjArr).containsExactly(123, 456);
        assertThat(store.longObjArr).containsExactly(123L, 456L);
        assertThat(store.floatObjArr).containsExactly(1.23f, 4.56f);
        assertThat(store.doubleObjArr).containsExactly(1.23, 4.56);
        assertThat(store.bigIntObjArr)
          .containsExactly(new BigInteger("123"), new BigInteger("456"));
        assertThat(store.bigDeclObjArr)
          .containsExactly(new BigDecimal("123"), new BigDecimal("456"));
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void testSetOptionStoreFieldValue_if_number_format_error() {
      var store = new NoAnnotationOptions();
      var cfgs = makeOptCfgsFor(store);

      var opts = new HashMap<String, List<String>>();
      opts.put("byteVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("byteVal");
        assertThat(e.type).isEqualTo(byte.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("shortVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("shortVal");
        assertThat(e.type).isEqualTo(short.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("intVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("intVal");
        assertThat(e.type).isEqualTo(int.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("longVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("longVal");
        assertThat(e.type).isEqualTo(long.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("floatVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("floatVal");
        assertThat(e.type).isEqualTo(float.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("doubleVal", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("doubleVal");
        assertThat(e.type).isEqualTo(double.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("byteObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("byteObj");
        assertThat(e.type).isEqualTo(Byte.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("shortObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("shortObj");
        assertThat(e.type).isEqualTo(Short.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("intObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("intObj");
        assertThat(e.type).isEqualTo(Integer.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("longObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("longObj");
        assertThat(e.type).isEqualTo(Long.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("floatObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("floatObj");
        assertThat(e.type).isEqualTo(Float.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("doubleObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("doubleObj");
        assertThat(e.type).isEqualTo(Double.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("bigIntObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("bigIntObj");
        assertThat(e.type).isEqualTo(BigInteger.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("bigDeclObj", List.of("xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("bigDeclObj");
        assertThat(e.type).isEqualTo(BigDecimal.class);
        assertThat(e.optArgs).containsExactly("xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("byteArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("byteArr");
        assertThat(e.type).isEqualTo(byte[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("shortArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("shortArr");
        assertThat(e.type).isEqualTo(short[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("intArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("intArr");
        assertThat(e.type).isEqualTo(int[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("longArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("longArr");
        assertThat(e.type).isEqualTo(long[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("floatArr", List.of("1.23", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("floatArr");
        assertThat(e.type).isEqualTo(float[].class);
        assertThat(e.optArgs).containsExactly("1.23", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("doubleArr", List.of("1.23", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("doubleArr");
        assertThat(e.type).isEqualTo(double[].class);
        assertThat(e.optArgs).containsExactly("1.23", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("byteObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("byteObjArr");
        assertThat(e.type).isEqualTo(Byte[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("shortObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("shortObjArr");
        assertThat(e.type).isEqualTo(Short[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("intObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("intObjArr");
        assertThat(e.type).isEqualTo(Integer[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("longObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("longObjArr");
        assertThat(e.type).isEqualTo(Long[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("floatObjArr", List.of("1.23", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("floatObjArr");
        assertThat(e.type).isEqualTo(Float[].class);
        assertThat(e.optArgs).containsExactly("1.23", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("doubleObjArr", List.of("1.23", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("doubleObjArr");
        assertThat(e.type).isEqualTo(Double[].class);
        assertThat(e.optArgs).containsExactly("1.23", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("bigIntObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("bigIntObjArr");
        assertThat(e.type).isEqualTo(BigInteger[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }

      opts.clear();
      opts.put("bigDeclObjArr", List.of("123", "xxx"));
      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("bigDeclObjArr");
        assertThat(e.type).isEqualTo(BigDecimal[].class);
        assertThat(e.optArgs).containsExactly("123", "xxx");
        assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      }
    }

    @Test
    void parseFor_noOpts() {
      var optStore = new NoAnnotationOptions();

      var cmd = new Cmd("path/to/app");
      try {
        cmd.parseFor(optStore);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);

      assertThat(cmd.hasOpt("boolVal")).isFalse();
      assertThat(cmd.hasOpt("strObj")).isFalse();
      assertThat(cmd.hasOpt("byteVal")).isFalse();
      assertThat(cmd.hasOpt("shortVal")).isFalse();
      assertThat(cmd.hasOpt("intVal")).isFalse();
      assertThat(cmd.hasOpt("longVal")).isFalse();
      assertThat(cmd.hasOpt("floatVal")).isFalse();
      assertThat(cmd.hasOpt("doubleVal")).isFalse();
      assertThat(cmd.hasOpt("byteObj")).isFalse();
      assertThat(cmd.hasOpt("shortObj")).isFalse();
      assertThat(cmd.hasOpt("intObj")).isFalse();
      assertThat(cmd.hasOpt("longObj")).isFalse();
      assertThat(cmd.hasOpt("floatObj")).isFalse();
      assertThat(cmd.hasOpt("doubleObj")).isFalse();
      assertThat(cmd.hasOpt("bigIntObj")).isFalse();
      assertThat(cmd.hasOpt("bigDeclObj")).isFalse();
      assertThat(cmd.hasOpt("strObjArr")).isFalse();
      assertThat(cmd.hasOpt("byteArr")).isFalse();
      assertThat(cmd.hasOpt("shortArr")).isFalse();
      assertThat(cmd.hasOpt("intArr")).isFalse();
      assertThat(cmd.hasOpt("longArr")).isFalse();
      assertThat(cmd.hasOpt("floatArr")).isFalse();
      assertThat(cmd.hasOpt("doubleArr")).isFalse();
      assertThat(cmd.hasOpt("byteObjArr")).isFalse();
      assertThat(cmd.hasOpt("shortObjArr")).isFalse();
      assertThat(cmd.hasOpt("intObjArr")).isFalse();
      assertThat(cmd.hasOpt("longObjArr")).isFalse();
      assertThat(cmd.hasOpt("floatObjArr")).isFalse();
      assertThat(cmd.hasOpt("doubleObjArr")).isFalse();
      assertThat(cmd.hasOpt("bigIntObjArr")).isFalse();
      assertThat(cmd.hasOpt("bigDeclObjArr")).isFalse();

      assertThat(optStore.boolVal).isFalse();
      assertThat(optStore.strObj).isNull();
      assertThat(optStore.byteVal).isEqualTo((byte)0);
      assertThat(optStore.shortVal).isEqualTo((short)0);
      assertThat(optStore.intVal).isEqualTo(0);
      assertThat(optStore.longVal).isEqualTo(0L);
      assertThat(optStore.floatVal).isEqualTo(0.0f);
      assertThat(optStore.doubleVal).isEqualTo(0.0);
      assertThat(optStore.byteObj).isNull();
      assertThat(optStore.shortObj).isNull();
      assertThat(optStore.intObj).isNull();
      assertThat(optStore.longObj).isNull();
      assertThat(optStore.floatObj).isNull();
      assertThat(optStore.doubleObj).isNull();
      assertThat(optStore.bigIntObj).isNull();
      assertThat(optStore.bigDeclObj).isNull();
      assertThat(optStore.strObjArr).isNull();
      assertThat(optStore.byteArr).isNull();
      assertThat(optStore.shortArr).isNull();
      assertThat(optStore.intArr).isNull();
      assertThat(optStore.longArr).isNull();
      assertThat(optStore.floatArr).isNull();
      assertThat(optStore.doubleArr).isNull();
      assertThat(optStore.byteObjArr).isNull();
      assertThat(optStore.shortObjArr).isNull();
      assertThat(optStore.intObjArr).isNull();
      assertThat(optStore.longObjArr).isNull();
      assertThat(optStore.floatObjArr).isNull();
      assertThat(optStore.doubleObjArr).isNull();
      assertThat(optStore.bigIntObjArr).isNull();
      assertThat(optStore.bigDeclObjArr).isNull();
    }

    @Test
    void parseFor_fullOpts() {
      var optStore = new NoAnnotationOptions();

      var cmd = new Cmd(
        "path/to/app",
        "--boolVal",
        "--strObj", "ABC",
        "--byteVal", "12",
        "--shortVal", "123",
        "--intVal", "1234",
        "--longVal", "12345",
        "--floatVal", "1.23",
        "--doubleVal", "123.456",
        "--byteObj", "12",
        "--shortObj", "123",
        "--intObj", "1234",
        "--longObj", "12345",
        "--floatObj", "1.23",
        "--doubleObj", "123.456",
        "--bigIntObj", "123",
        "--bigDeclObj", "1234",
        "--strObjArr", "ABC", "--strObjArr=DEF",
        "--byteArr", "12", "--byteArr=34",
        "--shortArr", "123", "--shortArr=456",
        "--intArr", "123", "--intArr=456",
        "--longArr", "123", "--longArr=456",
        "--floatArr", "1.23", "--floatArr=4.56",
        "--doubleArr", "1.23", "--doubleArr=4.56",
        "--byteObjArr", "12", "--byteObjArr=34",
        "--shortObjArr", "123", "--shortObjArr=456",
        "--intObjArr", "123", "--intObjArr=456",
        "--longObjArr", "123", "--longObjArr=456",
        "--floatObjArr", "1.23", "--floatObjArr=4.56",
        "--doubleObjArr", "1.23", "--doubleObjArr=4.56",
        "--bigIntObjArr", "123", "--bigIntObjArr=456",
        "--bigDeclObjArr", "123", "--bigDeclObjArr=456",
        "xxx"
      );
      try {
        cmd.parseFor(optStore);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("xxx");

      assertThat(cmd.hasOpt("boolVal")).isTrue();
      assertThat(cmd.optArg("boolVal").isPresent()).isFalse();
      assertThat(cmd.optArgs("boolVal").get()).hasSize(0);
      assertThat(cmd.hasOpt("strObj")).isTrue();
      assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("byteVal")).isTrue();
      assertThat(cmd.optArg("byteVal").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteVal").get()).containsExactly("12");
      assertThat(cmd.hasOpt("shortVal")).isTrue();
      assertThat(cmd.optArg("shortVal").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortVal").get()).containsExactly("123");
      assertThat(cmd.hasOpt("intVal")).isTrue();
      assertThat(cmd.optArg("intVal").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("intVal").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("longVal")).isTrue();
      assertThat(cmd.optArg("longVal").get()).isEqualTo("12345");
      assertThat(cmd.optArgs("longVal").get()).containsExactly("12345");
      assertThat(cmd.hasOpt("floatVal")).isTrue();
      assertThat(cmd.optArg("floatVal").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatVal").get()).containsExactly("1.23");
      assertThat(cmd.hasOpt("doubleVal")).isTrue();
      assertThat(cmd.optArg("doubleVal").get()).isEqualTo("123.456");
      assertThat(cmd.optArgs("doubleVal").get()).containsExactly("123.456");
      assertThat(cmd.hasOpt("byteObj")).isTrue();
      assertThat(cmd.optArg("byteObj").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteObj").get()).containsExactly("12");
      assertThat(cmd.hasOpt("shortObj")).isTrue();
      assertThat(cmd.optArg("shortObj").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortObj").get()).containsExactly("123");
      assertThat(cmd.hasOpt("intObj")).isTrue();
      assertThat(cmd.optArg("intObj").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("intObj").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("longObj")).isTrue();
      assertThat(cmd.optArg("longObj").get()).isEqualTo("12345");
      assertThat(cmd.optArgs("longObj").get()).containsExactly("12345");
      assertThat(cmd.hasOpt("floatObj")).isTrue();
      assertThat(cmd.optArg("floatObj").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatObj").get()).containsExactly("1.23");
      assertThat(cmd.hasOpt("doubleObj")).isTrue();
      assertThat(cmd.optArg("doubleObj").get()).isEqualTo("123.456");
      assertThat(cmd.optArgs("doubleObj").get()).containsExactly("123.456");
      assertThat(cmd.hasOpt("bigIntObj")).isTrue();
      assertThat(cmd.optArg("bigIntObj").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigIntObj").get()).containsExactly("123");
      assertThat(cmd.hasOpt("bigDeclObj")).isTrue();
      assertThat(cmd.optArg("bigDeclObj").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("bigDeclObj").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("strObjArr")).isTrue();
      assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
      assertThat(cmd.hasOpt("byteArr")).isTrue();
      assertThat(cmd.optArg("byteArr").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteArr").get()).containsExactly("12", "34");
      assertThat(cmd.hasOpt("shortArr")).isTrue();
      assertThat(cmd.optArg("shortArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("intArr")).isTrue();
      assertThat(cmd.optArg("intArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("longArr")).isTrue();
      assertThat(cmd.optArg("longArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("longArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("floatArr")).isTrue();
      assertThat(cmd.optArg("floatArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("doubleArr")).isTrue();
      assertThat(cmd.optArg("doubleArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("doubleArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("byteObjArr")).isTrue();
      assertThat(cmd.optArg("byteObjArr").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteObjArr").get()).containsExactly("12", "34");
      assertThat(cmd.hasOpt("shortObjArr")).isTrue();
      assertThat(cmd.optArg("shortObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("intObjArr")).isTrue();
      assertThat(cmd.optArg("intObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("longObjArr")).isTrue();
      assertThat(cmd.optArg("longObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("longObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("floatObjArr")).isTrue();
      assertThat(cmd.optArg("floatObjArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatObjArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("doubleObjArr")).isTrue();
      assertThat(cmd.optArg("doubleObjArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("doubleObjArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("bigIntObjArr")).isTrue();
      assertThat(cmd.optArg("bigIntObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigIntObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("bigDeclObjArr")).isTrue();
      assertThat(cmd.optArg("bigDeclObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigDeclObjArr").get()).containsExactly("123", "456");

      assertThat(optStore.boolVal).isTrue();
      assertThat(optStore.strObj).isEqualTo("ABC");
      assertThat(optStore.byteVal).isEqualTo((byte)12);
      assertThat(optStore.shortVal).isEqualTo((short)123);
      assertThat(optStore.intVal).isEqualTo(1234);
      assertThat(optStore.longVal).isEqualTo(12345L);
      assertThat(optStore.floatVal).isEqualTo(1.23f);
      assertThat(optStore.doubleVal).isEqualTo(123.456);
      assertThat(optStore.byteObj).isEqualTo((byte)12);
      assertThat(optStore.shortObj).isEqualTo((short)123);
      assertThat(optStore.intObj).isEqualTo(1234);
      assertThat(optStore.longObj).isEqualTo(12345L);
      assertThat(optStore.floatObj).isEqualTo(1.23f);
      assertThat(optStore.doubleObj).isEqualTo(123.456);
      assertThat(optStore.bigIntObj).isEqualTo(new BigInteger("123"));
      assertThat(optStore.bigDeclObj).isEqualTo(new BigDecimal("1234"));
      assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
      assertThat(optStore.byteArr).containsExactly((byte)12, (byte)34);
      assertThat(optStore.shortArr).containsExactly((short)123, (short)456);
      assertThat(optStore.intArr).containsExactly(123, 456);
      assertThat(optStore.longArr).containsExactly(123L, 456L);
      assertThat(optStore.floatArr).containsExactly(1.23f, 4.56f);
      assertThat(optStore.doubleArr).containsExactly(1.23, 4.56);
      assertThat(optStore.byteObjArr).containsExactly((byte)12, (byte)34);
      assertThat(optStore.shortObjArr).containsExactly((short)123, (short)456);
      assertThat(optStore.intObjArr).containsExactly(123, 456);
      assertThat(optStore.longObjArr).containsExactly(123L, 456L);
      assertThat(optStore.floatObjArr).containsExactly(1.23f, 4.56f);
      assertThat(optStore.doubleObjArr).containsExactly(1.23, 4.56);
      assertThat(optStore.bigIntObjArr)
        .containsExactly(new BigInteger("123"), new BigInteger("456"));
      assertThat(optStore.bigDeclObjArr)
        .containsExactly(new BigDecimal("123"), new BigDecimal("456"));
    }

    @Test
    void parseUntilSubCmdFor_noSubCmd() {
      var optStore = new NoAnnotationOptions();
      var cmd = new Cmd("path/to/app");

      try {
        var optional = cmd.parseUntilSubCmdFor(optStore);
        assertThat(optional.isPresent()).isFalse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
  
        assertThat(cmd.hasOpt("boolVal")).isFalse();
        assertThat(cmd.hasOpt("strObj")).isFalse();
        assertThat(cmd.hasOpt("byteVal")).isFalse();
        assertThat(cmd.hasOpt("shortVal")).isFalse();
        assertThat(cmd.hasOpt("intVal")).isFalse();
        assertThat(cmd.hasOpt("longVal")).isFalse();
        assertThat(cmd.hasOpt("floatVal")).isFalse();
        assertThat(cmd.hasOpt("doubleVal")).isFalse();
        assertThat(cmd.hasOpt("byteObj")).isFalse();
        assertThat(cmd.hasOpt("shortObj")).isFalse();
        assertThat(cmd.hasOpt("intObj")).isFalse();
        assertThat(cmd.hasOpt("longObj")).isFalse();
        assertThat(cmd.hasOpt("floatObj")).isFalse();
        assertThat(cmd.hasOpt("doubleObj")).isFalse();
        assertThat(cmd.hasOpt("bigIntObj")).isFalse();
        assertThat(cmd.hasOpt("bigDeclObj")).isFalse();
        assertThat(cmd.hasOpt("strObjArr")).isFalse();
        assertThat(cmd.hasOpt("byteArr")).isFalse();
        assertThat(cmd.hasOpt("shortArr")).isFalse();
        assertThat(cmd.hasOpt("intArr")).isFalse();
        assertThat(cmd.hasOpt("longArr")).isFalse();
        assertThat(cmd.hasOpt("floatArr")).isFalse();
        assertThat(cmd.hasOpt("doubleArr")).isFalse();
        assertThat(cmd.hasOpt("bigIntObjArr")).isFalse();
        assertThat(cmd.hasOpt("bigDeclObjArr")).isFalse();
  
        assertThat(optStore.boolVal).isFalse();
        assertThat(optStore.strObj).isNull();
        assertThat(optStore.byteVal).isEqualTo((byte)0);
        assertThat(optStore.shortVal).isEqualTo((short)0);
        assertThat(optStore.intVal).isEqualTo(0);
        assertThat(optStore.longVal).isEqualTo(0L);
        assertThat(optStore.floatVal).isEqualTo(0.0f);
        assertThat(optStore.doubleVal).isEqualTo(0.0);
        assertThat(optStore.byteObj).isNull();
        assertThat(optStore.shortObj).isNull();
        assertThat(optStore.intObj).isNull();
        assertThat(optStore.longObj).isNull();
        assertThat(optStore.floatObj).isNull();
        assertThat(optStore.doubleObj).isNull();
        assertThat(optStore.bigIntObj).isNull();
        assertThat(optStore.bigDeclObj).isNull();
        assertThat(optStore.strObjArr).isNull();
        assertThat(optStore.byteArr).isNull();
        assertThat(optStore.shortArr).isNull();
        assertThat(optStore.intArr).isNull();
        assertThat(optStore.longArr).isNull();
        assertThat(optStore.floatArr).isNull();
        assertThat(optStore.doubleArr).isNull();
        assertThat(optStore.byteObjArr).isNull();
        assertThat(optStore.shortObjArr).isNull();
        assertThat(optStore.intObjArr).isNull();
        assertThat(optStore.longObjArr).isNull();
        assertThat(optStore.floatObjArr).isNull();
        assertThat(optStore.doubleObjArr).isNull();
        assertThat(optStore.bigIntObjArr).isNull();
        assertThat(optStore.bigDeclObjArr).isNull();
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void parseUntilSubCmdFor_withSubCmd() {
      var optStore = new NoAnnotationOptions();

      var cmd = new Cmd(
        "path/to/app",
        "--boolVal",
        "--strObj", "ABC",
        "--byteVal", "12",
        "--shortVal", "123",
        "--intVal", "1234",
        "--longVal", "12345",
        "--floatVal", "1.23",
        "--doubleVal", "123.456",
        "--byteObj", "12",
        "--shortObj", "123",
        "--intObj", "1234",
        "--longObj", "12345",
        "--floatObj", "1.23",
        "--doubleObj", "123.456",
        "--bigIntObj", "123",
        "--bigDeclObj", "1234",
        "--strObjArr", "ABC", "--strObjArr=DEF",
        "--byteArr", "12", "--byteArr=34",
        "--shortArr", "123", "--shortArr=456",
        "--intArr", "123", "--intArr=456",
        "--longArr", "123", "--longArr=456",
        "--floatArr", "1.23", "--floatArr=4.56",
        "--doubleArr", "1.23", "--doubleArr=4.56",
        "--byteObjArr", "12", "--byteObjArr=34",
        "--shortObjArr", "123", "--shortObjArr=456",
        "--intObjArr", "123", "--intObjArr=456",
        "--longObjArr", "123", "--longObjArr=456",
        "--floatObjArr", "1.23", "--floatObjArr=4.56",
        "--doubleObjArr", "1.23", "--doubleObjArr=4.56",
        "--bigIntObjArr", "123", "--bigIntObjArr=456",
        "--bigDeclObjArr", "123", "--bigDeclObjArr=456",
        "subsub",
        "argarg"
      );
      try {
        var optional = cmd.parseUntilSubCmdFor(optStore);
        assertThat(optional.isPresent()).isTrue();

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
  
        assertThat(cmd.hasOpt("boolVal")).isTrue();
        assertThat(cmd.optArg("boolVal").isPresent()).isFalse();
        assertThat(cmd.optArgs("boolVal").get()).hasSize(0);
        assertThat(cmd.hasOpt("strObj")).isTrue();
        assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
        assertThat(cmd.hasOpt("byteVal")).isTrue();
        assertThat(cmd.optArg("byteVal").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteVal").get()).containsExactly("12");
        assertThat(cmd.hasOpt("shortVal")).isTrue();
        assertThat(cmd.optArg("shortVal").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortVal").get()).containsExactly("123");
        assertThat(cmd.hasOpt("intVal")).isTrue();
        assertThat(cmd.optArg("intVal").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("intVal").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("longVal")).isTrue();
        assertThat(cmd.optArg("longVal").get()).isEqualTo("12345");
        assertThat(cmd.optArgs("longVal").get()).containsExactly("12345");
        assertThat(cmd.hasOpt("floatVal")).isTrue();
        assertThat(cmd.optArg("floatVal").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatVal").get()).containsExactly("1.23");
        assertThat(cmd.hasOpt("doubleVal")).isTrue();
        assertThat(cmd.optArg("doubleVal").get()).isEqualTo("123.456");
        assertThat(cmd.optArgs("doubleVal").get()).containsExactly("123.456");
        assertThat(cmd.hasOpt("byteObj")).isTrue();
        assertThat(cmd.optArg("byteObj").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteObj").get()).containsExactly("12");
        assertThat(cmd.hasOpt("shortObj")).isTrue();
        assertThat(cmd.optArg("shortObj").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortObj").get()).containsExactly("123");
        assertThat(cmd.hasOpt("intObj")).isTrue();
        assertThat(cmd.optArg("intObj").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("intObj").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("longObj")).isTrue();
        assertThat(cmd.optArg("longObj").get()).isEqualTo("12345");
        assertThat(cmd.optArgs("longObj").get()).containsExactly("12345");
        assertThat(cmd.hasOpt("floatObj")).isTrue();
        assertThat(cmd.optArg("floatObj").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatObj").get()).containsExactly("1.23");
        assertThat(cmd.hasOpt("doubleObj")).isTrue();
        assertThat(cmd.optArg("doubleObj").get()).isEqualTo("123.456");
        assertThat(cmd.optArgs("doubleObj").get()).containsExactly("123.456");
        assertThat(cmd.hasOpt("bigIntObj")).isTrue();
        assertThat(cmd.optArg("bigIntObj").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigIntObj").get()).containsExactly("123");
        assertThat(cmd.hasOpt("bigDeclObj")).isTrue();
        assertThat(cmd.optArg("bigDeclObj").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("bigDeclObj").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("strObjArr")).isTrue();
        assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
        assertThat(cmd.hasOpt("byteArr")).isTrue();
        assertThat(cmd.optArg("byteArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("shortArr")).isTrue();
        assertThat(cmd.optArg("shortArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("intArr")).isTrue();
        assertThat(cmd.optArg("intArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("longArr")).isTrue();
        assertThat(cmd.optArg("longArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("longArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("floatArr")).isTrue();
        assertThat(cmd.optArg("floatArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("doubleArr")).isTrue();
        assertThat(cmd.optArg("doubleArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("doubleArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("byteObjArr")).isTrue();
        assertThat(cmd.optArg("byteObjArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteObjArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("shortObjArr")).isTrue();
        assertThat(cmd.optArg("shortObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("intObjArr")).isTrue();
        assertThat(cmd.optArg("intObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("longObjArr")).isTrue();
        assertThat(cmd.optArg("longObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("longObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("floatObjArr")).isTrue();
        assertThat(cmd.optArg("floatObjArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatObjArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("doubleObjArr")).isTrue();
        assertThat(cmd.optArg("doubleObjArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("doubleObjArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("bigIntObjArr")).isTrue();
        assertThat(cmd.optArg("bigIntObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigIntObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("bigDeclObjArr")).isTrue();
        assertThat(cmd.optArg("bigDeclObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigDeclObjArr").get()).containsExactly("123", "456");
  
        assertThat(optStore.boolVal).isTrue();
        assertThat(optStore.strObj).isEqualTo("ABC");
        assertThat(optStore.byteVal).isEqualTo((byte)12);
        assertThat(optStore.shortVal).isEqualTo((short)123);
        assertThat(optStore.intVal).isEqualTo(1234);
        assertThat(optStore.longVal).isEqualTo(12345L);
        assertThat(optStore.floatVal).isEqualTo(1.23f);
        assertThat(optStore.doubleVal).isEqualTo(123.456);
        assertThat(optStore.byteObj).isEqualTo((byte)12);
        assertThat(optStore.shortObj).isEqualTo((short)123);
        assertThat(optStore.intObj).isEqualTo(1234);
        assertThat(optStore.longObj).isEqualTo(12345L);
        assertThat(optStore.floatObj).isEqualTo(1.23f);
        assertThat(optStore.doubleObj).isEqualTo(123.456);
        assertThat(optStore.bigIntObj).isEqualTo(new BigInteger("123"));
        assertThat(optStore.bigDeclObj).isEqualTo(new BigDecimal("1234"));
        assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
        assertThat(optStore.byteArr).containsExactly((byte)12, (byte)34);
        assertThat(optStore.shortArr).containsExactly((short)123, (short)456);
        assertThat(optStore.intArr).containsExactly(123, 456);
        assertThat(optStore.longArr).containsExactly(123L, 456L);
        assertThat(optStore.floatArr).containsExactly(1.23f, 4.56f);
        assertThat(optStore.doubleArr).containsExactly(1.23, 4.56);
        assertThat(optStore.byteObjArr).containsExactly((byte)12, (byte)34);
        assertThat(optStore.shortObjArr).containsExactly((short)123, (short)456);
        assertThat(optStore.intObjArr).containsExactly(123, 456);
        assertThat(optStore.longObjArr).containsExactly(123L, 456L);
        assertThat(optStore.floatObjArr).containsExactly(1.23f, 4.56f);
        assertThat(optStore.doubleObjArr).containsExactly(1.23, 4.56);
        assertThat(optStore.bigIntObjArr)
          .containsExactly(new BigInteger("123"), new BigInteger("456"));
        assertThat(optStore.bigDeclObjArr)
          .containsExactly(new BigDecimal("123"), new BigDecimal("456"));

        assertThat(subCmd.name()).isEqualTo("subsub");
        assertThat(subCmd.args()).contains("argarg");
      } catch (Exception e) {
        fail(e);
      }
    }
  }

  @Nested
  class TestsWithAnnotation {
    class WithAnnotationOptions {
      @Opt(cfg="b,bool", desc="The description of boolVal")
      boolean boolVal;

      @Opt(cfg="s,str=ABC", desc="The description of strObj", arg="txt")
      String strObj;

      @Opt(cfg="byte", desc="The description of byteVal", arg="<n>")
      byte byteVal;

      @Opt(cfg="short", desc="The description of shortVal", arg="<n>")
      short shortVal;

      @Opt(cfg="i,int=123", desc="The description of intVal", arg="<n>")
      int intVal;

      @Opt(cfg="long", desc="The description of longVal", arg="<n>")
      long longVal;

      @Opt(cfg="f,float", desc="The description of floatVal", arg="<n>")
      float floatVal;

      @Opt(cfg="d,double", desc="The description of doubleVal", arg="<n>")
      double doubleVal;

      @Opt(cfg="byte-obj", desc="The description of byteObj", arg="<n>")
      Byte byteObj;

      @Opt(cfg="short-obj", desc="The description of shortObj", arg="<n>")
      Short shortObj;

      @Opt(cfg="int-obj=123", desc="The description of intObj", arg="<n>")
      Integer intObj;

      @Opt(cfg="long-obj", desc="The description of longObj", arg="<n>")
      Long longObj;

      @Opt(cfg="float-obj", desc="The description of floatObj", arg="<n>")
      Float floatObj;

      @Opt(cfg="double-obj", desc="The description of doubleObj", arg="<n>")
      Double doubleObj;

      @Opt(cfg="big-int", desc="The description of bigIntObj", arg="<n>")
      BigInteger bigIntObj;

      @Opt(cfg="big-decl", desc="The description of bigDeclObj", arg="<n>")
      BigDecimal bigDeclObj;

      @Opt(cfg="str-arr=[ABC,DEF]", desc="The description of strObjArr", arg="txt")
      String[] strObjArr;

      @Opt(cfg="byte-arr", desc="The description of byteArr", arg="<n>")
      byte[] byteArr;

      @Opt(cfg="short-arr", desc="The description of shortArr", arg="<n>")
      short[] shortArr;

      @Opt(cfg="int-arr=[12,34]", desc="The description of intArr", arg="<n>")
      int[] intArr;

      @Opt(cfg="long-arr", desc="The description of longArr", arg="<n>")
      long[] longArr;

      @Opt(cfg="float-arr", desc="The description of floatArr", arg="<n>")
      float[] floatArr;

      @Opt(cfg="double-arr", desc="The description of doubleArr", arg="<n>")
      double[] doubleArr;

      @Opt(cfg="byte-obj-arr", desc="The description of byteObjArr", arg="<n>")
      Byte[] byteObjArr;

      @Opt(cfg="short-obj-arr", desc="The description of shortObjArr", arg="<n>")
      Short[] shortObjArr;

      @Opt(cfg="int-obj-arr=/[12/34]", desc="The description of intObjArr", arg="<n>")
      Integer[] intObjArr;

      @Opt(cfg="long-obj-arr", desc="The description of longObjArr", arg="<n>")
      Long[] longObjArr;

      @Opt(cfg="float-obj-arr", desc="The description of floatObjArr", arg="<n>")
      Float[] floatObjArr;

      @Opt(cfg="double-obj-arr", desc="The description of doubleObjArr", arg="<n>")
      Double[] doubleObjArr;

      @Opt(cfg="big-int-arr", desc="The description of bigIntObjArr", arg="<n>")
      BigInteger[] bigIntObjArr;

      @Opt(cfg="big-decl-arr", desc="The description of bigDeclObjArr", arg="<n>")
      BigDecimal[] bigDeclObjArr;

      @Opt(cfg="=[]")
      String[] empty;

      @Opt(cfg="=/[]")
      String[] emptyWithDelimiter;

      @Opt(cfg="single-empty=")
      String[] singleEmptyElem;

      @Opt(cfg="=[")
      String[] onlyOpenParenthesis;

      @Opt(cfg="=]")
      String[] onlyCloseParenthesis;
    }

    @Test
    void testMakeOptCfgsForOptStore() {
      var store = new WithAnnotationOptions();
      var cfgs = makeOptCfgsFor(store);
      assertThat(cfgs).hasSize(36);

      var cfg = cfgs[0];
      assertThat(cfg.storeKey).isEqualTo("boolVal");
      assertThat(cfg.names).containsExactly("b", "bool");
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of boolVal");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[1];
      assertThat(cfg.storeKey).isEqualTo("strObj");
      assertThat(cfg.names).containsExactly("s", "str");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.get()).containsExactly("ABC");
      assertThat(cfg.desc).isEqualTo("The description of strObj");
      assertThat(cfg.argInHelp).isEqualTo("txt");

      cfg = cfgs[2];
      assertThat(cfg.storeKey).isEqualTo("byteVal");
      assertThat(cfg.names).containsExactly("byte");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of byteVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[3];
      assertThat(cfg.storeKey).isEqualTo("shortVal");
      assertThat(cfg.names).containsExactly("short");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of shortVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[4];
      assertThat(cfg.storeKey).isEqualTo("intVal");
      assertThat(cfg.names).containsExactly("i", "int");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.get()).containsExactly("123");
      assertThat(cfg.desc).isEqualTo("The description of intVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[5];
      assertThat(cfg.storeKey).isEqualTo("longVal");
      assertThat(cfg.names).containsExactly("long");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of longVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[6];
      assertThat(cfg.storeKey).isEqualTo("floatVal");
      assertThat(cfg.names).containsExactly("f", "float");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of floatVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[7];
      assertThat(cfg.storeKey).isEqualTo("doubleVal");
      assertThat(cfg.names).containsExactly("d", "double");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of doubleVal");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[8];
      assertThat(cfg.storeKey).isEqualTo("byteObj");
      assertThat(cfg.names).containsExactly("byte-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of byteObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[9];
      assertThat(cfg.storeKey).isEqualTo("shortObj");
      assertThat(cfg.names).containsExactly("short-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of shortObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[10];
      assertThat(cfg.storeKey).isEqualTo("intObj");
      assertThat(cfg.names).containsExactly("int-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.get()).containsExactly("123");
      assertThat(cfg.desc).isEqualTo("The description of intObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[11];
      assertThat(cfg.storeKey).isEqualTo("longObj");
      assertThat(cfg.names).containsExactly("long-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of longObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[12];
      assertThat(cfg.storeKey).isEqualTo("floatObj");
      assertThat(cfg.names).containsExactly("float-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of floatObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[13];
      assertThat(cfg.storeKey).isEqualTo("doubleObj");
      assertThat(cfg.names).containsExactly("double-obj");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of doubleObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[14];
      assertThat(cfg.storeKey).isEqualTo("bigIntObj");
      assertThat(cfg.names).containsExactly("big-int");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of bigIntObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[15];
      assertThat(cfg.storeKey).isEqualTo("bigDeclObj");
      assertThat(cfg.names).containsExactly("big-decl");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of bigDeclObj");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[16];
      assertThat(cfg.storeKey).isEqualTo("strObjArr");
      assertThat(cfg.names).containsExactly("str-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("ABC", "DEF");
      assertThat(cfg.desc).isEqualTo("The description of strObjArr");
      assertThat(cfg.argInHelp).isEqualTo("txt");

      cfg = cfgs[17];
      assertThat(cfg.storeKey).isEqualTo("byteArr");
      assertThat(cfg.names).containsExactly("byte-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of byteArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[18];
      assertThat(cfg.storeKey).isEqualTo("shortArr");
      assertThat(cfg.names).containsExactly("short-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of shortArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[19];
      assertThat(cfg.storeKey).isEqualTo("intArr");
      assertThat(cfg.names).containsExactly("int-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("12", "34");
      assertThat(cfg.desc).isEqualTo("The description of intArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[20];
      assertThat(cfg.storeKey).isEqualTo("longArr");
      assertThat(cfg.names).containsExactly("long-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of longArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[21];
      assertThat(cfg.storeKey).isEqualTo("floatArr");
      assertThat(cfg.names).containsExactly("float-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of floatArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[22];
      assertThat(cfg.storeKey).isEqualTo("doubleArr");
      assertThat(cfg.names).containsExactly("double-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of doubleArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[23];
      assertThat(cfg.storeKey).isEqualTo("byteObjArr");
      assertThat(cfg.names).containsExactly("byte-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of byteObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[24];
      assertThat(cfg.storeKey).isEqualTo("shortObjArr");
      assertThat(cfg.names).containsExactly("short-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of shortObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[25];
      assertThat(cfg.storeKey).isEqualTo("intObjArr");
      assertThat(cfg.names).containsExactly("int-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("12", "34");
      assertThat(cfg.desc).isEqualTo("The description of intObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[26];
      assertThat(cfg.storeKey).isEqualTo("longObjArr");
      assertThat(cfg.names).containsExactly("long-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of longObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[27];
      assertThat(cfg.storeKey).isEqualTo("floatObjArr");
      assertThat(cfg.names).containsExactly("float-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of floatObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[28];
      assertThat(cfg.storeKey).isEqualTo("doubleObjArr");
      assertThat(cfg.names).containsExactly("double-obj-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of doubleObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[29];
      assertThat(cfg.storeKey).isEqualTo("bigIntObjArr");
      assertThat(cfg.names).containsExactly("big-int-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of bigIntObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[30];
      assertThat(cfg.storeKey).isEqualTo("bigDeclObjArr");
      assertThat(cfg.names).containsExactly("big-decl-arr");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("The description of bigDeclObjArr");
      assertThat(cfg.argInHelp).isEqualTo("<n>");

      cfg = cfgs[31];
      assertThat(cfg.storeKey).isEqualTo("empty");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).hasSize(0);
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[32];
      assertThat(cfg.storeKey).isEqualTo("emptyWithDelimiter");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).hasSize(0);
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[33];
      assertThat(cfg.storeKey).isEqualTo("singleEmptyElem");
      assertThat(cfg.names).containsExactly("single-empty");
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("");
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[34];
      assertThat(cfg.storeKey).isEqualTo("onlyOpenParenthesis");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("[");
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");

      cfg = cfgs[35];
      assertThat(cfg.storeKey).isEqualTo("onlyCloseParenthesis");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.get()).containsExactly("]");
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
    }

    @Test
    void parseFor_noOpts() {
      var optStore = new WithAnnotationOptions();

      var cmd = new Cmd("path/to/app");
      try {
        cmd.parseFor(optStore);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);

      assertThat(cmd.hasOpt("boolVal")).isFalse();
      assertThat(cmd.hasOpt("strObj")).isTrue();
      assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("byteVal")).isFalse();
      assertThat(cmd.hasOpt("shortVal")).isFalse();
      assertThat(cmd.hasOpt("intVal")).isTrue();
      assertThat(cmd.optArg("intVal").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intVal").get()).containsExactly("123");
      assertThat(cmd.hasOpt("longVal")).isFalse();
      assertThat(cmd.hasOpt("floatVal")).isFalse();
      assertThat(cmd.hasOpt("doubleVal")).isFalse();
      assertThat(cmd.hasOpt("byteObj")).isFalse();
      assertThat(cmd.hasOpt("shortObj")).isFalse();
      assertThat(cmd.hasOpt("intObj")).isTrue();
      assertThat(cmd.optArg("intObj").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intObj").get()).containsExactly("123");
      assertThat(cmd.hasOpt("longObj")).isFalse();
      assertThat(cmd.hasOpt("floatObj")).isFalse();
      assertThat(cmd.hasOpt("doubleObj")).isFalse();
      assertThat(cmd.hasOpt("bigIntObj")).isFalse();
      assertThat(cmd.hasOpt("bigDeclObj")).isFalse();
      assertThat(cmd.hasOpt("strObjArr")).isTrue();
      assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
      assertThat(cmd.hasOpt("byteArr")).isFalse();
      assertThat(cmd.hasOpt("shortArr")).isFalse();
      assertThat(cmd.hasOpt("intArr")).isTrue();
      assertThat(cmd.optArg("intArr").get()).isEqualTo("12");
      assertThat(cmd.optArgs("intArr").get()).containsExactly("12", "34");
      assertThat(cmd.hasOpt("longArr")).isFalse();
      assertThat(cmd.hasOpt("floatArr")).isFalse();
      assertThat(cmd.hasOpt("doubleArr")).isFalse();
      assertThat(cmd.hasOpt("bigIntObjArr")).isFalse();
      assertThat(cmd.hasOpt("bigDeclObjArr")).isFalse();

      assertThat(optStore.boolVal).isFalse();
      assertThat(optStore.strObj).isEqualTo("ABC");
      assertThat(optStore.byteVal).isEqualTo((byte)0);
      assertThat(optStore.shortVal).isEqualTo((short)0);
      assertThat(optStore.intVal).isEqualTo(123);
      assertThat(optStore.longVal).isEqualTo(0L);
      assertThat(optStore.floatVal).isEqualTo(0.0f);
      assertThat(optStore.doubleVal).isEqualTo(0.0);
      assertThat(optStore.byteObj).isNull();
      assertThat(optStore.shortObj).isNull();
      assertThat(optStore.intObj).isEqualTo(123);
      assertThat(optStore.longObj).isNull();
      assertThat(optStore.floatObj).isNull();
      assertThat(optStore.doubleObj).isNull();
      assertThat(optStore.bigIntObj).isNull();
      assertThat(optStore.bigDeclObj).isNull();
      assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
      assertThat(optStore.byteArr).isNull();
      assertThat(optStore.shortArr).isNull();
      assertThat(optStore.intArr).containsExactly(12, 34);
      assertThat(optStore.longArr).isNull();
      assertThat(optStore.floatArr).isNull();
      assertThat(optStore.doubleArr).isNull();
      assertThat(optStore.byteObjArr).isNull();
      assertThat(optStore.shortObjArr).isNull();
      assertThat(optStore.intObjArr).containsExactly(12, 34);
      assertThat(optStore.longObjArr).isNull();
      assertThat(optStore.floatObjArr).isNull();
      assertThat(optStore.doubleObjArr).isNull();
      assertThat(optStore.bigIntObjArr).isNull();
      assertThat(optStore.bigDeclObjArr).isNull();
    }

    @Test
    void parseFor_fullOpts() {
      var optStore = new WithAnnotationOptions();

      var cmd = new Cmd(
        "path/to/app",
        "--bool",
        "--str", "ABC",
        "--byte", "12",
        "--short", "123",
        "--int", "1234",
        "--long", "12345",
        "--float", "1.23",
        "--double", "123.456",
        "--byte-obj", "12",
        "--short-obj", "123",
        "--int-obj", "1234",
        "--long-obj", "12345",
        "--float-obj", "1.23",
        "--double-obj", "123.456",
        "--big-int", "123",
        "--big-decl", "1234",
        "--str-arr", "ABC", "--str-arr=DEF",
        "--byte-arr", "12", "--byte-arr=34",
        "--short-arr", "123", "--short-arr=456",
        "--int-arr", "123", "--int-arr=456",
        "--long-arr", "123", "--long-arr=456",
        "--float-arr", "1.23", "--float-arr=4.56",
        "--double-arr", "1.23", "--double-arr=4.56",
        "--byte-obj-arr", "12", "--byte-obj-arr=34",
        "--short-obj-arr", "123", "--short-obj-arr=456",
        "--int-obj-arr", "123", "--int-obj-arr=456",
        "--long-obj-arr", "123", "--long-obj-arr=456",
        "--float-obj-arr", "1.23", "--float-obj-arr=4.56",
        "--double-obj-arr", "1.23", "--double-obj-arr=4.56",
        "--big-int-arr", "123", "--big-int-arr=456",
        "--big-decl-arr", "123", "--big-decl-arr=456",
        "xxx"
      );
      try {
        cmd.parseFor(optStore);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("xxx");

      assertThat(cmd.hasOpt("boolVal")).isTrue();
      assertThat(cmd.optArg("boolVal").isPresent()).isFalse();
      assertThat(cmd.optArgs("boolVal").get()).hasSize(0);
      assertThat(cmd.hasOpt("strObj")).isTrue();
      assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("byteVal")).isTrue();
      assertThat(cmd.optArg("byteVal").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteVal").get()).containsExactly("12");
      assertThat(cmd.hasOpt("shortVal")).isTrue();
      assertThat(cmd.optArg("shortVal").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortVal").get()).containsExactly("123");
      assertThat(cmd.hasOpt("intVal")).isTrue();
      assertThat(cmd.optArg("intVal").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("intVal").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("longVal")).isTrue();
      assertThat(cmd.optArg("longVal").get()).isEqualTo("12345");
      assertThat(cmd.optArgs("longVal").get()).containsExactly("12345");
      assertThat(cmd.hasOpt("floatVal")).isTrue();
      assertThat(cmd.optArg("floatVal").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatVal").get()).containsExactly("1.23");
      assertThat(cmd.hasOpt("doubleVal")).isTrue();
      assertThat(cmd.optArg("doubleVal").get()).isEqualTo("123.456");
      assertThat(cmd.optArgs("doubleVal").get()).containsExactly("123.456");
      assertThat(cmd.hasOpt("byteObj")).isTrue();
      assertThat(cmd.optArg("byteObj").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteObj").get()).containsExactly("12");
      assertThat(cmd.hasOpt("shortObj")).isTrue();
      assertThat(cmd.optArg("shortObj").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortObj").get()).containsExactly("123");
      assertThat(cmd.hasOpt("intObj")).isTrue();
      assertThat(cmd.optArg("intObj").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("intObj").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("longObj")).isTrue();
      assertThat(cmd.optArg("longObj").get()).isEqualTo("12345");
      assertThat(cmd.optArgs("longObj").get()).containsExactly("12345");
      assertThat(cmd.hasOpt("floatObj")).isTrue();
      assertThat(cmd.optArg("floatObj").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatObj").get()).containsExactly("1.23");
      assertThat(cmd.hasOpt("doubleObj")).isTrue();
      assertThat(cmd.optArg("doubleObj").get()).isEqualTo("123.456");
      assertThat(cmd.optArgs("doubleObj").get()).containsExactly("123.456");
      assertThat(cmd.hasOpt("bigIntObj")).isTrue();
      assertThat(cmd.optArg("bigIntObj").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigIntObj").get()).containsExactly("123");
      assertThat(cmd.hasOpt("bigDeclObj")).isTrue();
      assertThat(cmd.optArg("bigDeclObj").get()).isEqualTo("1234");
      assertThat(cmd.optArgs("bigDeclObj").get()).containsExactly("1234");
      assertThat(cmd.hasOpt("strObjArr")).isTrue();
      assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
      assertThat(cmd.hasOpt("byteArr")).isTrue();
      assertThat(cmd.optArg("byteArr").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteArr").get()).containsExactly("12", "34");
      assertThat(cmd.hasOpt("shortArr")).isTrue();
      assertThat(cmd.optArg("shortArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("intArr")).isTrue();
      assertThat(cmd.optArg("intArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("longArr")).isTrue();
      assertThat(cmd.optArg("longArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("longArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("floatArr")).isTrue();
      assertThat(cmd.optArg("floatArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("doubleArr")).isTrue();
      assertThat(cmd.optArg("doubleArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("doubleArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("byteObjArr")).isTrue();
      assertThat(cmd.optArg("byteObjArr").get()).isEqualTo("12");
      assertThat(cmd.optArgs("byteObjArr").get()).containsExactly("12", "34");
      assertThat(cmd.hasOpt("shortObjArr")).isTrue();
      assertThat(cmd.optArg("shortObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("shortObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("intObjArr")).isTrue();
      assertThat(cmd.optArg("intObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("intObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("longObjArr")).isTrue();
      assertThat(cmd.optArg("longObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("longObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("floatObjArr")).isTrue();
      assertThat(cmd.optArg("floatObjArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("floatObjArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("doubleObjArr")).isTrue();
      assertThat(cmd.optArg("doubleObjArr").get()).isEqualTo("1.23");
      assertThat(cmd.optArgs("doubleObjArr").get()).containsExactly("1.23", "4.56");
      assertThat(cmd.hasOpt("bigIntObjArr")).isTrue();
      assertThat(cmd.optArg("bigIntObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigIntObjArr").get()).containsExactly("123", "456");
      assertThat(cmd.hasOpt("bigDeclObjArr")).isTrue();
      assertThat(cmd.optArg("bigDeclObjArr").get()).isEqualTo("123");
      assertThat(cmd.optArgs("bigDeclObjArr").get()).containsExactly("123", "456");

      assertThat(optStore.boolVal).isTrue();
      assertThat(optStore.strObj).isEqualTo("ABC");
      assertThat(optStore.byteVal).isEqualTo((byte)12);
      assertThat(optStore.shortVal).isEqualTo((short)123);
      assertThat(optStore.intVal).isEqualTo(1234);
      assertThat(optStore.longVal).isEqualTo(12345L);
      assertThat(optStore.floatVal).isEqualTo(1.23f);
      assertThat(optStore.doubleVal).isEqualTo(123.456);
      assertThat(optStore.byteObj).isEqualTo((byte)12);
      assertThat(optStore.shortObj).isEqualTo((short)123);
      assertThat(optStore.intObj).isEqualTo(1234);
      assertThat(optStore.longObj).isEqualTo(12345L);
      assertThat(optStore.floatObj).isEqualTo(1.23f);
      assertThat(optStore.doubleObj).isEqualTo(123.456);
      assertThat(optStore.bigIntObj).isEqualTo(new BigInteger("123"));
      assertThat(optStore.bigDeclObj).isEqualTo(new BigDecimal("1234"));
      assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
      assertThat(optStore.byteArr).containsExactly((byte)12, (byte)34);
      assertThat(optStore.shortArr).containsExactly((short)123, (short)456);
      assertThat(optStore.intArr).containsExactly(123, 456);
      assertThat(optStore.longArr).containsExactly(123L, 456L);
      assertThat(optStore.floatArr).containsExactly(1.23f, 4.56f);
      assertThat(optStore.doubleArr).containsExactly(1.23, 4.56);
      assertThat(optStore.byteObjArr).containsExactly((byte)12, (byte)34);
      assertThat(optStore.shortObjArr).containsExactly((short)123, (short)456);
      assertThat(optStore.intObjArr).containsExactly(123, 456);
      assertThat(optStore.longObjArr).containsExactly(123L, 456L);
      assertThat(optStore.floatObjArr).containsExactly(1.23f, 4.56f);
      assertThat(optStore.doubleObjArr).containsExactly(1.23, 4.56);
      assertThat(optStore.bigIntObjArr)
        .containsExactly(new BigInteger("123"), new BigInteger("456"));
      assertThat(optStore.bigDeclObjArr)
        .containsExactly(new BigDecimal("123"), new BigDecimal("456"));
    }

    @Test
    void parseUntilSubCmdFor_noSubCmd() {
      var optStore = new WithAnnotationOptions();

      var cmd = new Cmd("path/to/app");

      try {
        var optional = cmd.parseUntilSubCmdFor(optStore);
        assertThat(optional.isPresent()).isFalse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
  
        assertThat(cmd.hasOpt("boolVal")).isFalse();
        assertThat(cmd.hasOpt("strObj")).isTrue();
        assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
        assertThat(cmd.hasOpt("byteVal")).isFalse();
        assertThat(cmd.hasOpt("shortVal")).isFalse();
        assertThat(cmd.hasOpt("intVal")).isTrue();
        assertThat(cmd.optArg("intVal").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intVal").get()).containsExactly("123");
        assertThat(cmd.hasOpt("longVal")).isFalse();
        assertThat(cmd.hasOpt("floatVal")).isFalse();
        assertThat(cmd.hasOpt("doubleVal")).isFalse();
        assertThat(cmd.hasOpt("byteObj")).isFalse();
        assertThat(cmd.hasOpt("shortObj")).isFalse();
        assertThat(cmd.hasOpt("intObj")).isTrue();
        assertThat(cmd.optArg("intObj").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intObj").get()).containsExactly("123");
        assertThat(cmd.hasOpt("longObj")).isFalse();
        assertThat(cmd.hasOpt("floatObj")).isFalse();
        assertThat(cmd.hasOpt("doubleObj")).isFalse();
        assertThat(cmd.hasOpt("bigIntObj")).isFalse();
        assertThat(cmd.hasOpt("bigDeclObj")).isFalse();
        assertThat(cmd.hasOpt("strObjArr")).isTrue();
        assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
        assertThat(cmd.hasOpt("byteArr")).isFalse();
        assertThat(cmd.hasOpt("shortArr")).isFalse();
        assertThat(cmd.hasOpt("intArr")).isTrue();
        assertThat(cmd.optArg("intArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("intArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("longArr")).isFalse();
        assertThat(cmd.hasOpt("floatArr")).isFalse();
        assertThat(cmd.hasOpt("doubleArr")).isFalse();
        assertThat(cmd.hasOpt("byteObjArr")).isFalse();
        assertThat(cmd.hasOpt("shortObjArr")).isFalse();
        assertThat(cmd.hasOpt("intObjArr")).isTrue();
        assertThat(cmd.optArg("intObjArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("intObjArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("longObjArr")).isFalse();
        assertThat(cmd.hasOpt("floatObjArr")).isFalse();
        assertThat(cmd.hasOpt("doubleObjArr")).isFalse();
        assertThat(cmd.hasOpt("bigIntObjArr")).isFalse();
        assertThat(cmd.hasOpt("bigDeclObjArr")).isFalse();
  
        assertThat(optStore.boolVal).isFalse();
        assertThat(optStore.strObj).isEqualTo("ABC");
        assertThat(optStore.byteVal).isEqualTo((byte)0);
        assertThat(optStore.shortVal).isEqualTo((short)0);
        assertThat(optStore.intVal).isEqualTo(123);
        assertThat(optStore.longVal).isEqualTo(0L);
        assertThat(optStore.floatVal).isEqualTo(0.0f);
        assertThat(optStore.doubleVal).isEqualTo(0.0);
        assertThat(optStore.byteObj).isNull();
        assertThat(optStore.shortObj).isNull();
        assertThat(optStore.intObj).isEqualTo(123);
        assertThat(optStore.longObj).isNull();
        assertThat(optStore.floatObj).isNull();
        assertThat(optStore.doubleObj).isNull();
        assertThat(optStore.bigIntObj).isNull();
        assertThat(optStore.bigDeclObj).isNull();
        assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
        assertThat(optStore.byteArr).isNull();
        assertThat(optStore.shortArr).isNull();
        assertThat(optStore.intArr).containsExactly(12, 34);
        assertThat(optStore.longArr).isNull();
        assertThat(optStore.floatArr).isNull();
        assertThat(optStore.doubleArr).isNull();
        assertThat(optStore.byteObjArr).isNull();
        assertThat(optStore.shortObjArr).isNull();
        assertThat(optStore.intObjArr).containsExactly(12, 34);
        assertThat(optStore.longObjArr).isNull();
        assertThat(optStore.floatObjArr).isNull();
        assertThat(optStore.doubleObjArr).isNull();
        assertThat(optStore.bigIntObjArr).isNull();
        assertThat(optStore.bigDeclObjArr).isNull();
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void parseUntilSubCmdFor_withSubCmd() {
      var optStore = new WithAnnotationOptions();

      var cmd = new Cmd(
        "path/to/app",
        "--bool",
        "--str", "ABC",
        "--byte", "12",
        "--short", "123",
        "--int", "1234",
        "--long", "12345",
        "--float", "1.23",
        "--double", "123.456",
        "--byte-obj", "12",
        "--short-obj", "123",
        "--int-obj", "1234",
        "--long-obj", "12345",
        "--float-obj", "1.23",
        "--double-obj", "123.456",
        "--big-int", "123",
        "--big-decl", "1234",
        "--str-arr", "ABC", "--str-arr=DEF",
        "--byte-arr", "12", "--byte-arr=34",
        "--short-arr", "123", "--short-arr=456",
        "--int-arr", "123", "--int-arr=456",
        "--long-arr", "123", "--long-arr=456",
        "--float-arr", "1.23", "--float-arr=4.56",
        "--double-arr", "1.23", "--double-arr=4.56",
        "--byte-obj-arr", "12", "--byte-obj-arr=34",
        "--short-obj-arr", "123", "--short-obj-arr=456",
        "--int-obj-arr", "123", "--int-obj-arr=456",
        "--long-obj-arr", "123", "--long-obj-arr=456",
        "--float-obj-arr", "1.23", "--float-obj-arr=4.56",
        "--double-obj-arr", "1.23", "--double-obj-arr=4.56",
        "--big-int-arr", "123", "--big-int-arr=456",
        "--big-decl-arr", "123", "--big-decl-arr=456",
        "subsub",
        "argarg"
      );

      try {
        var optional = cmd.parseUntilSubCmdFor(optStore);
        assertThat(optional.isPresent()).isTrue();

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);

        assertThat(cmd.hasOpt("boolVal")).isTrue();
        assertThat(cmd.optArg("boolVal").isPresent()).isFalse();
        assertThat(cmd.optArgs("boolVal").get()).hasSize(0);
        assertThat(cmd.hasOpt("strObj")).isTrue();
        assertThat(cmd.optArg("strObj").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObj").get()).containsExactly("ABC");
        assertThat(cmd.hasOpt("byteVal")).isTrue();
        assertThat(cmd.optArg("byteVal").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteVal").get()).containsExactly("12");
        assertThat(cmd.hasOpt("shortVal")).isTrue();
        assertThat(cmd.optArg("shortVal").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortVal").get()).containsExactly("123");
        assertThat(cmd.hasOpt("intVal")).isTrue();
        assertThat(cmd.optArg("intVal").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("intVal").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("longVal")).isTrue();
        assertThat(cmd.optArg("longVal").get()).isEqualTo("12345");
        assertThat(cmd.optArgs("longVal").get()).containsExactly("12345");
        assertThat(cmd.hasOpt("floatVal")).isTrue();
        assertThat(cmd.optArg("floatVal").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatVal").get()).containsExactly("1.23");
        assertThat(cmd.hasOpt("doubleVal")).isTrue();
        assertThat(cmd.optArg("doubleVal").get()).isEqualTo("123.456");
        assertThat(cmd.optArgs("doubleVal").get()).containsExactly("123.456");
        assertThat(cmd.hasOpt("byteObj")).isTrue();
        assertThat(cmd.optArg("byteObj").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteObj").get()).containsExactly("12");
        assertThat(cmd.hasOpt("shortObj")).isTrue();
        assertThat(cmd.optArg("shortObj").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortObj").get()).containsExactly("123");
        assertThat(cmd.hasOpt("intObj")).isTrue();
        assertThat(cmd.optArg("intObj").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("intObj").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("longObj")).isTrue();
        assertThat(cmd.optArg("longObj").get()).isEqualTo("12345");
        assertThat(cmd.optArgs("longObj").get()).containsExactly("12345");
        assertThat(cmd.hasOpt("floatObj")).isTrue();
        assertThat(cmd.optArg("floatObj").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatObj").get()).containsExactly("1.23");
        assertThat(cmd.hasOpt("doubleObj")).isTrue();
        assertThat(cmd.optArg("doubleObj").get()).isEqualTo("123.456");
        assertThat(cmd.optArgs("doubleObj").get()).containsExactly("123.456");
        assertThat(cmd.hasOpt("bigIntObj")).isTrue();
        assertThat(cmd.optArg("bigIntObj").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigIntObj").get()).containsExactly("123");
        assertThat(cmd.hasOpt("bigDeclObj")).isTrue();
        assertThat(cmd.optArg("bigDeclObj").get()).isEqualTo("1234");
        assertThat(cmd.optArgs("bigDeclObj").get()).containsExactly("1234");
        assertThat(cmd.hasOpt("strObjArr")).isTrue();
        assertThat(cmd.optArg("strObjArr").get()).isEqualTo("ABC");
        assertThat(cmd.optArgs("strObjArr").get()).containsExactly("ABC", "DEF");
        assertThat(cmd.hasOpt("byteArr")).isTrue();
        assertThat(cmd.optArg("byteArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("shortArr")).isTrue();
        assertThat(cmd.optArg("shortArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("intArr")).isTrue();
        assertThat(cmd.optArg("intArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("longArr")).isTrue();
        assertThat(cmd.optArg("longArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("longArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("floatArr")).isTrue();
        assertThat(cmd.optArg("floatArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("doubleArr")).isTrue();
        assertThat(cmd.optArg("doubleArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("doubleArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("byteObjArr")).isTrue();
        assertThat(cmd.optArg("byteObjArr").get()).isEqualTo("12");
        assertThat(cmd.optArgs("byteObjArr").get()).containsExactly("12", "34");
        assertThat(cmd.hasOpt("shortObjArr")).isTrue();
        assertThat(cmd.optArg("shortObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("shortObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("intObjArr")).isTrue();
        assertThat(cmd.optArg("intObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("intObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("longObjArr")).isTrue();
        assertThat(cmd.optArg("longObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("longObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("floatObjArr")).isTrue();
        assertThat(cmd.optArg("floatObjArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("floatObjArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("doubleObjArr")).isTrue();
        assertThat(cmd.optArg("doubleObjArr").get()).isEqualTo("1.23");
        assertThat(cmd.optArgs("doubleObjArr").get()).containsExactly("1.23", "4.56");
        assertThat(cmd.hasOpt("bigIntObjArr")).isTrue();
        assertThat(cmd.optArg("bigIntObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigIntObjArr").get()).containsExactly("123", "456");
        assertThat(cmd.hasOpt("bigDeclObjArr")).isTrue();
        assertThat(cmd.optArg("bigDeclObjArr").get()).isEqualTo("123");
        assertThat(cmd.optArgs("bigDeclObjArr").get()).containsExactly("123", "456");
  
        assertThat(optStore.boolVal).isTrue();
        assertThat(optStore.strObj).isEqualTo("ABC");
        assertThat(optStore.byteVal).isEqualTo((byte)12);
        assertThat(optStore.shortVal).isEqualTo((short)123);
        assertThat(optStore.intVal).isEqualTo(1234);
        assertThat(optStore.longVal).isEqualTo(12345L);
        assertThat(optStore.floatVal).isEqualTo(1.23f);
        assertThat(optStore.doubleVal).isEqualTo(123.456);
        assertThat(optStore.byteObj).isEqualTo((byte)12);
        assertThat(optStore.shortObj).isEqualTo((short)123);
        assertThat(optStore.intObj).isEqualTo(1234);
        assertThat(optStore.longObj).isEqualTo(12345L);
        assertThat(optStore.floatObj).isEqualTo(1.23f);
        assertThat(optStore.doubleObj).isEqualTo(123.456);
        assertThat(optStore.bigIntObj).isEqualTo(new BigInteger("123"));
        assertThat(optStore.bigDeclObj).isEqualTo(new BigDecimal("1234"));
        assertThat(optStore.strObjArr).containsExactly("ABC", "DEF");
        assertThat(optStore.byteArr).containsExactly((byte)12, (byte)34);
        assertThat(optStore.shortArr).containsExactly((short)123, (short)456);
        assertThat(optStore.intArr).containsExactly(123, 456);
        assertThat(optStore.longArr).containsExactly(123L, 456L);
        assertThat(optStore.floatArr).containsExactly(1.23f, 4.56f);
        assertThat(optStore.doubleArr).containsExactly(1.23, 4.56);
        assertThat(optStore.byteObjArr).containsExactly((byte)12, (byte)34);
        assertThat(optStore.shortObjArr).containsExactly((short)123, (short)456);
        assertThat(optStore.intObjArr).containsExactly(123, 456);
        assertThat(optStore.longObjArr).containsExactly(123L, 456L);
        assertThat(optStore.floatObjArr).containsExactly(1.23f, 4.56f);
        assertThat(optStore.doubleObjArr).containsExactly(1.23, 4.56);
        assertThat(optStore.bigIntObjArr)
          .containsExactly(new BigInteger("123"), new BigInteger("456"));
        assertThat(optStore.bigDeclObjArr)
          .containsExactly(new BigDecimal("123"), new BigDecimal("456"));

        assertThat(subCmd.name()).isEqualTo("subsub");
        assertThat(subCmd.args()).containsExactly("argarg");
      } catch (Exception e) {
        fail(e);
      }
    }
  }

  @Nested
  class TestsBadFieldType {
    class BadSingleFieldTypeOptions {
      Date date;
    }
    class BadArrayFieldTypeOptions {
      Date[] dates;
    }

    @Test
    void testSetOptionStoreFieldValues_badSingleFieldType() {
      var store = new BadSingleFieldTypeOptions();
      var cfgs = makeOptCfgsFor(store);
      var opts = new HashMap<String, List<String>>();
      opts.put("date", List.of("2024-01-02T11:22:33.444.Z"));

      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("date");
        assertThat(e.type).isEqualTo(Date.class);
        assertThat(e.optArgs).hasSize(1);
        assertThat(e.getCause()).isInstanceOf(BadFieldType.class);
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void testSetOptionStoreFieldValues_badArrayFieldType() {
      var store = new BadArrayFieldTypeOptions();
      var cfgs = makeOptCfgsFor(store);
      var opts = new HashMap<String, List<String>>();
      opts.put("dates", List.of("2024-01-02T11:22:33.444.Z"));

      try {
        ParseFor.setOptionStoreFieldValues(store, cfgs, opts);
        fail();
      } catch (FailToSetOptionStoreField e) {
        assertThat(e.field).isEqualTo("dates");
        assertThat(e.type).isEqualTo(Date[].class);
        assertThat(e.optArgs).hasSize(1);
        assertThat(e.getCause()).isInstanceOf(BadFieldType.class);
      } catch (Exception e) {
        fail(e);
      }
    }
  }
}
