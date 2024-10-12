package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.exceptions.OptionContainsInvalidChar;

@SuppressWarnings("missing-explicit-ctor")
public class ParseTest {

  @Nested
  class TestsOfParse {

    @Test
    void shouldParseZeroArg() {
      var cmd = new Cmd("/path/to/app", new String[]{});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneOptWithNoArg() {
      var cmd = new Cmd("/path/to/app", new String[]{"abcd"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("abcd");
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneLongOpt() {
      var cmd = new Cmd("/path/to/app", new String[]{"--silent"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isTrue();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").get()).hasSize(0);
    }

    @Test
    void shouldParseOneLongOptWithArg() {
      var cmd = new Cmd("/path/to/app", new String[]{"--alphabet=ABC"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isTrue();
      assertThat(cmd.optArg("alphabet").get()).isEqualTo("ABC");
      assertThat(cmd.optArgs("alphabet").get()).containsExactly("ABC");
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneShortOpt() {
      var cmd = new Cmd("/path/to/app", new String[]{"-s"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneShortOneWithArg() {
      var cmd = new Cmd("/path/to/app", new String[]{"-a=123"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").get()).isEqualTo("123");
      assertThat(cmd.optArgs("a").get()).containsExactly("123");
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneArgByMultipleShortOpts() {
      var cmd = new Cmd("/path/to/app", new String[]{"-sa"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").get()).hasSize(0);
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOneArgByMultipleShortOptsWithArg() {
      var cmd = new Cmd("/path/to/app", new String[]{"-sa=123"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").get()).isEqualTo("123");
      assertThat(cmd.optArgs("a").get()).containsExactly("123");
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseLongOptNameIncludingHyphenMarks() {
      var cmd = new Cmd("/path/to/app", new String[]{"--aaa-bbb-ccc=123"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("aaa-bbb-ccc")).isTrue();
      assertThat(cmd.optArg("aaa-bbb-ccc").get()).isEqualTo("123");
      assertThat(cmd.optArgs("aaa-bbb-ccc").get()).containsExactly("123");
    }

    @Test
    void shouldParseOptsAndArgIncludingEqualMarks() {
      var cmd = new Cmd("/path/to/app", new String[]{"-sa=b=c"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").get()).isEqualTo("b=c");
      assertThat(cmd.optArgs("a").get()).containsExactly("b=c");
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseOptsWithArgsIncludingMarks() {
      var cmd = new Cmd("/path/to/app", new String[]{"-sa=1,2-3"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").get()).isEqualTo("1,2-3");
      assertThat(cmd.optArgs("a").get()).containsExactly("1,2-3");
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseButFailBecauseOfIllegalLongOptIncludingInvalidChar() {
      var cmd = new Cmd("path/to/app", new String[]{"-s", "--abc%def", "-a"});
      try {
        cmd.parse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("abc%def");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").get()).hasSize(0);
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseButFailBecauseOfIllegalLongOptOfWhichFirstCharIsNumber() {
      var cmd = new Cmd("/path/to/app", new String[]{"--1abc"});
      try {
        cmd.parse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("1abc");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseButFailBecauseOfIllegalLongOptOfWhichFirstCharIsHyphen() {
      var cmd = new Cmd("/path/to/app", new String[]{"---aaa=123"});
      try {
        cmd.parse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("-aaa=123");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseButFailBecauseOfIllegalCharInShortOpt() {
      var cmd = new Cmd("/path/to/app", new String[]{"-s", "--alphabet", "-a@"});
      try {
        cmd.parse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("@");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").get()).hasSize(0);
      assertThat(cmd.hasOpt("alphabet")).isTrue();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").get()).hasSize(0);
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseWithEndOptMark() {
      var cmd = new Cmd("/path/to/app", new String[]{"-s", "--", "-a", "-s@", "--", "xxx"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("-a", "-s@", "--", "xxx");
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isTrue();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").get()).hasSize(0);
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseSingleHyphen() {
      var cmd = new Cmd("/path/to/app", new String[]{"-"});
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("-");
      assertThat(cmd.hasOpt("a")).isFalse();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").isPresent()).isFalse();
      assertThat(cmd.hasOpt("alphabet")).isFalse();
      assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
      assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
      assertThat(cmd.hasOpt("s")).isFalse();
      assertThat(cmd.optArg("s").isPresent()).isFalse();
      assertThat(cmd.optArgs("s").isPresent()).isFalse();
      assertThat(cmd.hasOpt("silent")).isFalse();
      assertThat(cmd.optArg("silent").isPresent()).isFalse();
      assertThat(cmd.optArgs("silent").isPresent()).isFalse();
    }

    @Test
    void shouldParseMultipleArgs() {
      var cmd = new Cmd("/path/to/app", new String[]{
        "--foo-bar", "-a", "--baz", "-bc=3", "qux", "-c=4", "quux"
      });
      try {
        cmd.parse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("qux", "quux");
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.optArg("a").isPresent()).isFalse();
      assertThat(cmd.optArgs("a").get()).hasSize(0);
      assertThat(cmd.hasOpt("b")).isTrue();
      assertThat(cmd.optArg("b").isPresent()).isFalse();
      assertThat(cmd.optArgs("b").get()).hasSize(0);
      assertThat(cmd.hasOpt("c")).isTrue();
      assertThat(cmd.optArg("c").get()).isEqualTo("3");
      assertThat(cmd.optArgs("c").get()).containsExactly("3", "4");
      assertThat(cmd.hasOpt("foo-bar")).isTrue();
      assertThat(cmd.optArg("foo-bar").isPresent()).isFalse();
      assertThat(cmd.optArgs("foo-bar").get()).hasSize(0);
      assertThat(cmd.hasOpt("baz")).isTrue();
      assertThat(cmd.optArg("baz").isPresent()).isFalse();
      assertThat(cmd.optArgs("baz").get()).hasSize(0);
    }

    @Test
    void shouldParseAllArgsEvenIfFailed() {
      var cmd = new Cmd("/path/to/app", new String[]{"--foo", "--1", "-b2ar", "--3", "baz"});
      try {
        cmd.parse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("1");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).containsExactly("baz");
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.hasOpt("b")).isTrue();
      assertThat(cmd.hasOpt("a")).isTrue();
      assertThat(cmd.hasOpt("r")).isTrue();
      assertThat(cmd.hasOpt("1")).isFalse();
      assertThat(cmd.hasOpt("2")).isFalse();
      assertThat(cmd.hasOpt("3")).isFalse();
    }
  }

  @Nested
  class TestsOfParseUntilSubCmd {

    @Test
    void testIfCommandLineArgumentsContainsNoCommandArgumentAndOption() {
      var osArgs = new String[]{};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var subCmd = cmd.parseUntilSubCmd();
        assertThat(subCmd.isPresent()).isFalse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
    }

    @Test
    void testIfCommandLineArgumentsContainsOnlyCommandArguments() {
      var osArgs = new String[]{"foo", "bar"};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var subCmdOptional = cmd.parseUntilSubCmd();
        assertThat(subCmdOptional.isPresent()).isTrue();
        var subCmd = subCmdOptional.get();
        assertThat(subCmd.name()).isEqualTo("foo");
        assertThat(subCmd.args()).hasSize(0);

        subCmd.parse();
        assertThat(subCmd.name()).isEqualTo("foo");
        assertThat(subCmd.args()).containsExactly("bar");
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void testIfCommandLineArgumentsContainsOnlyCommandOptions() {
      var osArgs = new String[]{"--foo", "-b"};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var subCmdOptional = cmd.parseUntilSubCmd();
        assertThat(subCmdOptional.isPresent()).isFalse();
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.optArg("foo").isPresent()).isFalse();
      assertThat(cmd.hasOpt("b")).isTrue();
      assertThat(cmd.optArg("b").isPresent()).isFalse();
    }

    @Test
    void testIfCommandLineArgumentsContainsBothCommandArgumentsAndOptions() {
      var osArgs = new String[]{"--foo=123", "bar", "--baz", "-q=ABC", "quux"};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var subCmdOptional = cmd.parseUntilSubCmd();
        assertThat(subCmdOptional.isPresent()).isTrue();

        var subCmd = subCmdOptional.get();
        assertThat(subCmd.name()).isEqualTo("bar");
        assertThat(subCmd.args()).hasSize(0);
        assertThat(subCmd.hasOpt("baz")).isFalse();
        assertThat(subCmd.optArg("baz").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("q")).isFalse();
        assertThat(subCmd.optArg("q").isPresent()).isFalse();

        subCmd.parse();
        assertThat(subCmd.name()).isEqualTo("bar");
        assertThat(subCmd.args()).containsExactly("quux");
        assertThat(subCmd.hasOpt("baz")).isTrue();
        assertThat(subCmd.optArg("baz").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("q")).isTrue();
        assertThat(subCmd.optArg("q").get()).isEqualTo("ABC");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("foo")).isTrue();
      assertThat(cmd.optArg("foo").get()).isEqualTo("123");
      assertThat(cmd.optArgs("foo").get()).containsExactly("123");
    }

    @Test
    void testIfFailToParse() {
      var osArgs = new String[]{"--f#o", "bar"};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var optional = cmd.parseUntilSubCmd();
        assertThat(optional.isPresent()).isFalse();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("f#o");
      } catch (Exception e) {
        fail(e);
      }

      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("f#o")).isFalse();
      assertThat(cmd.optArg("f#o").isPresent()).isFalse();
    }

    @Test
    void testIfSubCommandIsLikePath() {
      var osArgs = new String[]{"--foo-bar", "path/to/bar", "--baz", "qux"};
      var cmd = new Cmd("/path/to/app", osArgs);

      try {
        var optional = cmd.parseUntilSubCmd();
        assertThat(optional.isPresent());

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
        assertThat(cmd.hasOpt("foo-bar")).isTrue();

        assertThat(subCmd.name()).isEqualTo("path/to/bar");
        assertThat(subCmd.args()).containsExactly("qux");
        assertThat(subCmd.hasOpt("baz")).isTrue();

      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void shouldParseSingleHyphen() {
      var cmd = new Cmd("/path/to/app", new String[]{"-a", "-", "b", "-"});
      try {
        var optional = cmd.parseUntilSubCmd();
        assertThat(optional.isPresent());

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
        assertThat(cmd.hasOpt("a")).isTrue();

        assertThat(subCmd.name()).isEqualTo("-");
        assertThat(subCmd.args()).containsExactly("b", "-");
        assertThat(subCmd.hasOpt("a")).isFalse();
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void shouldParseSingleHyphenButError() {
      var cmd = new Cmd("/path/to/app", new String[]{"-a", "-@", "-", "b", "-"});
      try {
        cmd.parseUntilSubCmd();
        fail();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("@");
      } catch (Exception e) {
        fail(e);
      }
      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
      assertThat(cmd.hasOpt("a")).isTrue();
    }

    @Test
    void shouldParseWithEndOptMark() {
      var cmd = new Cmd("/path/to/app", new String[]{"sub", "--", "-a", "-s@", "--", "xxx"});
      try {
        var optional = cmd.parseUntilSubCmd();
        assertThat(optional.isPresent());

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
        assertThat(cmd.hasOpt("a")).isFalse();
        assertThat(cmd.optArg("a").isPresent()).isFalse();
        assertThat(cmd.optArgs("a").isPresent()).isFalse();
        assertThat(cmd.hasOpt("alphabet")).isFalse();
        assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
        assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
        assertThat(cmd.hasOpt("s")).isFalse();
        assertThat(cmd.optArg("s").isPresent()).isFalse();
        assertThat(cmd.optArgs("s").isPresent()).isFalse();
        assertThat(cmd.hasOpt("silent")).isFalse();
        assertThat(cmd.optArg("silent").isPresent()).isFalse();
        assertThat(cmd.optArgs("silent").isPresent()).isFalse();

        assertThat(subCmd.name()).isEqualTo("sub");
        assertThat(subCmd.args()).containsExactly("-a", "-s@", "--", "xxx");
        assertThat(subCmd.hasOpt("a")).isFalse();
        assertThat(subCmd.optArg("a").isPresent()).isFalse();
        assertThat(subCmd.optArgs("a").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("alphabet")).isFalse();
        assertThat(subCmd.optArg("alphabet").isPresent()).isFalse();
        assertThat(subCmd.optArgs("alphabet").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("s")).isFalse();
        assertThat(subCmd.optArg("s").isPresent()).isFalse();
        assertThat(subCmd.optArgs("s").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("silent")).isFalse();
        assertThat(subCmd.optArg("silent").isPresent()).isFalse();
        assertThat(subCmd.optArgs("silent").isPresent()).isFalse();
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void shouldParseAfterEndOptMark() {
      var cmd = new Cmd("/path/to/app", new String[]{"-s", "--", "-a", "-s@", "--", "xxx"});
      try {
        var optional = cmd.parseUntilSubCmd();
        assertThat(optional.isPresent());

        var subCmd = optional.get();
        subCmd.parse();

        assertThat(cmd.name()).isEqualTo("app");
        assertThat(cmd.args()).hasSize(0);
        assertThat(cmd.hasOpt("a")).isFalse();
        assertThat(cmd.optArg("a").isPresent()).isFalse();
        assertThat(cmd.optArgs("a").isPresent()).isFalse();
        assertThat(cmd.hasOpt("alphabet")).isFalse();
        assertThat(cmd.optArg("alphabet").isPresent()).isFalse();
        assertThat(cmd.optArgs("alphabet").isPresent()).isFalse();
        assertThat(cmd.hasOpt("s")).isTrue();
        assertThat(cmd.optArg("s").isPresent()).isFalse();
        assertThat(cmd.optArgs("s").get()).hasSize(0);
        assertThat(cmd.hasOpt("silent")).isFalse();
        assertThat(cmd.optArg("silent").isPresent()).isFalse();
        assertThat(cmd.optArgs("silent").isPresent()).isFalse();

        assertThat(subCmd.name()).isEqualTo("-a");
        assertThat(subCmd.args()).containsExactly("-s@", "--", "xxx");
        assertThat(subCmd.hasOpt("a")).isFalse();
        assertThat(subCmd.optArg("a").isPresent()).isFalse();
        assertThat(subCmd.optArgs("a").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("alphabet")).isFalse();
        assertThat(subCmd.optArg("alphabet").isPresent()).isFalse();
        assertThat(subCmd.optArgs("alphabet").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("s")).isFalse();
        assertThat(subCmd.optArg("s").isPresent()).isFalse();
        assertThat(subCmd.optArgs("s").isPresent()).isFalse();
        assertThat(subCmd.hasOpt("silent")).isFalse();
        assertThat(subCmd.optArg("silent").isPresent()).isFalse();
        assertThat(subCmd.optArgs("silent").isPresent()).isFalse();
      } catch (Exception e) {
        fail(e);
      }
    }

    @Test
    void shouldParseAfterEndOptMarkButError() {
      var cmd = new Cmd("/path/to/app", new String[]{"-@", "--", "-a", "-s@", "--", "xxx"});
      try {
        cmd.parseUntilSubCmd();
        fail();
      } catch (OptionContainsInvalidChar e) {
        assertThat(e.option()).isEqualTo("@");
      } catch (Exception e) {
        fail(e);
      }
      assertThat(cmd.name()).isEqualTo("app");
      assertThat(cmd.args()).hasSize(0);
    }
  }
}
