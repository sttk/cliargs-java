package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.storeKey;
import static com.github.sttk.cliargs.OptCfg.NamedParam.names;
import static com.github.sttk.cliargs.OptCfg.NamedParam.hasArg;
import static com.github.sttk.cliargs.OptCfg.NamedParam.isArray;
import static com.github.sttk.cliargs.OptCfg.NamedParam.defaults;
import static com.github.sttk.cliargs.OptCfg.NamedParam.type;
import static com.github.sttk.cliargs.OptCfg.NamedParam.converter;
import static com.github.sttk.cliargs.OptCfg.NamedParam.postparser;
import com.github.sttk.exception.ReasonedException;
import com.github.sttk.cliargs.CliArgs.ConfigHasDefaultsButHasNoArg;
import com.github.sttk.cliargs.CliArgs.ConfigIsArrayButHasNoArg;
import com.github.sttk.cliargs.CliArgs.FailToConvertOptionArg;
import com.github.sttk.cliargs.CliArgs.StoreKeyIsDuplicated;
import com.github.sttk.cliargs.CliArgs.UnconfiguredOption;
import com.github.sttk.cliargs.CliArgs.InvalidOption;
import com.github.sttk.cliargs.CliArgs.OptionHasInvalidChar;
import com.github.sttk.cliargs.CliArgs.OptionIsNotArray;
import com.github.sttk.cliargs.CliArgs.OptionNameIsDuplicated;
import com.github.sttk.cliargs.CliArgs.OptionNeedsArg;
import com.github.sttk.cliargs.CliArgs.OptionTakesNoArg;
import com.github.sttk.cliargs.convert.IntConverter.InvalidIntegerFormat;
import com.github.sttk.cliargs.convert.IntConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("missing-explicit-ctor")
public class ParseWithTest {

  @Test
  void testParseWith_zeroCfgAndZeroArg() {
    var cfgs = new OptCfg[0];
    var args = new String[0];

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_zeroCfgAndOneCommandArg() {
    var cfgs = new OptCfg[0];
    var args = new String[]{"foo-bar"};

    var cliargs = new CliArgs("/path/to/app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("foo-bar");
  }

  @Test
  void testParseWith_zeroCfgAndOneLongOpt() {
    var cfgs = new OptCfg[0];
    var args = new String[]{"--foo-bar"};

    var cliargs = new CliArgs("/path/to/app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case UnconfiguredOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_zeroCfgAndOneShortOpt() {
    var cfgs = new OptCfg[0];
    var args = new String[]{"-f"};

    var cliargs = new CliArgs("/path/to/app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case UnconfiguredOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgAndZeroOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[] {
      new OptCfg(names("foo-bar"))
    };
    var args = new String[0];

    var cliargs = new CliArgs("path/to/app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgAndOneCmdArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"))
    };
    var args = new String[]{"foo-bar"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("foo-bar");
  }

  @Test
  void testParseWith_oneCfgAndOneLongOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"))
    };
    var args = new String[]{"--foo-bar"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgAndOneShortOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f"))
    };
    var args = new String[]{"-f"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgAndOneDifferentLongOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"))
    };
    var args = new String[]{"--boo-far"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case UnconfiguredOption r -> {
        assertThat(r.option()).isEqualTo("boo-far");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("boo-far");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgAndOneDifferentShortOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f"))
    };
    var args = new String[]{"-b"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case UnconfiguredOption r -> {
        assertThat(r.option()).isEqualTo("b");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("b");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_anyOptCfgAndOneDifferentLongOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar")),
      new OptCfg(names("*"))
    };
    var args = new String[]{"--boo-far"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("boo-far")).isTrue();
    assertThat((String)cmd.getOptArg("boo-far")).isNull();
    assertThat((List<?>)cmd.getOptArgs("boo-far")).isEmpty();
  }

  @Test
  void testParseWith_anyOptCfgAndOneDifferentShortOpt() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f")),
      new OptCfg(names("*"))
    };
    var args = new String[]{"-b"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("b")).isTrue();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasArgAndOneLongOptHasArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"), hasArg(true))
    };

