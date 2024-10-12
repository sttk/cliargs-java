package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import static com.github.sttk.cliargs.OptCfg.Param.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.exceptions.UnconfiguredOption;
import com.github.sttk.cliargs.exceptions.OptionNeedsArg;
import com.github.sttk.cliargs.exceptions.OptionTakesNoArg;
import com.github.sttk.cliargs.exceptions.ConfigIsArrayButHasNoArg;
import com.github.sttk.cliargs.exceptions.OptionNeedsArg;
import com.github.sttk.cliargs.exceptions.OptionIsNotArray;
import com.github.sttk.cliargs.exceptions.ConfigHasDefaultsButHasNoArg;
import com.github.sttk.cliargs.exceptions.OptionNameIsDuplicated;
import com.github.sttk.cliargs.exceptions.StoreKeyIsDuplicated;

@SuppressWarnings("missing-explicit-ctor")
public class ParseWithTest {

  @Nested
  class TestsOfParseWith {
    @Test
    void zeroCfgAndZeroArg() {
      var optCfgs = new OptCfg[0];

      var cmd = new Cmd("app", new String[]{});
      try {
        cmd.parseWith(optCfgs);

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
        assertThat(cmd.hasOpt("foo-bar")).isFalse();
        assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
        assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

        assertThat(cmd.optCfgs()).hasSize(0);
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void zeroCfgAndOneCommandArg() {
      var optCfgs = new OptCfg[0];

      var cmd = new Cmd("app", new String[]{"foo-bar"});

      try {
        cmd.parseWith(optCfgs);

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).containsExactly("foo-bar");
        assertThat(cmd.hasOpt("foo-bar")).isFalse();
        assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
        assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

        assertThat(cmd.optCfgs()).hasSize(0);
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void zeroCfgAndOneLongOpt() {
      var optCfgs = new OptCfg[0];

      var cmd = new Cmd("app", new String[]{"--foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(0);
    }

    @Test
    void zeroCfgAndOneShortOpt() {
      var optCfgs = new OptCfg[0];

      var cmd = new Cmd("app", new String[]{"-f"});

      try {
        cmd.parseWith(optCfgs);
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("f");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(0);
    }

    @Test
    void oneCfgAndZeroOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"))
      };

      var cmd = new Cmd("path/to/app", new String[0]);

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgAndOneCmdArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("foo-bar");
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgAndOneLongOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"--foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").get()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgAndOneShortOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"-f"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgAndOneDifferentLongOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"--bar-foo"});

      try {
        cmd.parseWith(optCfgs);
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("bar-foo");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.hasOpt("bar-foo")).isFalse();
      assertThat(cmd.optArg("bar-foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("bar-foo").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgAndOneDifferentShortOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"-b"});

      try {
        cmd.parseWith(optCfgs);
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("b");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.hasOpt("bar-foo")).isFalse();
      assertThat(cmd.optArg("bar-foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("bar-foo").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void anyOptCfgAndOneDifferentLongOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar")),
        new OptCfg(names("*"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"--bar-foo", "--baaz=123"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.hasOpt("bar-foo")).isTrue();
      assertThat(cmd.optArg("bar-foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("bar-foo").get()).hasSize(0);
      assertThat(cmd.hasOpt("baaz")).isTrue();
      assertThat(cmd.optArg("baaz").get()).isEqualTo("123");
      assertThat(cmd.optArgs("baaz").get()).containsExactly("123");

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("*");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void anyOptCfgAndOneDifferentShortOpt() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f")),
        new OptCfg(names("*"))
      };

      var cmd = new Cmd("path/to/app", new String[]{"-b", "-c=123"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.hasOpt("b")).isTrue();
      assertThat(cmd.optArg("b").isPresent()).isFalse();
      assertThat(cmd.optArgs("b").get()).hasSize(0);
      assertThat(cmd.hasOpt("c")).isTrue();
      assertThat(cmd.optArg("c").get()).isEqualTo("123");
      assertThat(cmd.optArgs("c").get()).containsExactly("123");

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("*");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresArgAndOneLongOptHasArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar", "ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("foo-bar").get()).containsExactly("ABC");

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresArgAndOneShortOptHasArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f"), hasArg(true)),
      };

      var cmd = new Cmd("app", new String[]{"-f", "ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("f").get()).containsExactly("ABC");

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresArgButOneLongOptHasNoArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionNeedsArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresArgButOneShortOptHasNoArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f"), hasArg(true)),
      };

      var cmd = new Cmd("app", new String[]{"-f"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionNeedsArg e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("f");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresNoArgButOneLongOptHasArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar")),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar", "ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("ABC");
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"--foo-bar=ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"--foo-bar", ""});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("");
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"--foo-bar="});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresNoArgButOneShortOptHasArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("f")),
      };

      var cmd = new Cmd("app", new String[]{"-f", "ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("ABC");
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"-f=ABC"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("f");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"-f", ""});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("");
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      cmd = new Cmd("app", new String[]{"-f="});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("f");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresNoArgButIsArray() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(false), isArray(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (ConfigIsArrayButHasNoArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgIsArrayAndOptHasNoArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true), isArray(true)),
        new OptCfg(names("f"), hasArg(true), isArray(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionNeedsArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(1).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(1).isArray).isTrue();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgIsArrayAndOptHasOneArg() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true), isArray(true)),
        new OptCfg(names("f"), hasArg(true), isArray(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar", "ABC", "-f", "DEF"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("foo-bar").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").get()).isEqualTo("DEF");
      assertThat(cmd.optArgs("f").get()).containsExactly("DEF");

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(1).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(1).isArray).isTrue();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgIsArrayAndOptHasMultipleArgs() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true), isArray(true)),
        new OptCfg(names("f"), hasArg(true), isArray(true)),
      };

