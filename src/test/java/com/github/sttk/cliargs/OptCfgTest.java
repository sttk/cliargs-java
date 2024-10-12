package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import static com.github.sttk.cliargs.OptCfg.Param.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import com.github.sttk.cliargs.validators.IntegerValidator;
import com.github.sttk.cliargs.validators.LongValidator;
import java.util.List;

@SuppressWarnings("missing-explicit-ctor")
public class OptCfgTest {

  @Test
  void testConstructor_withNoValidator() {
    var cfg = new OptCfg("fooBar", List.of("foo-bar", "f"), true, true, List.of("123", "456"),
      "description of the option", "<num>", null);
    assertThat(cfg.storeKey).isEqualTo("fooBar");
    assertThat(cfg.names).containsExactly("foo-bar", "f");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.defaults.get()).containsExactly("123", "456");
    assertThat(cfg.desc).isEqualTo("description of the option");
    assertThat(cfg.argInHelp).isEqualTo("<num>");
    assertThat(cfg.validator).isNull();
  }

  @Test
  void testConstructor_withValidator() {
    var cfg = new OptCfg("fooBar", List.of("foo-bar", "f"), true, true, List.of("123", "456"),
      "description of the option", "<num>", new IntegerValidator());
    assertThat(cfg.storeKey).isEqualTo("fooBar");
    assertThat(cfg.names).containsExactly("foo-bar", "f");
    assertThat(cfg.hasArg).isTrue();
    assertThat(cfg.isArray).isTrue();
    assertThat(cfg.defaults.get()).containsExactly("123", "456");
    assertThat(cfg.desc).isEqualTo("description of the option");
    assertThat(cfg.argInHelp).isEqualTo("<num>");
    assertThat(cfg.validator).isNotNull();
  }

  @Test
  void testConstructor_allNullish() {
    var cfg = new OptCfg(null, null, false, false, null, null, null, null);
    assertThat(cfg.storeKey).isEqualTo("");
    assertThat(cfg.names).hasSize(0);
    assertThat(cfg.hasArg).isFalse();
    assertThat(cfg.isArray).isFalse();
    assertThat(cfg.defaults.isPresent()).isFalse();
    assertThat(cfg.desc).isEqualTo("");
    assertThat(cfg.argInHelp).isEqualTo("");
    assertThat(cfg.validator).isNull();
  }

  @Nested
  class TestNamedParam {
    @Test
    void testOfStoreKey() {
      var cfg = new OptCfg(storeKey("fooBar"));
      assertThat(cfg.storeKey).isEqualTo("fooBar");
      assertThat(cfg.names).isEmpty();
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfNames() {
      var cfg = new OptCfg(names("foo-bar", "f"));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).containsExactly("foo-bar", "f");
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfNameList() {
      var cfg = new OptCfg(names(List.of("foo-bar", "f")));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).containsExactly("foo-bar", "f");
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();

      cfg = new OptCfg(names((List<String>) null));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).isEmpty();
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfHasArg() {
      var cfg = new OptCfg(hasArg(true));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isTrue();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfIsArray() {
      var cfg = new OptCfg(isArray(true));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isTrue();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfDefaults() {
      var cfg = new OptCfg(defaults("123", "456"));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.get()).containsExactly("123", "456");
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfDefaultList() {
      var cfg = new OptCfg(defaults(List.of("123", "456")));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.get()).containsExactly("123", "456");
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();

      cfg = new OptCfg(defaults((List<String>)null));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfDesc() {
      var cfg = new OptCfg(desc("xxx"));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("xxx");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfArgInHelp() {
      var cfg = new OptCfg(argInHelp("xxx"));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("xxx");
      assertThat(cfg.validator).isNull();
    }

    @Test
    void testOfAValidator() {
      var cfg = new OptCfg(validator(new LongValidator()));
      assertThat(cfg.storeKey).isEqualTo("");
      assertThat(cfg.names).hasSize(0);
      assertThat(cfg.hasArg).isFalse();
      assertThat(cfg.isArray).isFalse();
      assertThat(cfg.defaults.isPresent()).isFalse();
      assertThat(cfg.desc).isEqualTo("");
      assertThat(cfg.argInHelp).isEqualTo("");
      assertThat(cfg.validator).isInstanceOf(LongValidator.class);
    }
  }

  @Test
  void testToString() {
    var cfg = new OptCfg("fooBar", List.of("foo-bar", "f"), true, true, List.of("123", "456"),
      "description of the option", "<num>", null);

    assertThat(cfg.toString()).isEqualTo("OptCfg{storeKey=fooBar, names=[foo-bar, f], hasArg=true, isArray=true, defaults=Optional[[123, 456]], desc=description of the option, argInHelp=<num>}");
  }
}