    var args = new String[]{"--foo-bar", "ABC"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars0 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars0).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar=ABC"};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars1 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars1).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar", ""};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("");
    List<String> fooBar2 = cmd.getOptArgs("foo-bar");
    assertThat(fooBar2).containsExactly("");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar="};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("");
    List<String> fooBar3 = cmd.getOptArgs("foo-bar");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasArgAndOneShortOptHasArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f"), hasArg(true))
    };

    var args = new String[]{"-f", "ABC"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("ABC");
    List<String> f0 = cmd.getOptArgs("f");
    assertThat(f0).containsExactly("ABC");
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f=ABC"};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("ABC");
    List<String> f1 = cmd.getOptArgs("f");
    assertThat(f1).containsExactly("ABC");
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f", ""};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("");
    List<String> fooBar2 = cmd.getOptArgs("f");
    assertThat(fooBar2).containsExactly("");
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f="};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("");
    List<String> fooBar3 = cmd.getOptArgs("f");
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasArgButOneLongOptHasNoArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"), hasArg(true))
    };

    var args = new String[]{"--foo-bar"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionNeedsArg r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
        assertThat(r.storeKey()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasArgAndOneShortOptHasNoArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f"), hasArg(true))
    };

    var args = new String[]{"-f"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionNeedsArg r -> {
        assertThat(r.option()).isEqualTo("f");
        assertThat(r.storeKey()).isEqualTo("f");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasNoArgAndOneLongOptHasArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"))
    };

    var args = new String[]{"--foo-bar", "ABC"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("ABC");

    args = new String[]{"--foo-bar=ABC"};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
        assertThat(r.storeKey()).isEqualTo("foo-bar");
        assertThat(r.optArg()).isEqualTo("ABC");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar", ""};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("");

    args = new String[]{"--foo-bar="};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
        assertThat(r.storeKey()).isEqualTo("foo-bar");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasNoArgAndOneShortOptHasArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("f"))
    };

    var args = new String[]{"-f", "ABC"};

    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("ABC");

    args = new String[]{"-f=ABC"};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("f");
        assertThat(r.storeKey()).isEqualTo("f");
        assertThat(r.optArg()).isEqualTo("ABC");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f", ""};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("");

    args = new String[]{"-f="};

    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("f");
        assertThat(r.storeKey()).isEqualTo("f");
        assertThat(r.optArg()).isEqualTo("");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasNoArgButIsArray() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"), hasArg(false), isArray(true))
    };

    var args = new String[]{"--foo-bar", "ABC"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case ConfigIsArrayButHasNoArg r -> {
        assertThat(r.storeKey()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgIsArrayAndOptHasOneArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"), hasArg(true), isArray(true)),
      new OptCfg(names("f"), hasArg(true), isArray(true))
    };

    var args = new String[]{"--foo-bar", "ABC"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars0 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars0).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar", "ABC", "--foo-bar=DEF"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars1 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars1).containsExactly("ABC", "DEF");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f", "ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("ABC");
    List<String> f0 = cmd.getOptArgs("f");
    assertThat(f0).containsExactly("ABC");
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f", "ABC", "-f=DEF"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isTrue();
    assertThat((String)cmd.getOptArg("f")).isEqualTo("ABC");
    List<String> f1 = cmd.getOptArgs("f");
    assertThat(f1).containsExactly("ABC", "DEF");
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgHasAliasesAndArgMatchesName() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar", "f", "b"), hasArg(true)),
    };

    var args = new String[]{"--foo-bar", "ABC"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars0 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars0).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar=ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars1 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars1).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_hasOptsOfBothNameAndAliases() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar", "f", "b"), hasArg(true), isArray(true))
    };

    var args = new String[]{"--foo-bar", "ABC"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars0 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars0).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"--foo-bar=ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars1 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars1).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f", "ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars2 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars2).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-f=ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars3 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars3).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-b", "ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars4 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars4).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-b=ABC"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars5 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars5).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();

    args = new String[]{"-b=ABC", "-f", "DEF", "--foo-bar", "GHI"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars6 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars6).containsExactly("ABC", "DEF", "GHI");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_oneCfgIsNotArrayButOptsAreMultiple() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar", "f"), hasArg(true), isArray(false))
    };

    var args = new String[]{"--foo-bar", "ABC", "--foo-bar=DEF"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionIsNotArray r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
        assertThat(r.storeKey()).isEqualTo("foo-bar");
        assertThat(r.optArg()).isEqualTo("DEF");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isEqualTo("ABC");
    List<String> fooBars6 = cmd.getOptArgs("foo-bar");
    assertThat(fooBars6).containsExactly("ABC");
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat((String)cmd.getOptArg("b")).isNull();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyDefault() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("bar"), hasArg(true), defaults("A")),
      new OptCfg(names("baz"), hasArg(true), isArray(true), defaults("B"))
    };

    var args = new String[0];
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("bar")).isTrue();
    assertThat((String)cmd.getOptArg("bar")).isEqualTo("A");
    List<String> bar0 = cmd.getOptArgs("bar");
    assertThat(bar0).containsExactly("A");
    assertThat(cmd.hasOpt("baz")).isTrue();
    assertThat((String)cmd.getOptArg("baz")).isEqualTo("B");
    List<String> baz0 = cmd.getOptArgs("baz");
    assertThat(baz0).containsExactly("B");

    args = new String[]{"--bar", "C"};
    cliargs = new CliArgs("app", args);
    result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("bar")).isTrue();
    assertThat((String)cmd.getOptArg("bar")).isEqualTo("C");
    List<String> bar1 = cmd.getOptArgs("bar");
    assertThat(bar1).containsExactly("C");
    assertThat(cmd.hasOpt("baz")).isTrue();
    assertThat((String)cmd.getOptArg("baz")).isEqualTo("B");
    List<String> baz1 = cmd.getOptArgs("baz");
    assertThat(baz1).containsExactly("B");
  }

  @Test
  void testParseWith_oneCfgHasNoArgButHasDefault() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar"), hasArg(false), defaults("A")),
    };

    var args = new String[0];
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case ConfigHasDefaultsButHasNoArg r -> {
        assertThat(r.storeKey()).isEqualTo("foo-bar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isFalse();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_multipleArgs() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo-bar")),
      new OptCfg(names("baz", "z"), hasArg(true), isArray(true)),
      new OptCfg(names("corge"), hasArg(true), defaults("99")),
      new OptCfg(names("*")),
    };

    var args = new String[] {
      "--foo-bar", "qux", "--baz", "1", "-z=2", "-x", "quux"
    };
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat(cmd.hasOpt("baz")).isTrue();
    assertThat(cmd.hasOpt("x")).isTrue();
    assertThat(cmd.hasOpt("corge")).isTrue();
    assertThat((String)cmd.getOptArg("foo-bar")).isNull();
    assertThat((String)cmd.getOptArg("baz")).isEqualTo("1");
    assertThat((String)cmd.getOptArg("corge")).isEqualTo("99");
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    List<String> baz = cmd.getOptArgs("baz");
    assertThat(baz).containsExactly("1", "2");
    List<String> corge = cmd.getOptArgs("corge");
    assertThat(corge).containsExactly("99");
    assertThat(cmd.getArgs()).containsExactly("qux", "quux");
  }

  @Test
  void testParseWith_parseAllArgsEvenIfError() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo", "f")),
    };

    var args = new String[] {"-ef", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case UnconfiguredOption r -> {
        assertThat(r.option()).isEqualTo("e");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("e");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.hasOpt("e")).isFalse();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((String)cmd.getOptArg("e")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("e")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("bar");
  }

  @Test
  void testParseWith_parseAllArgsEvenIfLongOptionValueIsError() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("e")),
      new OptCfg(names("foo", "f"))
    };

    var args = new String[] {"--foo=123", "-e", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("foo");
        assertThat(r.storeKey()).isEqualTo("foo");
        assertThat(r.optArg()).isEqualTo("123");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.hasOpt("e")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((String)cmd.getOptArg("e")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("e")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("bar");
  }

  @Test
  void testParseWith_parseAllArgsEvenIfShortOptionValueIsError() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("e")),
      new OptCfg(names("foo", "f"))
    };

    var args = new String[] {"-ef=123", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionTakesNoArg r -> {
        assertThat(r.option()).isEqualTo("f");
        assertThat(r.storeKey()).isEqualTo("foo");
        assertThat(r.optArg()).isEqualTo("123");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.hasOpt("e")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((String)cmd.getOptArg("f")).isNull();
    assertThat((String)cmd.getOptArg("e")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("f")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("e")).isEmpty();
    assertThat(cmd.getArgs()).containsExactly("bar");
  }

  @Test
  void testParseWith_ignoreCfgIsNamesIsEmpty() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names()),
      new OptCfg(names("foo"))
    };

    var args = new String[] {"--foo", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.getArgs()).containsExactly("bar");
  }

  @Test
  void testParseWith_optionNameIsDuplicated() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo", "f")),
      new OptCfg(names("bar", "f"))
    };

    var args = new String[] {"--foo", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case OptionNameIsDuplicated r -> {
        assertThat(r.storeKey()).isEqualTo("bar");
        assertThat(r.option()).isEqualTo("f");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("bar")).isFalse();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_useStoreKey() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(storeKey("FooBar"), names("f", "foo")),
    };

    var args = new String[] {"--foo", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("FooBar")).isTrue();
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("bar")).isFalse();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.getArgs()).containsExactly("bar");
  }

  @Test
  void testParseWith_storeKeyIsDuplicated() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(storeKey("FooBar"), names("f", "foo")),
      new OptCfg(storeKey("FooBar"), names("b", "bar"))
    };

    var args = new String[] {"--foo", "bar"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    assertThat(exc).isNotNull();
    switch (exc.getReason()) {
      case StoreKeyIsDuplicated r -> {
        assertThat(r.storeKey()).isEqualTo("FooBar");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("FooBar")).isFalse();
    assertThat(cmd.hasOpt("f")).isFalse();
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("b")).isFalse();
    assertThat(cmd.hasOpt("bar")).isFalse();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_acceptAllOptionsIfStoreKeyIsAsterisk() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(storeKey("*"))
    };

    var args = new String[] {"--foo", "--bar", "baz"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.hasOpt("bar")).isTrue();
    assertThat(cmd.getArgs()).containsExactly("baz");
  }

  @Test
  void testParseWith_acceptUnconfiguredOptionEvenIfItMatchesStoreKey() { 
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        storeKey("Bar"),
        names("foo", "f"),
        hasArg(true),
        isArray(true)
      ),
      new OptCfg(storeKey("*"))
    };

    var args = new String[] {"--foo", "1", "-f=2", "--Bar=3"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("Bar")).isTrue();
    assertThat((String)cmd.getOptArg("Bar")).isEqualTo("1");
    List<String> foo = cmd.getOptArgs("Bar");
    assertThat(foo).containsExactly("1", "2", "3");
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Byte() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Byte.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Byte)cmd.getOptArg("foo")).isEqualTo((byte)1);
    List<Byte> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly((byte)1);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Short() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Short.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Short)cmd.getOptArg("foo")).isEqualTo((short)1);
    List<Short> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly((short)1);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Integer() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Integer.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Integer)cmd.getOptArg("foo")).isEqualTo(1);
    List<Integer> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(1);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Long() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Long.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Long)cmd.getOptArg("foo")).isEqualTo(1L);
    List<Long> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly((long)1);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Float() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Float.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Float)cmd.getOptArg("foo")).isEqualTo(1.0f);
    List<Float> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(1.0f);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_Double() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Double.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Double)cmd.getOptArg("foo")).isEqualTo(1.0);
    List<Double> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(1.0);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_BigInteger() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(BigInteger.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((BigInteger)cmd.getOptArg("foo"))
      .isEqualTo(new BigInteger("1"));
    List<BigInteger> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(new BigInteger("1"));
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyType_BigDecimal() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(BigDecimal.class))
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((BigDecimal)cmd.getOptArg("foo"))
      .isEqualTo(new BigDecimal("1"));
    List<BigDecimal> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(new BigDecimal("1"));
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyConverter_UintConverter() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        names("foo"), type(Integer.class), converter(new IntConverter())
      )
    };

    var args = new String[] {"--foo", "1"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((Integer)cmd.getOptArg("foo")).isEqualTo(1);
    List<Integer> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly(1);
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_invalidIntegerFormat() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(names("foo"), type(Integer.class))
    };

    var args = new String[] {"--foo", "A"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    switch (exc.getReason()) {
      case InvalidIntegerFormat r -> {
        assertThat(r.option()).isEqualTo("foo");
        assertThat(r.optArg()).isEqualTo("A");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_failToConvertOptionArg() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        names("foo"),
        type(String.class),
        converter((arg, opt, key) -> {
          throw new Exception("invalid");
        })
      )
    };

    var args = new String[] {"--foo", "A"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    switch (exc.getReason()) {
      case FailToConvertOptionArg r -> {
        assertThat(r.optArg()).isEqualTo("A");
        assertThat(r.option()).isEqualTo("foo");
        assertThat(r.storeKey()).isEqualTo("foo");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_specifyPostparser() {
    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        names("foo", "f"),
        type(String.class),
        isArray(true),
        postparser(list -> {
          var iter = list.listIterator();
          while (iter.hasNext()) {
            iter.set(iter.next().toUpperCase());
          }
        })
      ),
      new OptCfg(
        names("bar", "b"),
        type(Integer.class),
        defaults(123),
        postparser(list -> {
          list.set(0, - list.get(0));
        })
      ),
      new OptCfg(
        names("baz"),
        type(Integer.class),
        postparser(list -> {})
      )
    };

    var args = new String[] {"--foo", "abc", "-f", "def"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.hasOpt("bar")).isTrue();
    assertThat(cmd.hasOpt("baz")).isFalse();

    assertThat((String)cmd.getOptArg("foo")).isEqualTo("ABC");
    assertThat((Integer)cmd.getOptArg("bar")).isEqualTo(-123);
    assertThat((Integer)cmd.getOptArg("baz")).isNull();

    List<String> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly("ABC", "DEF");
    List<Integer> bar = cmd.getOptArgs("bar");
    assertThat(bar).containsExactly(-123);
    List<Integer> baz = cmd.getOptArgs("baz");
    assertThat(baz).isEmpty();

  }

  @Test
  void testParseWith_failToProcessPostparser() {
    record Reason() {}

    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        names("foo", "f"),
        type(String.class),
        isArray(true),
        postparser(l -> {
          throw new ReasonedException(new Reason());
        })
      )
    };

    var args = new String[] {"--foo", "abc", "-f", "def"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    switch (exc.getReason()) {
      case Reason r -> {}
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isEqualTo("abc");
    List<String> foo = cmd.getOptArgs("foo");
    assertThat(foo).containsExactly("abc", "def");
    assertThat(cmd.getArgs()).isEmpty();
  }

  @Test
  void testParseWith_failToConvertOptionArgAndFailToProcessPostparser() {
    record Reason() {}

    @SuppressWarnings("unchecked")
    var cfgs = new OptCfg[]{
      new OptCfg(
        names("foo"),
        type(String.class),
        converter((arg, opt, key) -> {
          throw new Exception("invalid");
        }),
        postparser(list -> {
          throw new ReasonedException(new Reason());
        })
      )
    };

    var args = new String[] {"--foo", "A"};
    var cliargs = new CliArgs("app", args);
    var result = cliargs.parseWith(cfgs);

    assertThat(result.optCfgs()).isEqualTo(cfgs);

    var exc = result.exception();
    switch (exc.getReason()) {
      case FailToConvertOptionArg r -> {
        assertThat(r.optArg()).isEqualTo("A");
        assertThat(r.option()).isEqualTo("foo");
        assertThat(r.storeKey()).isEqualTo("foo");
      }
      default -> fail(exc);
    }
    switch (exc.getReason()) {
      case InvalidOption r -> {
        assertThat(r.option()).isEqualTo("foo");
      }
      default -> fail(exc);
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat((String)cmd.getOptArg("foo")).isNull();
    assertThat((List<?>)cmd.getOptArgs("foo")).isEmpty();
    assertThat(cmd.getArgs()).isEmpty();
  }
}