      var cmd = new Cmd("app", new String[]{
        "--foo-bar", "ABC", "-f", "DEF", "--foo-bar", "GHI", "-f", "JKL",
      });

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("foo-bar").get()).containsExactly("ABC", "GHI");
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").get()).isEqualTo("DEF");
      assertThat(cmd.optArgs("f").get()).containsExactly("DEF", "JKL");

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("f");
      assertThat(cmd.optCfgs().get(1).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(1).isArray).isTrue();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgHasNameAndAliasAndArgMatchesThem() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar", "f"), hasArg(true), isArray(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar", "ABC", "-f", "DEF"});

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("foo-bar").get()).containsExactly("ABC", "DEF");
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar", "f");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgIsNotArrayButOptsAreMultiple() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo-bar"), hasArg(true)),
        new OptCfg(names("f"), hasArg(true)),
      };

      var cmd = new Cmd("app", new String[]{"--foo-bar=ABC", "--foo-bar", "DEF"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionIsNotArray e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("foo-bar").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      cmd = new Cmd("app", new String[]{"-f=ABC", "-f", "DEF"});

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionIsNotArray e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("f");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isTrue();
      assertThat(cmd.optArg("f").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("f").get()).containsExactly("ABC");
    }

    @Test
    void specifyDefaults() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("bar"), hasArg(true), defaults("A")),
        new OptCfg(names("baz"), hasArg(true), isArray(true), defaults("B")),
      };

      var cmd = new Cmd("app", new String[0]);

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isFalse();
      assertThat(cmd.optArg("foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo").isPresent()).isFalse();
      assertThat(cmd.hasOpt("bar")).isTrue();
      assertThat(cmd.optArg("bar").get()).isEqualTo("A");
      assertThat(cmd.optArgs("bar").get()).containsExactly("A");
      assertThat(cmd.hasOpt("baz")).isTrue();
      assertThat(cmd.optArg("baz").get()).isEqualTo("B");
      assertThat(cmd.optArgs("baz").get()).containsExactly("B");

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.get()).containsExactly("A");
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("baz");
      assertThat(cmd.optCfgs().get(1).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(1).isArray).isTrue();
      assertThat(cmd.optCfgs().get(1).defaults.get()).containsExactly("B");
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void oneCfgRequiresNoArgButHasDefaults() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names("foo-bar"), defaults("A"))
      };

      var cmd = new Cmd("app", new String[0]);

      try {
        cmd.parseWith(optCfgs);
      } catch (ConfigHasDefaultsButHasNoArg e) {
        assertThat(e.option()).isEqualTo("foo-bar");
        assertThat(e.name).isEqualTo("foo-bar");
        assertThat(e.storeKey).isEqualTo("foo-bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.get()).containsExactly("A");
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void multipleArgs() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names("foo-bar")),
        new OptCfg(names("baz", "z"), hasArg(true), isArray(true)),
        new OptCfg(names("corge"), hasArg(true), defaults("99")),
        new OptCfg(names("*"))
      };

      var cmd = new Cmd("app", "--foo-bar", "qux", "--baz", "1", "-z=Z", "-X", "quux");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("qux", "quux");
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").get()).hasSize(0);
      assertThat(cmd.hasOpt("baz")).isTrue();
      assertThat(cmd.optArg("baz").get()).isEqualTo("1");
      assertThat(cmd.optArgs("baz").get()).containsExactly("1", "Z");
      assertThat(cmd.hasOpt("X")).isTrue();
      assertThat(cmd.optArg("X").isPresent()).isFalse();
      assertThat(cmd.optArgs("X").get()).hasSize(0);
      assertThat(cmd.hasOpt("corge")).isTrue();
      assertThat(cmd.optArg("corge").get()).isEqualTo("99");
      assertThat(cmd.optArgs("corge").get()).containsExactly("99");

      assertThat(cmd.optCfgs()).hasSize(4);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo-bar");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("baz", "z");
      assertThat(cmd.optCfgs().get(1).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(1).isArray).isTrue();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(2)).isEqualTo(optCfgs[2]);
      assertThat(cmd.optCfgs().get(2).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(2).names).containsExactly("corge");
      assertThat(cmd.optCfgs().get(2).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(2).isArray).isFalse();
      assertThat(cmd.optCfgs().get(2).defaults.get()).containsExactly("99");
      assertThat(cmd.optCfgs().get(2).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(2).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(3)).isEqualTo(optCfgs[3]);
      assertThat(cmd.optCfgs().get(3).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(3).names).containsExactly("*");
      assertThat(cmd.optCfgs().get(3).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(3).isArray).isFalse();
      assertThat(cmd.optCfgs().get(3).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(3).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(3).argInHelp).isEqualTo("");
    }

    @Test
    void parseAllArgsEvenIfError() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("foo", "f"))
      };

      var cmd = new Cmd("app", "-ef", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("e");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("bar");
      assertThat(cmd.hasOpt("e")).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.optArg("foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo", "f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void parseAllArgsEvenIfShortOptionValueIsError() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("e")),
        new OptCfg(names("foo", "f"))
      };

      var cmd = new Cmd("app", "-ef=123", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("foo");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("bar");
      assertThat(cmd.hasOpt("e")).isTrue();
      assertThat(cmd.optArg("e").isPresent()).isFalse();
      assertThat(cmd.optArgs("e").get()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.hasOpt("foo")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("e");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("foo", "f");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void parseAllArgsEvenIfLongOptionValueIsError() {
      var optCfgs = new OptCfg[] {
        new OptCfg(names("e")),
        new OptCfg(names("foo", "f"))
      };

      var cmd = new Cmd("app", "--foo=123", "-e", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionTakesNoArg e) {
        assertThat(e.option()).isEqualTo("foo");
        assertThat(e.storeKey).isEqualTo("foo");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("bar");
      assertThat(cmd.hasOpt("e")).isTrue();
      assertThat(cmd.optArg("e").isPresent()).isFalse();
      assertThat(cmd.optArgs("e").get()).hasSize(0);
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.hasOpt("foo")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0)).isEqualTo(optCfgs[0]);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("e");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1)).isEqualTo(optCfgs[1]);
      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("foo", "f");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void ignoreCfgIfNamesIsEmpty() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names()),
        new OptCfg(names("foo"))
      };

      var cmd = new Cmd("app", "--foo", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("bar");
      assertThat(cmd.hasOpt("foo")).isTrue();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).hasSize(0);
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("foo");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void optionNameIsDuplicated() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names("foo", "f")),
        new OptCfg(names("bar", "f")),
      };

      var cmd = new Cmd("app", "--foo", "--bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (OptionNameIsDuplicated e) {
        assertThat(e.option()).isEqualTo("f");
        assertThat(e.name).isEqualTo("f");
        assertThat(e.storeKey).isEqualTo("bar");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isFalse();
      assertThat(cmd.hasOpt("bar")).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo", "f");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("bar", "f");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void useStoreKey() {
      var optCfgs = new OptCfg[]{
        new OptCfg(storeKey("fooBar"), names("f", "foo"))
      };

      var cmd = new Cmd("app", "--foo", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("bar");
      assertThat(cmd.hasOpt("fooBar")).isTrue();
      assertThat(cmd.optArg("fooBar").isPresent()).isFalse();
      assertThat(cmd.optArgs("fooBar").get()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("fooBar");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f", "foo");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void useStoreKeyAsOptionNameIfNamesIssEmpty() {
      var optCfgs = new OptCfg[]{
        new OptCfg(storeKey("fooBar"))
      };

      var cmd = new Cmd("app", "--fooBar");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("fooBar")).isTrue();
      assertThat(cmd.optArg("fooBar").isPresent()).isFalse();
      assertThat(cmd.optArgs("fooBar").get()).hasSize(0);

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("fooBar");
      assertThat(cmd.optCfgs().get(0).names).hasSize(0);
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void storeKeyIsDuplicated() {
      var optCfgs = new OptCfg[]{
        new OptCfg(storeKey("fooBar"), names("f", "foo")),
        new OptCfg(storeKey("fooBar"), names("b", "bar"))
      };

      var cmd = new Cmd("app", "--foo", "bar");

      try {
        cmd.parseWith(optCfgs);
      } catch (StoreKeyIsDuplicated e) {
        assertThat(e.storeKey).isEqualTo("fooBar");
        assertThat(e.name).isEqualTo("b");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("fooBar")).isFalse();
      assertThat(cmd.hasOpt("foo")).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.hasOpt("bar")).isFalse();
      assertThat(cmd.hasOpt("b")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("fooBar");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("f", "foo");
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("fooBar");
      assertThat(cmd.optCfgs().get(1).names).containsExactly("b", "bar");
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }

    @Test
    void acceptAllOptionsIfStoreKeyIsAsterisk() {
      var optCfgs = new OptCfg[]{
        new OptCfg(storeKey("*")),
      };

      var cmd = new Cmd("app", "--foo", "--bar", "baz");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("baz");
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.hasOpt("bar")).isTrue();

      assertThat(cmd.optCfgs()).hasSize(1);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("*");
      assertThat(cmd.optCfgs().get(0).names).hasSize(0);
      assertThat(cmd.optCfgs().get(0).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(0).isArray).isFalse();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");
    }

    @Test
    void acceptUnconfiguredOptionEvenIfItMatchesStoreKey() {
      var optCfgs = new OptCfg[]{
        new OptCfg(storeKey("Bar"), names("foo", "f"), hasArg(true), isArray(true)),
        new OptCfg(storeKey("*")),
      };

      var cmd = new Cmd("app", "--foo", "1", "-f=2", "--Bar=3");

      try {
        cmd.parseWith(optCfgs);
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("Bar")).isTrue();
      assertThat(cmd.optArg("Bar").get()).isEqualTo("1");
      assertThat(cmd.optArgs("Bar").get()).containsExactly("1", "2", "3");
      assertThat(cmd.hasOpt("foo")).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();

      assertThat(cmd.optCfgs()).hasSize(2);
      assertThat(cmd.optCfgs().get(0).storeKey).isEqualTo("Bar");
      assertThat(cmd.optCfgs().get(0).names).containsExactly("foo", "f");
      assertThat(cmd.optCfgs().get(0).hasArg).isTrue();
      assertThat(cmd.optCfgs().get(0).isArray).isTrue();
      assertThat(cmd.optCfgs().get(0).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(0).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(0).argInHelp).isEqualTo("");

      assertThat(cmd.optCfgs().get(1).storeKey).isEqualTo("*");
      assertThat(cmd.optCfgs().get(1).names).hasSize(0);
      assertThat(cmd.optCfgs().get(1).hasArg).isFalse();
      assertThat(cmd.optCfgs().get(1).isArray).isFalse();
      assertThat(cmd.optCfgs().get(1).defaults.isPresent()).isFalse();
      assertThat(cmd.optCfgs().get(1).desc).isEqualTo("");
      assertThat(cmd.optCfgs().get(1).argInHelp).isEqualTo("");
    }
  }

  @Nested
  class TestsOfParseUntilSubCmdWith {
    @Test
    void getSubCmd() {
      var optCfgs1 = new OptCfg[]{ new OptCfg(names("foo", "f")) };
      var optCfgs2 = new OptCfg[]{ new OptCfg(names("bar", "b")) };

      var cmd = new Cmd("app", "--foo", "sub", "--bar");

      try {
        var optional = cmd.parseUntilSubCmdWith(optCfgs1);
        assertThat(optional.isPresent()).isTrue();

        var subCmd = optional.get();

        assertThat(subCmd.name()).isEqualTo("sub");
        assertThat(subCmd.args()).hasSize(0);
        assertThat(subCmd.hasOpt("bar")).isFalse();
        assertThat(subCmd.optArg("bar").isPresent()).isFalse();
        assertThat(subCmd.optArgs("bar").isPresent()).isFalse();

        subCmd.parseWith(optCfgs2);

        assertThat(subCmd.name()).isEqualTo("sub");
        assertThat(subCmd.args()).hasSize(0);
        assertThat(subCmd.hasOpt("bar")).isTrue();
        assertThat(subCmd.optArg("bar").isPresent()).isFalse();
        assertThat(subCmd.optArgs("bar").get()).hasSize(0);

        assertThat(subCmd.optCfgs()).containsExactly(optCfgs2);

      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.optArg("foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo").get()).hasSize(0);

      assertThat(cmd.optCfgs()).containsExactly(optCfgs1);
    }

    @Test
    void noSubCmd() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names("foo", "f"))
      };

      var cmd = new Cmd("app", "--foo");

      try {
        var optional = cmd.parseUntilSubCmdWith(optCfgs);
        assertThat(optional.isPresent()).isFalse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.optArg("foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo").get()).hasSize(0);

      assertThat(cmd.optCfgs()).containsExactly(optCfgs);
    }

    @Test
    void failToParse() {
      var optCfgs = new OptCfg[]{
        new OptCfg(names("foo-bar"))
      };

      var cmd = new Cmd("path/to/app", "--bar-foo");

      try {
        var optional = cmd.parseUntilSubCmdWith(optCfgs);
        assertThat(optional.isPresent()).isFalse();
      } catch (UnconfiguredOption e) {
        assertThat(e.option()).isEqualTo("bar-foo");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo-bar")).isFalse();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").isPresent()).isFalse();
      assertThat(cmd.hasOpt("f")).isFalse();
      assertThat(cmd.optArg("f").isPresent()).isFalse();
      assertThat(cmd.optArgs("f").isPresent()).isFalse();

      assertThat(cmd.hasOpt("bar-foo")).isFalse();
      assertThat(cmd.optArg("bar-foo").isPresent()).isFalse();
      assertThat(cmd.optArgs("bar-foo").isPresent()).isFalse();

      assertThat(cmd.optCfgs()).containsExactly(optCfgs);
    }
  }
}
