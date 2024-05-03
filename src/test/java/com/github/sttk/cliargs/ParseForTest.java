package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.annotation.Opt;
import com.github.sttk.cliargs.CliArgs;
import com.github.sttk.cliargs.convert.IntConverter.InvalidIntegerFormat;
import com.github.sttk.cliargs.convert.DoubleConverter.InvalidDoubleFormat;
import com.github.sttk.exception.ReasonedException;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("missing-explicit-ctor")
public class ParseForTest {

  @Test
  void testParseFor_emptyOptionStoreAndNoArgs() {
    class MyOptions {}

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[0]);
    var result = cliargs.parseFor(options);

    assertThat(result.cmd().getName()).isEqualTo("app");
    assertThat(result.cmd().getArgs()).isEmpty();
    assertThat(result.optCfgs()).isEmpty();
    assertThat(result.exception()).isNull();
  }

  @Test
  void testParseFor_nonEmptyOptionStoreAndNoArgs() {
    class MyOptions {
      @Opt boolean boolVal;
      @Opt byte byteVal;
      @Opt short shortVal;
      @Opt int intVal;
      @Opt long longVal;
      @Opt float floatVal;
      @Opt double doubleVal;
      @Opt BigInteger bigIntVal;
      @Opt BigDecimal bigDecimalVal;
      @Opt String stringVal;
      @Opt byte[] byteArr;
      @Opt short[] shortArr;
      @Opt int[] intArr;
      @Opt long[] longArr;
      @Opt float[] floatArr;
      @Opt double[] doubleArr;
      @Opt BigInteger[] bigIntArr;
      @Opt BigDecimal[] bigDecimalArr;
      @Opt String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[0]);
    var result = cliargs.parseFor(options);

    assertThat(result.cmd().getName()).isEqualTo("app");
    assertThat(result.cmd().getArgs()).isEmpty();
    assertThat(result.optCfgs()).hasSize(19);
    assertThat(result.exception()).isNull();

    var cfg = result.optCfgs()[0];
    assertThat(cfg.storeKey).isEqualTo("boolVal");
    assertThat(cfg.names).containsExactly("boolVal");
    assertThat(cfg.hasArg).isFalse();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(boolean.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[1];
    assertThat(cfg.storeKey).isEqualTo("byteVal");
    assertThat(cfg.names).containsExactly("byteVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(byte.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[2];
    assertThat(cfg.storeKey).isEqualTo("shortVal");
    assertThat(cfg.names).containsExactly("shortVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(short.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[3];
    assertThat(cfg.storeKey).isEqualTo("intVal");
    assertThat(cfg.names).containsExactly("intVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(int.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[4];
    assertThat(cfg.storeKey).isEqualTo("longVal");
    assertThat(cfg.names).containsExactly("longVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(long.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[5];
    assertThat(cfg.storeKey).isEqualTo("floatVal");
    assertThat(cfg.names).containsExactly("floatVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(float.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[6];
    assertThat(cfg.storeKey).isEqualTo("doubleVal");
    assertThat(cfg.names).containsExactly("doubleVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(double.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[7];
    assertThat(cfg.storeKey).isEqualTo("bigIntVal");
    assertThat(cfg.names).containsExactly("bigIntVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(BigInteger.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[8];
    assertThat(cfg.storeKey).isEqualTo("bigDecimalVal");
    assertThat(cfg.names).containsExactly("bigDecimalVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(BigDecimal.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[9];
    assertThat(cfg.storeKey).isEqualTo("stringVal");
    assertThat(cfg.names).containsExactly("stringVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(String.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[10];
    assertThat(cfg.storeKey).isEqualTo("byteArr");
    assertThat(cfg.names).containsExactly("byteArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(byte.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[11];
    assertThat(cfg.storeKey).isEqualTo("shortArr");
    assertThat(cfg.names).containsExactly("shortArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(short.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[12];
    assertThat(cfg.storeKey).isEqualTo("intArr");
    assertThat(cfg.names).containsExactly("intArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(int.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[13];
    assertThat(cfg.storeKey).isEqualTo("longArr");
    assertThat(cfg.names).containsExactly("longArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(long.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[14];
    assertThat(cfg.storeKey).isEqualTo("floatArr");
    assertThat(cfg.names).containsExactly("floatArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(float.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[15];
    assertThat(cfg.storeKey).isEqualTo("doubleArr");
    assertThat(cfg.names).containsExactly("doubleArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(double.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[16];
    assertThat(cfg.storeKey).isEqualTo("bigIntArr");
    assertThat(cfg.names).containsExactly("bigIntArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(BigInteger.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[17];
    assertThat(cfg.storeKey).isEqualTo("bigDecimalArr");
    assertThat(cfg.names).containsExactly("bigDecimalArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(BigDecimal.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[18];
    assertThat(cfg.storeKey).isEqualTo("stringArr");
    assertThat(cfg.names).containsExactly("stringArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(String.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    assertThat(options.boolVal).isFalse();
    assertThat(options.byteVal).isEqualTo((byte)0);
    assertThat(options.shortVal).isEqualTo((short)0);
    assertThat(options.intVal).isEqualTo(0);
    assertThat(options.longVal).isEqualTo(0L);
    assertThat(options.floatVal).isEqualTo(0.0f);
    assertThat(options.doubleVal).isEqualTo(0.0);
    assertThat(options.bigIntVal).isNull();
    assertThat(options.bigDecimalVal).isNull();
    assertThat(options.stringVal).isNull();
    assertThat(options.byteArr).isNull();
    assertThat(options.shortArr).isNull();
    assertThat(options.intArr).isNull();
    assertThat(options.longArr).isNull();
    assertThat(options.floatArr).isNull();
    assertThat(options.doubleArr).isNull();
    assertThat(options.bigIntArr).isNull();
    assertThat(options.bigDecimalArr).isNull();
    assertThat(options.stringArr).isNull();
  }

  @Test
  void testParseFor_dontOverwriteOptionsIfNoArgs() {
    class MyOptions {
      @Opt boolean boolVal;
      @Opt byte byteVal;
      @Opt short shortVal;
      @Opt int intVal;
      @Opt long longVal;
      @Opt float floatVal;
      @Opt double doubleVal;
      @Opt BigInteger bigIntVal;
      @Opt BigDecimal bigDecimalVal;
      @Opt String stringVal;
      @Opt byte[] byteArr;
      @Opt short[] shortArr;
      @Opt int[] intArr;
      @Opt long[] longArr;
      @Opt float[] floatArr;
      @Opt double[] doubleArr;
      @Opt BigInteger[] bigIntArr;
      @Opt BigDecimal[] bigDecimalArr;
      @Opt String[] stringArr;
    }

    var options = new MyOptions();
    options.boolVal = true;
    options.byteVal = (byte)111;
    options.shortVal = (short)22;
    options.intVal = 333;
    options.longVal = 444L;
    options.floatVal = 0.123f;
    options.doubleVal = 0.456789;
    options.bigIntVal = new BigInteger("1234");
    options.bigDecimalVal = new BigDecimal("5.678");
    options.stringVal = "abcdefg";
    options.byteArr = new byte[]{(byte)1, (byte)1, (byte)1};
    options.shortArr = new short[]{(short)2, (short)2};
    options.intArr = new int[]{3, 3, 3};
    options.longArr = new long[]{4L, 4L, 4L};
    options.floatArr = new float[]{0.1f, 2.3f};
    options.doubleArr = new double[]{0.45, 6.789};
    options.bigIntArr = new BigInteger[]{new BigInteger("1234")};
    options.bigDecimalArr = new BigDecimal[]{new BigDecimal("56.78")};
    options.stringArr = new String[]{"ab", "cd", "efg"};

    var cliargs = new CliArgs("/path/to/app", new String[0]);
    var result = cliargs.parseFor(options);

    assertThat(result.cmd().getName()).isEqualTo("app");
    assertThat(result.cmd().getArgs()).isEmpty();
    assertThat(result.optCfgs()).hasSize(19);
    assertThat(result.exception()).isNull();

    var cfg = result.optCfgs()[0];
    assertThat(cfg.storeKey).isEqualTo("boolVal");
    assertThat(cfg.names).containsExactly("boolVal");
    assertThat(cfg.hasArg).isFalse();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(boolean.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[1];
    assertThat(cfg.storeKey).isEqualTo("byteVal");
    assertThat(cfg.names).containsExactly("byteVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(byte.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[2];
    assertThat(cfg.storeKey).isEqualTo("shortVal");
    assertThat(cfg.names).containsExactly("shortVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(short.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[3];
    assertThat(cfg.storeKey).isEqualTo("intVal");
    assertThat(cfg.names).containsExactly("intVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(int.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[4];
    assertThat(cfg.storeKey).isEqualTo("longVal");
    assertThat(cfg.names).containsExactly("longVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(long.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[5];
    assertThat(cfg.storeKey).isEqualTo("floatVal");
    assertThat(cfg.names).containsExactly("floatVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(float.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[6];
    assertThat(cfg.storeKey).isEqualTo("doubleVal");
    assertThat(cfg.names).containsExactly("doubleVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(double.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[7];
    assertThat(cfg.storeKey).isEqualTo("bigIntVal");
    assertThat(cfg.names).containsExactly("bigIntVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(BigInteger.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[8];
    assertThat(cfg.storeKey).isEqualTo("bigDecimalVal");
    assertThat(cfg.names).containsExactly("bigDecimalVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(BigDecimal.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[9];
    assertThat(cfg.storeKey).isEqualTo("stringVal");
    assertThat(cfg.names).containsExactly("stringVal");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(String.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[10];
    assertThat(cfg.storeKey).isEqualTo("byteArr");
    assertThat(cfg.names).containsExactly("byteArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(byte.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[11];
    assertThat(cfg.storeKey).isEqualTo("shortArr");
    assertThat(cfg.names).containsExactly("shortArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(short.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[12];
    assertThat(cfg.storeKey).isEqualTo("intArr");
    assertThat(cfg.names).containsExactly("intArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(int.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[13];
    assertThat(cfg.storeKey).isEqualTo("longArr");
    assertThat(cfg.names).containsExactly("longArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(long.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[14];
    assertThat(cfg.storeKey).isEqualTo("floatArr");
    assertThat(cfg.names).containsExactly("floatArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(float.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[15];
    assertThat(cfg.storeKey).isEqualTo("doubleArr");
    assertThat(cfg.names).containsExactly("doubleArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(double.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[16];
    assertThat(cfg.storeKey).isEqualTo("bigIntArr");
    assertThat(cfg.names).containsExactly("bigIntArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(BigInteger.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[17];
    assertThat(cfg.storeKey).isEqualTo("bigDecimalArr");
    assertThat(cfg.names).containsExactly("bigDecimalArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(BigDecimal.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    cfg = result.optCfgs()[18];
    assertThat(cfg.storeKey).isEqualTo("stringArr");
    assertThat(cfg.names).containsExactly("stringArr");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.type).isEqualTo(String.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    assertThat(options.boolVal).isTrue();
    assertThat(options.byteVal).isEqualTo((byte)111);
    assertThat(options.shortVal).isEqualTo((short)22);
    assertThat(options.intVal).isEqualTo(333);
    assertThat(options.longVal).isEqualTo(444L);
    assertThat(options.floatVal).isEqualTo(0.123f);
    assertThat(options.doubleVal).isEqualTo(0.456789);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("1234"));
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("5.678"));
    assertThat(options.stringVal).isEqualTo("abcdefg");
    assertThat(options.byteArr).containsExactly(1, 1, 1);
    assertThat(options.shortArr).containsExactly(2, 2);
    assertThat(options.intArr).containsExactly(3, 3, 3);
    assertThat(options.longArr).containsExactly(4, 4, 4);
    assertThat(options.floatArr).containsExactly(0.1f, 2.3f);
    assertThat(options.doubleArr).containsExactly(0.45, 6.789);
    assertThat(options.bigIntArr).containsExactly(new BigInteger("1234"));
    assertThat(options.bigDecimalArr).containsExactly(new BigDecimal("56.78"));
    assertThat(options.stringArr).containsExactly("ab", "cd", "efg");
  }

  @Test
  void testParseFor_optionIsBoolAndArgIsName() {
    class MyOptions {
      @Opt(cfg="flag,f")
      boolean flag;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{"--flag", "abc"});
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    assertThat(result.cmd().getName()).isEqualTo("app");
    assertThat(result.cmd().getArgs()).containsExactly("abc");
    assertThat(result.cmd().hasOpt("flag")).isTrue();
    assertThat((String)result.cmd().getOptArg("flag")).isNull();
    assertThat((List<?>)result.cmd().getOptArgs("flag")).isEmpty();

    var cfg = result.optCfgs()[0];
    assertThat(cfg.storeKey).isEqualTo("flag");
    assertThat(cfg.names).containsExactly("flag", "f");
    assertThat(cfg.hasArg).isFalse();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(boolean.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    assertThat(options.flag).isTrue();
  }

  @Test
  void testParseFor_optionIsBoolAndArgIsAlias() {
    class MyOptions {
      @Opt(cfg="flag,f")
      boolean flag;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{"-f", "abc"});
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    assertThat(result.cmd().getName()).isEqualTo("app");
    assertThat(result.cmd().getArgs()).containsExactly("abc");
    assertThat(result.cmd().hasOpt("flag")).isTrue();
    assertThat((String)result.cmd().getOptArg("flag")).isNull();
    assertThat((List<?>)result.cmd().getOptArgs("flag")).isEmpty();

    var cfg = result.optCfgs()[0];
    assertThat(cfg.storeKey).isEqualTo("flag");
    assertThat(cfg.names).containsExactly("flag", "f");
    assertThat(cfg.hasArg).isFalse();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.type).isEqualTo(boolean.class);
    assertThat(cfg.defaults).isNull();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");

    assertThat(options.flag).isTrue();
  }

  @Test
  void testParseFor_optionsArePrimitiveIntegersAndArgsAreNames() {
    class MyOptions {
      @Opt(cfg="byte-val,b")
      byte byteVal;

      @Opt(cfg="short-val,s")
      short shortVal;

      @Opt(cfg="int-val,i")
      int intVal;

      @Opt(cfg="long-val,l")
      long longVal;

      @Opt(cfg="bigint-val,g")
      BigInteger bigIntVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "--byte-val", "12", "--short-val", "34", "--int-val", "56",
      "--long-val", "78", "--bigint-val", "123456"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("byteVal")).isTrue();
    assertThat(cmd.hasOpt("shortVal")).isTrue();
    assertThat(cmd.hasOpt("intVal")).isTrue();
    assertThat(cmd.hasOpt("longVal")).isTrue();
    assertThat(cmd.hasOpt("bigIntVal")).isTrue();

    assertThat((Byte)cmd.getOptArg("byteVal")).isEqualTo((byte)12);
    assertThat((Short)cmd.getOptArg("shortVal")).isEqualTo((short)34);
    assertThat((Integer)cmd.getOptArg("intVal")).isEqualTo(56);
    assertThat((Long)cmd.getOptArg("longVal")).isEqualTo(78L);
    assertThat((BigInteger)cmd.getOptArg("bigIntVal")).isEqualTo(123456);

    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).containsExactly((byte)12);
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).containsExactly((short)34);
    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).containsExactly(56);
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).containsExactly(78L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).containsExactly(new BigInteger("123456"));

    assertThat(options.byteVal).isEqualTo((byte)12);
    assertThat(options.shortVal).isEqualTo((short)34);
    assertThat(options.intVal).isEqualTo(56);
    assertThat(options.longVal).isEqualTo(78L);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("123456"));
  }

  @Test
  void testParseFor_optionsArePrimitiveIntegersAndArgsAreAliases() {
    class MyOptions {
      @Opt(cfg="byte-val,b")
      byte byteVal;

      @Opt(cfg="short-val,s")
      short shortVal;

      @Opt(cfg="int-val,i")
      int intVal;

      @Opt(cfg="long-val,l")
      long longVal;

      @Opt(cfg="bigint-val,g")
      BigInteger bigIntVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "-b", "12", "-s", "34", "-i", "56", "-l", "78", "-g", "123456"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("byteVal")).isTrue();
    assertThat(cmd.hasOpt("shortVal")).isTrue();
    assertThat(cmd.hasOpt("intVal")).isTrue();
    assertThat(cmd.hasOpt("longVal")).isTrue();
    assertThat(cmd.hasOpt("bigIntVal")).isTrue();

    assertThat((Byte)cmd.getOptArg("byteVal")).isEqualTo((byte)12);
    assertThat((Short)cmd.getOptArg("shortVal")).isEqualTo((short)34);
    assertThat((Integer)cmd.getOptArg("intVal")).isEqualTo(56);
    assertThat((Long)cmd.getOptArg("longVal")).isEqualTo(78L);
    assertThat((BigInteger)cmd.getOptArg("bigIntVal")).isEqualTo(123456);

    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).containsExactly((byte)12);
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).containsExactly((short)34);
    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).containsExactly(56);
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).containsExactly(78L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).containsExactly(new BigInteger("123456"));

    assertThat(options.byteVal).isEqualTo((byte)12);
    assertThat(options.shortVal).isEqualTo((short)34);
    assertThat(options.intVal).isEqualTo(56);
    assertThat(options.longVal).isEqualTo(78L);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("123456"));
  }

  @Test
  void testParseFor_optionsAreReferenceIntegersAndArgsAreNames() {
    class MyOptions {
      @Opt(cfg="byte-val,b")
      Byte byteVal;

      @Opt(cfg="short-val,s")
      Short shortVal;

      @Opt(cfg="int-val,i")
      Integer intVal;

      @Opt(cfg="long-val,l")
      Long longVal;

      @Opt(cfg="bigint-val,g")
      BigInteger bigIntVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "--byte-val", "12", "--short-val", "34", "--int-val", "56",
      "--long-val", "78", "--bigint-val", "123456"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("byteVal")).isTrue();
    assertThat(cmd.hasOpt("shortVal")).isTrue();
    assertThat(cmd.hasOpt("intVal")).isTrue();
    assertThat(cmd.hasOpt("longVal")).isTrue();
    assertThat(cmd.hasOpt("bigIntVal")).isTrue();

    assertThat((Byte)cmd.getOptArg("byteVal")).isEqualTo((byte)12);
    assertThat((Short)cmd.getOptArg("shortVal")).isEqualTo((short)34);
    assertThat((Integer)cmd.getOptArg("intVal")).isEqualTo(56);
    assertThat((Long)cmd.getOptArg("longVal")).isEqualTo(78L);
    assertThat((BigInteger)cmd.getOptArg("bigIntVal")).isEqualTo(123456);

    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).containsExactly((byte)12);
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).containsExactly((short)34);
    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).containsExactly(56);
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).containsExactly(78L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).containsExactly(new BigInteger("123456"));

    assertThat(options.byteVal).isEqualTo((byte)12);
    assertThat(options.shortVal).isEqualTo((short)34);
    assertThat(options.intVal).isEqualTo(56);
    assertThat(options.longVal).isEqualTo(78L);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("123456"));
  }

  @Test
  void testParseFor_optionsArePrimitiveFloatAndArgsAreNames() {
    class MyOptions {
      @Opt(cfg="float-val,f")
      float floatVal;

      @Opt(cfg="double-val,d")
      double doubleVal;

      @Opt(cfg="bigdecimal-val,i")
      BigDecimal bigDecimalVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "--float-val", "0.1234", "--double-val", "0.5678", "abc",
      "--bigdecimal-val", "987.654"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("floatVal")).isTrue();
    assertThat(cmd.hasOpt("doubleVal")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalVal")).isTrue();

    assertThat((Float)cmd.getOptArg("floatVal")).isEqualTo(0.1234f);
    assertThat((Double)cmd.getOptArg("doubleVal")).isEqualTo(0.5678);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalVal"))
      .isEqualTo(new BigDecimal("987.654"));

    List<Float> floatL = cmd.getOptArgs("floatVal");
    assertThat(floatL).containsExactly(0.1234f);
    List<Double> doubleL = cmd.getOptArgs("doubleVal");
    assertThat(doubleL).containsExactly(0.5678);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalVal");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("987.654"));

    assertThat(options.floatVal).isEqualTo(0.1234f);
    assertThat(options.doubleVal).isEqualTo(0.5678);
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("987.654"));
  }

  @Test
  void testParseFor_optionsArePrimitiveFloatAndArgsAreAliases() {
    class MyOptions {
      @Opt(cfg="float-val,f")
      float floatVal;

      @Opt(cfg="double-val,d")
      double doubleVal;

      @Opt(cfg="bigdecimal-val,i")
      BigDecimal bigDecimalVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "-f", "0.1234", "-d", "0.5678", "abc", "-i", "987.654"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("floatVal")).isTrue();
    assertThat(cmd.hasOpt("doubleVal")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalVal")).isTrue();

    assertThat((Float)cmd.getOptArg("floatVal")).isEqualTo(0.1234f);
    assertThat((Double)cmd.getOptArg("doubleVal")).isEqualTo(0.5678);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalVal"))
      .isEqualTo(new BigDecimal("987.654"));

    List<Float> floatL = cmd.getOptArgs("floatVal");
    assertThat(floatL).containsExactly(0.1234f);
    List<Double> doubleL = cmd.getOptArgs("doubleVal");
    assertThat(doubleL).containsExactly(0.5678);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalVal");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("987.654"));

    assertThat(options.floatVal).isEqualTo(0.1234f);
    assertThat(options.doubleVal).isEqualTo(0.5678);
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("987.654"));
  }

  @Test
  void testParseFor_optionsAreReferenceFloatAndArgsAreNames() {
    class MyOptions {
      @Opt(cfg="float-val,f")
      Float floatVal;

      @Opt(cfg="double-val,d")
      Double doubleVal;

      @Opt(cfg="bigdecimal-val,i")
      BigDecimal bigDecimalVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("/path/to/app", new String[]{
      "--float-val", "0.1234", "--double-val", "0.5678", "abc",
      "--bigdecimal-val", "987.654"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("floatVal")).isTrue();
    assertThat(cmd.hasOpt("doubleVal")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalVal")).isTrue();

    assertThat((Float)cmd.getOptArg("floatVal")).isEqualTo(0.1234f);
    assertThat((Double)cmd.getOptArg("doubleVal")).isEqualTo(0.5678);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalVal"))
      .isEqualTo(new BigDecimal("987.654"));

    List<Float> floatL = cmd.getOptArgs("floatVal");
    assertThat(floatL).containsExactly(0.1234f);
    List<Double> doubleL = cmd.getOptArgs("doubleVal");
    assertThat(doubleL).containsExactly(0.5678);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalVal");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("987.654"));

    assertThat(options.floatVal).isEqualTo(0.1234f);
    assertThat(options.doubleVal).isEqualTo(0.5678);
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("987.654"));
  }

  @Test
  void testParseFor_optionsAreStringAndArgsAreNames() {
    class MyOptions {
      @Opt(cfg="string-val,s")
      String stringVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "--string-val", "def", "abc"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("stringVal")).isTrue();
    assertThat((String)cmd.getOptArg("stringVal")).isEqualTo("def");
    List<String> stringL = cmd.getOptArgs("stringVal");
    assertThat(stringL).containsExactly("def");

    assertThat(options.stringVal).isEqualTo("def");
  }

  @Test
  void testParseFor_optionsAreStringAndArgsAreAliases() {
    class MyOptions {
      @Opt(cfg="string-val,s")
      String stringVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "-s", "def", "abc"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("stringVal")).isTrue();
    assertThat((String)cmd.getOptArg("stringVal")).isEqualTo("def");
    List<String> stringL = cmd.getOptArgs("stringVal");
    assertThat(stringL).containsExactly("def");

    assertThat(options.stringVal).isEqualTo("def");
  }

  @Test
  void testParseFor_optionsArePrimitiveIntegerArray() {
    class MyOptions {
      @Opt(cfg="int-arr,i")
      int[] intArr;

      @Opt(cfg="byte-arr,b")
      byte[] byteArr;

      @Opt(cfg="short-arr,s")
      short[] shortArr;

      @Opt(cfg="long-arr,l")
      long[] longArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "--int-arr", "123", "abc", "-i", "456",
      "--byte-arr", "1", "-b", "2",
      "--short-arr", "12", "-s", "34",
      "--long-arr", "1234", "-l", "5678",
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(4);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(123);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)1);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)12);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo(1234L);

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(123, 456);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)1, (byte)2);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)12, (short)34);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly(1234L, 5678L);

    assertThat(options.intArr).containsExactly(123, 456);
    assertThat(options.byteArr).containsExactly((byte)1, (byte)2);
    assertThat(options.shortArr).containsExactly((short)12, (short)34);
    assertThat(options.longArr).containsExactly(1234L, 5678L);
  }

  @Test
  void testParseFor_optionsAreReferenceIntegerArray() {
    class MyOptions {
      @Opt(cfg="int-arr,i")
      Integer[] intArr;

      @Opt(cfg="byte-arr,b")
      Byte[] byteArr;

      @Opt(cfg="short-arr,s")
      Short[] shortArr;

      @Opt(cfg="long-arr,l")
      Long[] longArr;

      @Opt(cfg="bigint-arr,g")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "--int-arr", "123", "abc", "-i", "456",
      "--byte-arr", "1", "-b", "2",
      "--short-arr", "12", "-s", "34",
      "--long-arr", "1234", "-l", "5678",
      "--bigint-arr", "12345", "-g", "9876",
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(123);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)1);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)12);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo(1234L);
    assertThat((BigInteger)cmd.getOptArg("bigIntArr"))
      .isEqualTo(new BigInteger("12345"));

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(123, 456);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)1, (byte)2);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)12, (short)34);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly(1234L, 5678L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntArr");
    assertThat(bigIntL)
      .containsExactly(new BigInteger("12345"), new BigInteger("9876"));

    assertThat(options.intArr).containsExactly(123, 456);
    assertThat(options.byteArr).containsExactly((byte)1, (byte)2);
    assertThat(options.shortArr).containsExactly((short)12, (short)34);
    assertThat(options.longArr).containsExactly(1234L, 5678L);
    assertThat(options.bigIntArr)
      .containsExactly(new BigInteger("12345"), new BigInteger("9876"));
  }

  @Test
  void testParseFor_optionsArePrimitiveFloatArray() {
    class MyOptions {
      @Opt(cfg="float-arr,f")
      float[] floatArr;

      @Opt(cfg="double-arr,d")
      double[] doubleArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "--float-arr", "1.23", "abc", "-f", "0.456",
      "--double-arr", "1.234", "-d", "0.5678",
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(2);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isEqualTo(1.23f);
    assertThat((Double)cmd.getOptArg("doubleArr")).isEqualTo(1.234);

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).containsExactly(1.23f, 0.456f);
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).containsExactly(1.234, 0.5678);

    assertThat(options.floatArr).containsExactly(1.23f, 0.456f);
    assertThat(options.doubleArr).containsExactly(1.234, 0.5678);
  }

  @Test
  void testParseFor_optionsAreReferenceFloatArray() {
    class MyOptions {
      @Opt(cfg="float-arr,f")
      Float[] floatArr;

      @Opt(cfg="double-arr,d")
      Double[] doubleArr;

      @Opt(cfg="bigdecimal-arr,b")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "-f", "1.23", "abc", "--float-arr", "0.456",
      "-d", "1.234", "--double-arr", "0.5678",
      "-b", "1.2345", "--bigdecimal-arr", "0.6789",
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isEqualTo(1.23f);
    assertThat((Double)cmd.getOptArg("doubleArr")).isEqualTo(1.234);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr"))
      .isEqualTo(new BigDecimal("1.2345"));

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).containsExactly(1.23f, 0.456f);
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).containsExactly(1.234, 0.5678);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL)
      .containsExactly(new BigDecimal("1.2345"), new BigDecimal("0.6789"));

    assertThat(options.floatArr).containsExactly(1.23f, 0.456f);
    assertThat(options.doubleArr).containsExactly(1.234, 0.5678);
    assertThat(options.bigDecimalArr)
      .containsExactly(new BigDecimal("1.2345"), new BigDecimal("0.6789"));
  }

  @Test
  void testParseFor_optionsAreStringArray() {
    class MyOptions {
      @Opt(cfg="string-arr,s")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app", new String[]{
      "--string-arr", "xxx", "abc", "-s", "yyy"
    });
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abc");

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isEqualTo("xxx");
    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).containsExactly("xxx", "yyy");

    assertThat(options.stringArr).containsExactly("xxx", "yyy");
  }

  @Test
  void testParseFor_defaultValueIsInteger() {
    class MyOptions {
      @Opt(cfg="=11")
      int intVal;

      @Opt(cfg="=22")
      byte byteVal;

      @Opt(cfg="=33")
      short shortVal;

      @Opt(cfg="=44")
      long longVal;

      @Opt(cfg="=55")
      BigInteger bigIntVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intVal")).isTrue();
    assertThat(cmd.hasOpt("byteVal")).isTrue();
    assertThat(cmd.hasOpt("shortVal")).isTrue();
    assertThat(cmd.hasOpt("longVal")).isTrue();
    assertThat(cmd.hasOpt("bigIntVal")).isTrue();

    assertThat((Integer)cmd.getOptArg("intVal")).isEqualTo(11);
    assertThat((Byte)cmd.getOptArg("byteVal")).isEqualTo((byte)22);
    assertThat((Short)cmd.getOptArg("shortVal")).isEqualTo((short)33);
    assertThat((Long)cmd.getOptArg("longVal")).isEqualTo((long)44);
    assertThat((BigInteger)cmd.getOptArg("bigIntVal"))
      .isEqualTo(new BigInteger("55"));

    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).containsExactly(11);
    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).containsExactly((byte)22);
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).containsExactly((short)33);
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).containsExactly(44L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).containsExactly(new BigInteger("55"));

    assertThat(options.intVal).isEqualTo(11);
    assertThat(options.byteVal).isEqualTo((byte)22);
    assertThat(options.shortVal).isEqualTo((short)33);
    assertThat(options.longVal).isEqualTo(44L);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("55"));
  }

  @Test
  void testParseFor_defaultValueIsNegativeInteger() {
    class MyOptions {
      @Opt(cfg="=-11")
      int intVal;

      @Opt(cfg="=-22")
      byte byteVal;

      @Opt(cfg="=-33")
      short shortVal;

      @Opt(cfg="=-44")
      long longVal;

      @Opt(cfg="=-55")
      BigInteger bigIntVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intVal")).isTrue();
    assertThat(cmd.hasOpt("byteVal")).isTrue();
    assertThat(cmd.hasOpt("shortVal")).isTrue();
    assertThat(cmd.hasOpt("longVal")).isTrue();
    assertThat(cmd.hasOpt("bigIntVal")).isTrue();

    assertThat((Integer)cmd.getOptArg("intVal")).isEqualTo(-11);
    assertThat((Byte)cmd.getOptArg("byteVal")).isEqualTo((byte)-22);
    assertThat((Short)cmd.getOptArg("shortVal")).isEqualTo((short)-33);
    assertThat((Long)cmd.getOptArg("longVal")).isEqualTo((long)-44);
    assertThat((BigInteger)cmd.getOptArg("bigIntVal"))
      .isEqualTo(new BigInteger("-55"));

    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).containsExactly(-11);
    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).containsExactly((byte)-22);
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).containsExactly((short)-33);
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).containsExactly(-44L);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).containsExactly(new BigInteger("-55"));

    assertThat(options.intVal).isEqualTo(-11);
    assertThat(options.byteVal).isEqualTo((byte)-22);
    assertThat(options.shortVal).isEqualTo((short)-33);
    assertThat(options.longVal).isEqualTo(-44L);
    assertThat(options.bigIntVal).isEqualTo(new BigInteger("-55"));
  }

  @Test
  void testParseFor_defaultValueIsFloat() {
    class MyOptions {
      @Opt(cfg="=0.123")
      float floatVal;

      @Opt(cfg="=0.456789")
      double doubleVal;

      @Opt(cfg="=0.9876")
      BigDecimal bigDecimalVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatVal")).isTrue();
    assertThat(cmd.hasOpt("doubleVal")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalVal")).isTrue();

    assertThat((Float)cmd.getOptArg("floatVal")).isEqualTo(0.123f);
    assertThat((Double)cmd.getOptArg("doubleVal")).isEqualTo(0.456789);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalVal"))
      .isEqualTo(new BigDecimal("0.9876"));

    List<Float> floatL = cmd.getOptArgs("floatVal");
    assertThat(floatL).containsExactly(0.123f);
    List<Double> doubleL = cmd.getOptArgs("doubleVal");
    assertThat(doubleL).containsExactly(0.456789);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalVal");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("0.9876"));

    assertThat(options.floatVal).isEqualTo(0.123f);
    assertThat(options.doubleVal).isEqualTo(0.456789);
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("0.9876"));
  }

  @Test
  void testParseFor_defaultValueIsNegativeFloat() {
    class MyOptions {
      @Opt(cfg="=-0.123")
      float floatVal;

      @Opt(cfg="=-0.456789")
      double doubleVal;

      @Opt(cfg="=-0.9876")
      BigDecimal bigDecimalVal;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatVal")).isTrue();
    assertThat(cmd.hasOpt("doubleVal")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalVal")).isTrue();

    assertThat((Float)cmd.getOptArg("floatVal")).isEqualTo(-0.123f);
    assertThat((Double)cmd.getOptArg("doubleVal")).isEqualTo(-0.456789);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalVal"))
      .isEqualTo(new BigDecimal("-0.9876"));

    List<Float> floatL = cmd.getOptArgs("floatVal");
    assertThat(floatL).containsExactly(-0.123f);
    List<Double> doubleL = cmd.getOptArgs("doubleVal");
    assertThat(doubleL).containsExactly(-0.456789);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalVal");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("-0.9876"));

    assertThat(options.floatVal).isEqualTo(-0.123f);
    assertThat(options.doubleVal).isEqualTo(-0.456789);
    assertThat(options.bigDecimalVal).isEqualTo(new BigDecimal("-0.9876"));
  }

  @Test
  void testParseFor_defaultValueIsIntegerArrayAndSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      int[] intArr;

      @Opt(cfg="=[]")
      byte[] byteArr;

      @Opt(cfg="=[]")
      short[] shortArr;

      @Opt(cfg="=[]")
      long[] longArr;

      @Opt(cfg="=[]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isNull();
    assertThat((Byte)cmd.getOptArg("byteArr")).isNull();
    assertThat((Short)cmd.getOptArg("shortArr")).isNull();
    assertThat((Long)cmd.getOptArg("longArr")).isNull();
    assertThat((BigDecimal)cmd.getOptArg("bigIntArr")).isNull();

    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).isEmpty();
    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).isEmpty();
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).isEmpty();
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).isEmpty();
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).isEmpty();

    assertThat(options.intArr).isEmpty();
    assertThat(options.byteArr).isEmpty();
    assertThat(options.shortArr).isEmpty();
    assertThat(options.longArr).isEmpty();
    assertThat(options.bigIntArr).isEmpty();
  }

  @Test
  void testParseFor_overwriteIntegerArrayWithDefaultValueIfSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      int[] intArr;

      @Opt(cfg="=[]")
      byte[] byteArr;

      @Opt(cfg="=[]")
      short[] shortArr;

      @Opt(cfg="=[]")
      long[] longArr;

      @Opt(cfg="=[]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();
    options.intArr = new int[]{1};
    options.byteArr = new byte[]{(byte)2};
    options.shortArr = new short[]{(short)3};
    options.longArr = new long[]{(long)4};
    options.bigIntArr = new BigInteger[]{new BigInteger("5")};

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isNull();
    assertThat((Byte)cmd.getOptArg("byteArr")).isNull();
    assertThat((Short)cmd.getOptArg("shortArr")).isNull();
    assertThat((Long)cmd.getOptArg("longArr")).isNull();
    assertThat((BigInteger)cmd.getOptArg("bigIntArr")).isNull();

    List<Integer> intL = cmd.getOptArgs("intVal");
    assertThat(intL).isEmpty();
    List<Byte> byteL = cmd.getOptArgs("byteVal");
    assertThat(byteL).isEmpty();
    List<Short> shortL = cmd.getOptArgs("shortVal");
    assertThat(shortL).isEmpty();
    List<Long> longL = cmd.getOptArgs("longVal");
    assertThat(longL).isEmpty();
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntVal");
    assertThat(bigIntL).isEmpty();

    assertThat(options.intArr).isEmpty();
    assertThat(options.byteArr).isEmpty();
    assertThat(options.shortArr).isEmpty();
    assertThat(options.longArr).isEmpty();
    assertThat(options.bigIntArr).isEmpty();
  }

  @Test
  void testParseFor_defaultValueIsIntegerArrayAndSize1() {
    class MyOptions {
      @Opt(cfg="=[1]")
      int[] intArr;

      @Opt(cfg="=[2]")
      byte[] byteArr;

      @Opt(cfg="=[3]")
      short[] shortArr;

      @Opt(cfg="=[4]")
      long[] longArr;

      @Opt(cfg="=[5]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(1);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)2);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)3);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo((long)4);
    assertThat((BigInteger)cmd.getOptArg("bigIntArr"))
      .isEqualTo(new BigInteger("5"));

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(1);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)2);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)3);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly((long)4);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntArr");
    assertThat(bigIntL).containsExactly(new BigInteger("5"));

    assertThat(options.intArr).containsExactly(1);
    assertThat(options.byteArr).containsExactly(2);
    assertThat(options.shortArr).containsExactly(3);
    assertThat(options.longArr).containsExactly(4);
    assertThat(options.bigIntArr).containsExactly(new BigInteger("5"));
  }

  @Test
  void testParseFor_defaultValueIsIntegerArrayAndSize2() {
    class MyOptions {
      @Opt(cfg="=[1,2]")
      int[] intArr;

      @Opt(cfg="=[2,3]")
      byte[] byteArr;

      @Opt(cfg="=[3,4]")
      short[] shortArr;

      @Opt(cfg="=[4,5]")
      long[] longArr;

      @Opt(cfg="=[6,7]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(5);

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(1);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)2);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)3);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo((long)4);
    assertThat((BigInteger)cmd.getOptArg("bigIntArr"))
      .isEqualTo(new BigInteger("6"));

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(1, 2);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)2, (byte)3);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)3, (short)4);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly((long)4, (long)5);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntArr");
    assertThat(bigIntL)
      .containsExactly(new BigInteger("6"), new BigInteger("7"));

    assertThat(options.intArr).containsExactly(1, 2);
    assertThat(options.byteArr).containsExactly((byte)2, (byte)3);
    assertThat(options.shortArr).containsExactly((short)3, (short)4);
    assertThat(options.longArr).containsExactly((long)4, (long)5);
    assertThat(options.bigIntArr)
      .containsExactly(new BigInteger("6"), new BigInteger("7"));
  }

  @Test
  void testParseFor_defaultValueIsNegativeIntegerArrays() {
    class MyOptions {
      @Opt(cfg="=[-1,-2]")
      int[] intArr;

      @Opt(cfg="=[-2,-3]")
      byte[] byteArr;

      @Opt(cfg="=[-3,-4]")
      short[] shortArr;

      @Opt(cfg="=[-4,-5]")
      long[] longArr;

      @Opt(cfg="=[-6,-7]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(5);

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(-1);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)-2);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)-3);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo((long)-4);
    assertThat((BigInteger)cmd.getOptArg("bigIntArr"))
      .isEqualTo(new BigInteger("-6"));

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(-1, -2);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)-2, (byte)-3);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)-3, (short)-4);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly((long)-4, (long)-5);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntArr");
    assertThat(bigIntL)
      .containsExactly(new BigInteger("-6"), new BigInteger("-7"));

    assertThat(options.intArr).containsExactly(-1, -2);
    assertThat(options.byteArr).containsExactly((byte)-2, (byte)-3);
    assertThat(options.shortArr).containsExactly((short)-3, (short)-4);
    assertThat(options.longArr).containsExactly((long)-4, (long)-5);
    assertThat(options.bigIntArr)
      .containsExactly(new BigInteger("-6"), new BigInteger("-7"));
  }

  @Test
  void testParseFor_defaultValueIsIntegerArraySeparatedByOtherMark() {
    class MyOptions {
      @Opt(cfg="=:[-1:-2]")
      int[] intArr;

      @Opt(cfg="=/[-2/-3]")
      byte[] byteArr;

      @Opt(cfg="=![-3!-4]")
      short[] shortArr;

      @Opt(cfg="=|[-4|-5]")
      long[] longArr;

      @Opt(cfg="=$[-6$-7]")
      BigInteger[] bigIntArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(5);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isTrue();
    assertThat(cmd.hasOpt("byteArr")).isTrue();
    assertThat(cmd.hasOpt("shortArr")).isTrue();
    assertThat(cmd.hasOpt("longArr")).isTrue();
    assertThat(cmd.hasOpt("bigIntArr")).isTrue();

    assertThat((Integer)cmd.getOptArg("intArr")).isEqualTo(-1);
    assertThat((Byte)cmd.getOptArg("byteArr")).isEqualTo((byte)-2);
    assertThat((Short)cmd.getOptArg("shortArr")).isEqualTo((short)-3);
    assertThat((Long)cmd.getOptArg("longArr")).isEqualTo((long)-4);
    assertThat((BigInteger)cmd.getOptArg("bigIntArr"))
      .isEqualTo(new BigInteger("-6"));

    List<Integer> intL = cmd.getOptArgs("intArr");
    assertThat(intL).containsExactly(-1, -2);
    List<Byte> byteL = cmd.getOptArgs("byteArr");
    assertThat(byteL).containsExactly((byte)-2, (byte)-3);
    List<Short> shortL = cmd.getOptArgs("shortArr");
    assertThat(shortL).containsExactly((short)-3, (short)-4);
    List<Long> longL = cmd.getOptArgs("longArr");
    assertThat(longL).containsExactly((long)-4, (long)-5);
    List<BigInteger> bigIntL = cmd.getOptArgs("bigIntArr");
    assertThat(bigIntL)
      .containsExactly(new BigInteger("-6"), new BigInteger("-7"));

    assertThat(options.intArr).containsExactly(-1, -2);
    assertThat(options.byteArr).containsExactly((byte)-2, (byte)-3);
    assertThat(options.shortArr).containsExactly((short)-3, (short)-4);
    assertThat(options.longArr).containsExactly((long)-4, (long)-5);
    assertThat(options.bigIntArr)
      .containsExactly(new BigInteger("-6"), new BigInteger("-7"));
  }

  @Test
  void testParseFor_defaultValueIsFloatArrayAndSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      float[] floatArr;

      @Opt(cfg="=[]")
      double[] doubleArr;

      @Opt(cfg="=[]")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isNull();
    assertThat((Double)cmd.getOptArg("doubleArr")).isNull();
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr")).isNull();

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).isEmpty();
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).isEmpty();
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL).isEmpty();

    assertThat(options.floatArr).isEmpty();
    assertThat(options.doubleArr).isEmpty();
    assertThat(options.bigDecimalArr).isEmpty();
  }

  @Test
  void testParseFor_overwriteFloatArrayWithDefaultValueIfSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      float[] floatArr;

      @Opt(cfg="=[]")
      double[] doubleArr;

      @Opt(cfg="=[]")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();
    options.floatArr = new float[]{0.1f, 0.2f};
    options.doubleArr = new double[]{0.3, 0.4};
    options.bigDecimalArr = new BigDecimal[]{
      new BigDecimal("0.5"), new BigDecimal("0.6")
    };

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isNull();
    assertThat((Double)cmd.getOptArg("doubleArr")).isNull();
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr")).isNull();

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).isEmpty();
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).isEmpty();
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL).isEmpty();

    assertThat(options.floatArr).isEmpty();
    assertThat(options.doubleArr).isEmpty();
    assertThat(options.bigDecimalArr).isEmpty();
  }

  @Test
  void testParseFor_defaultValueIsFloatArrayAndSize1() {
    class MyOptions {
      @Opt(cfg="=[0.1]")
      float[] floatArr;

      @Opt(cfg="=[0.2]")
      double[] doubleArr;

      @Opt(cfg="=[0.3]")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isEqualTo(0.1f);
    assertThat((Double)cmd.getOptArg("doubleArr")).isEqualTo(0.2);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr"))
      .isEqualTo(new BigDecimal("0.3"));

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).containsExactly(0.1f);
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).containsExactly(0.2);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL).containsExactly(new BigDecimal("0.3"));

    assertThat(options.floatArr).containsExactly(0.1f);
    assertThat(options.doubleArr).containsExactly(0.2);
    assertThat(options.bigDecimalArr).containsExactly(new BigDecimal("0.3"));
  }

  @Test
  void testParseFor_defaultValueIsFloatArrayAndSize2() {
    class MyOptions {
      @Opt(cfg="=[0.1,0.2]")
      float[] floatArr;

      @Opt(cfg="=[0.2,0.3]")
      double[] doubleArr;

      @Opt(cfg="=[0.3,0.4]")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isEqualTo(0.1f);
    assertThat((Double)cmd.getOptArg("doubleArr")).isEqualTo(0.2);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr"))
      .isEqualTo(new BigDecimal("0.3"));

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).containsExactly(0.1f, 0.2f);
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).containsExactly(0.2, 0.3);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL)
      .containsExactly(new BigDecimal("0.3"), new BigDecimal("0.4"));

    assertThat(options.floatArr).containsExactly(0.1f, 0.2f);
    assertThat(options.doubleArr).containsExactly(0.2, 0.3);
    assertThat(options.bigDecimalArr)
      .containsExactly(new BigDecimal("0.3"), new BigDecimal("0.4"));
  }

  @Test
  void testParseFor_defaultValueIsFloatArraySeparatedByOtherMarks() {
    class MyOptions {
      @Opt(cfg="=:[0.1:0.2]")
      float[] floatArr;

      @Opt(cfg="=/[0.2/0.3]")
      double[] doubleArr;

      @Opt(cfg="=|[0.3|0.4]")
      BigDecimal[] bigDecimalArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(3);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("floatArr")).isTrue();
    assertThat(cmd.hasOpt("doubleArr")).isTrue();
    assertThat(cmd.hasOpt("bigDecimalArr")).isTrue();

    assertThat((Float)cmd.getOptArg("floatArr")).isEqualTo(0.1f);
    assertThat((Double)cmd.getOptArg("doubleArr")).isEqualTo(0.2);
    assertThat((BigDecimal)cmd.getOptArg("bigDecimalArr"))
      .isEqualTo(new BigDecimal("0.3"));

    List<Float> floatL = cmd.getOptArgs("floatArr");
    assertThat(floatL).containsExactly(0.1f, 0.2f);
    List<Double> doubleL = cmd.getOptArgs("doubleArr");
    assertThat(doubleL).containsExactly(0.2, 0.3);
    List<BigDecimal> bigDecimalL = cmd.getOptArgs("bigDecimalArr");
    assertThat(bigDecimalL)
      .containsExactly(new BigDecimal("0.3"), new BigDecimal("0.4"));

    assertThat(options.floatArr).containsExactly(0.1f, 0.2f);
    assertThat(options.doubleArr).containsExactly(0.2, 0.3);
    assertThat(options.bigDecimalArr)
      .containsExactly(new BigDecimal("0.3"), new BigDecimal("0.4"));
  }

  @Test
  void testParseFor_defaultValueIsStringAndSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isNull();

    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).isEmpty();

    assertThat(options.stringArr).isEmpty();
  }

  @Test
  void testParseFor_overwriteStringArrayWithDefaultValueIfSize0() {
    class MyOptions {
      @Opt(cfg="=[]")
      String[] stringArr;
    }

    var options = new MyOptions();
    options.stringArr = new String[]{"ZZZ"};

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isNull();

    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).isEmpty();

    assertThat(options.stringArr).isEmpty();
  }

  @Test
  void testParseFor_defaultValueIsStringArrayAndSize1() {
    class MyOptions {
      @Opt(cfg="=[ABC]")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isEqualTo("ABC");

    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).containsExactly("ABC");

    assertThat(options.stringArr).containsExactly("ABC");
  }

  @Test
  void testParseFor_defaultValueIsStringArrayAndSize2() {
    class MyOptions {
      @Opt(cfg="=[ABC,DEF]")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isEqualTo("ABC");

    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).containsExactly("ABC", "DEF");

    assertThat(options.stringArr).containsExactly("ABC", "DEF");
  }

  @Test
  void testParseFor_defaultValueIsStringArraySeparatedByColons() {
    class MyOptions {
      @Opt(cfg="=|[ABC|DEF]")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isEqualTo("ABC");

    List<String> stringL = cmd.getOptArgs("stringArr");
    assertThat(stringL).containsExactly("ABC", "DEF");

    assertThat(options.stringArr).containsExactly("ABC", "DEF");
  }

  @Test
  void testParseFor_error_ConfigHasDefaultsButHasNoArg() {
    class MyOptions {
      @Opt(cfg="=")
      boolean boolVar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.optCfgs()).hasSize(1);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.ConfigHasDefaultsButHasNoArg r -> {
        assertThat(r.storeKey()).isEqualTo("boolVar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("boolVar")).isFalse();
    assertThat((String)cmd.getOptArg("boolVar")).isNull();

    List<Object> list = cmd.getOptArgs("boolVar");
    assertThat(list).isEmpty();

    assertThat(options.boolVar).isFalse();
  }

  @Test
  void testParseFor_error_optionIsIntButDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="int-var=")
      int intVar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.FailToConvertDefaultsInOptAnnotation r -> {
        assertThat(r.storeKey()).isEqualTo("intVar");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (ReasonedException.class.cast(exc.getCause()).getReason()) {
      case InvalidIntegerFormat r -> {
        assertThat(r.storeKey()).isEqualTo("intVar");
        assertThat(r.option()).isEqualTo("intVar");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc.getCause());
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intVar")).isFalse();
    assertThat((String)cmd.getOptArg("intVar")).isNull();

    List<Object> list = cmd.getOptArgs("intVar");
    assertThat(list).isEmpty();

    assertThat(options.intVar).isEqualTo(0);
  }

  @Test
  void testParseFor_error_optionIsDoubleButDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="double-var=")
      double doubleVar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.FailToConvertDefaultsInOptAnnotation r -> {
        assertThat(r.storeKey()).isEqualTo("doubleVar");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (ReasonedException.class.cast(exc.getCause()).getReason()) {
      case InvalidDoubleFormat r -> {
        assertThat(r.storeKey()).isEqualTo("doubleVar");
        assertThat(r.option()).isEqualTo("doubleVar");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc.getCause());
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("doubleVar")).isFalse();
    assertThat((String)cmd.getOptArg("doubleVar")).isNull();

    List<Object> list = cmd.getOptArgs("doubleVar");
    assertThat(list).isEmpty();

    assertThat(options.doubleVar).isEqualTo(0);
  }

  @Test
  void testParseFor_optionIsStringAndDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="string-var=")
      String stringVar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(1);

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringVar")).isTrue();
    assertThat((String)cmd.getOptArg("stringVar")).isEqualTo("");

    List<Object> list = cmd.getOptArgs("stringVar");
    assertThat(list).containsExactly("");

    assertThat(options.stringVar).isEqualTo("");
  }

  @Test
  void testParseFor_error_optionIsIntegerArrayButDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="int-arr=")
      int[] intArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.FailToConvertDefaultsInOptAnnotation r -> {
        assertThat(r.storeKey()).isEqualTo("intArr");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (ReasonedException.class.cast(exc.getCause()).getReason()) {
      case InvalidIntegerFormat r -> {
        assertThat(r.storeKey()).isEqualTo("intArr");
        assertThat(r.option()).isEqualTo("intArr");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc.getCause());
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("intArr")).isFalse();
    assertThat((String)cmd.getOptArg("intArr")).isNull();

    List<Object> list = cmd.getOptArgs("intArr");
    assertThat(list).isEmpty();

    assertThat(options.intArr).isNull();
  }

  @Test
  void testParseFor_error_optionIsDoubleArrayButDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="double-arr=")
      double[] doubleArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.FailToConvertDefaultsInOptAnnotation r -> {
        assertThat(r.storeKey()).isEqualTo("doubleArr");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (ReasonedException.class.cast(exc.getCause()).getReason()) {
      case InvalidDoubleFormat r -> {
        assertThat(r.storeKey()).isEqualTo("doubleArr");
        assertThat(r.option()).isEqualTo("doubleArr");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc.getCause());
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("doubleArr")).isFalse();
    assertThat((String)cmd.getOptArg("doubleArr")).isNull();

    List<Object> list = cmd.getOptArgs("doubleArr");
    assertThat(list).isEmpty();

    assertThat(options.doubleArr).isNull();
  }

  @Test
  void testParseFor_optionIsStringArrayAndDefaultValueIsEmpty() {
    class MyOptions {
      @Opt(cfg="string-arr=")
      String[] stringArr;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(1);

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("stringArr")).isTrue();
    assertThat((String)cmd.getOptArg("stringArr")).isEqualTo("");

    List<Object> list = cmd.getOptArgs("stringArr");
    assertThat(list).containsExactly("");

    assertThat(options.stringArr).containsExactly("");
  }

  @Test
  void testParseFor_optCfgHasUnsupportedType() {
    class A {}
    class MyOptions {
      @Opt(cfg="foo-bar,f", desc="FooBar description")
      A fooBar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.IllegalOptionType r -> {
        System.out.println(r);
      }
      default -> fail(exc);
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("fooBar")).isFalse();
    assertThat((String)cmd.getOptArg("fooBar")).isNull();

    List<Object> list = cmd.getOptArgs("fooBar");
    assertThat(list).isEmpty();

    assertThat(options.fooBar).isNull();
  }

  @Test
  void testParseFor_error_ifDefaultValueIsInvalidType() {
    class MyOptions {
      @Opt(cfg="foo-bar=ABC")
      int fooBar;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    var exc = result.exception();
    switch (exc.getReason()) {
      case CliArgs.FailToConvertDefaultsInOptAnnotation r -> {
        assertThat(r.storeKey()).isEqualTo("fooBar");
        assertThat(r.optArg()).isEqualTo("ABC");
      }
      default -> fail(exc);
    }
    switch (ReasonedException.class.cast(exc.getCause()).getReason()) {
      case InvalidIntegerFormat r -> {
        assertThat(r.storeKey()).isEqualTo("fooBar");
        assertThat(r.option()).isEqualTo("fooBar");
        assertThat(r.optArg()).isEqualTo("ABC");
      }
      default -> fail(exc.getCause());
    }

    assertThat(result.optCfgs()).isNull();

    var cmd = result.cmd();

    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("fooBar")).isFalse();
    assertThat((String)cmd.getOptArg("fooBar")).isNull();

    List<Object> list = cmd.getOptArgs("fooBar");
    assertThat(list).isEmpty();

    assertThat(options.fooBar).isEqualTo(0);
  }

  @Test
  void testParseFor_optionDescriptions() {
    class MyOptions {
      @Opt(desc="FooBar description")
      boolean fooBar;

      @Opt(desc="Baz description")
      int baz;

      @Opt(desc="Qux description")
      String qux;

      @Opt(desc="Quux description")
      String[] quux;

      @Opt
      int[] corge;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(5);

    var cfgs = result.optCfgs();
    assertThat(cfgs[0].desc).isEqualTo("FooBar description");
    assertThat(cfgs[1].desc).isEqualTo("Baz description");
    assertThat(cfgs[2].desc).isEqualTo("Qux description");
    assertThat(cfgs[3].desc).isEqualTo("Quux description");
    assertThat(cfgs[4].desc).isEqualTo("");
  }

  @Test
  void testParseFor_optionArgInHelp() {
    class MyOptions {
      @Opt(arg="{true|false}")
      boolean fooBar;

      @Opt(arg="<num>")
      int baz;

      @Opt(arg="<string>")
      String qux;

      @Opt(arg="<s> ...")
      String[] quux;

      @Opt
      int[] corge;
    }

    var options = new MyOptions();

    var cliargs = new CliArgs("path/to/app");
    var result = cliargs.parseFor(options);

    assertThat(result.exception()).isNull();
    assertThat(result.optCfgs()).hasSize(5);

    var cfgs = result.optCfgs();
    assertThat(cfgs[0].argInHelp).isEqualTo("{true|false}");
    assertThat(cfgs[1].argInHelp).isEqualTo("<num>");
    assertThat(cfgs[2].argInHelp).isEqualTo("<string>");
    assertThat(cfgs[3].argInHelp).isEqualTo("<s> ...");
    assertThat(cfgs[4].argInHelp).isEqualTo("");
  }
}
