package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.reasonedexception.ReasonedException;
import com.github.sttk.cliargs.CliArgs.OptionHasInvalidChar;
import com.github.sttk.cliargs.CliArgs.InvalidOption;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ParseTest {

  @Test
  void testParse_zeroArg() {
    var cliargs = new CliArgs("/path/to/app", new String[0]);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isFalse();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneNonOptArg() {
    var cliargs = new CliArgs("path/to/app", new String[]{"abcd"});

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("abcd");

    assertThat(cmd.hasOpt("a")).isFalse();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isFalse();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneLongOpt() {
    var args = new String[]{"--silent"};
    var cliargs = new CliArgs("app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isFalse();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isFalse();
    assertThat(cmd.hasOpt("silent")).isTrue();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneLongOptWithArg() {
    var args = new String[]{"--alphabet=ABC"};
    var cliargs = new CliArgs("path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isFalse();
    assertThat(cmd.hasOpt("alphabet")).isTrue();
    assertThat(cmd.hasOpt("s")).isFalse();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((String)cmd.getOptArg("alphabet")).isEqualTo("ABC");
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    @SuppressWarnings("unchecked")
    var alphabet = (List<String>)cmd.getOptArgs("alphabet");
    assertThat(alphabet).containsExactly("ABC");
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneShortOpt() {
    var args = new String[]{"-s"};
    var cliargs = new CliArgs("path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isFalse();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneShortOptWithArg() {
    var args = new String[]{"-a=123"};
    var cliargs = new CliArgs("path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isFalse();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((String)cmd.getOptArg("a")).isEqualTo("123");
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    @SuppressWarnings("unchecked")
    var a = (List<String>)cmd.getOptArgs("a");
    assertThat(a).containsExactly("123");
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneArgByMultipleShortOpts() {
    var args = new String[]{"-sa"};
    var cliargs = new CliArgs("app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_oneArgByMultipleShortOptsWithArg() {
    var args = new String[]{"-sa=123"};
    var cliargs = new CliArgs("/path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("alphabet")).isFalse();
    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat(cmd.hasOpt("silent")).isFalse();

    assertThat((String)cmd.getOptArg("a")).isEqualTo("123");
    assertThat((Object)cmd.getOptArg("alphabet")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("silent")).isNull();

    @SuppressWarnings("unchecked")
    var a = (List<String>)cmd.getOptArgs("a");
    assertThat(a).containsExactly("123");
    assertThat((List<?>)cmd.getOptArgs("alphabet")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("silent")).isEmpty();
  }

  @Test
  void testParse_longOptNameIncludesHyphenMark() {
    var args = new String[]{"--aaa-bbb-ccc=123"};
    var cliargs = new CliArgs("/path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("aaa-bbb-ccc")).isTrue();
    assertThat((String)cmd.getOptArg("aaa-bbb-ccc")).isEqualTo("123");
    @SuppressWarnings("unchecked")
    var aaaBbbCcc = (List<String>)cmd.getOptArgs("aaa-bbb-ccc");
    assertThat(aaaBbbCcc).containsExactly("123");
  }

  @Test
  void testParse_optsIncludesEqualMark() {
    var args = new String[]{"-sa=b=c"};
    var cliargs = new CliArgs("/path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat((String)cmd.getOptArg("a")).isEqualTo("b=c");

    @SuppressWarnings("unchecked")
    var a = (List<String>)cmd.getOptArgs("a");
    assertThat(a).containsExactly("b=c");
  }

  @Test
  void testParse_optsIncludesMarks() {
    var args = new String[]{"-sa=1,2-3"};
    var cliargs = new CliArgs("path/to/app", args);

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat((String)cmd.getOptArg("a")).isEqualTo("1,2-3");
    @SuppressWarnings("unchecked")
    var a = (List<String>)cmd.getOptArgs("a");
    assertThat(a).containsExactly("1,2-3");
  }

  @Test
  void testParse_illegalLongOptIfIncludingInvalidChar() {
    var args = new String[]{"-s", "--abc%def", "-a"};
    var cliargs = new CliArgs("app", args);

    var result = cliargs.parse();

    assertThat(result.exception().toString()).isEqualTo(
      "com.github.sttk.reasonedexception.ReasonedException: " +
      "OptionHasInvalidChar{option=abc%def}"
    );

    try {
      result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case OptionHasInvalidChar r -> {
          assertThat(r.option()).isEqualTo("abc%def");
        }
        default -> fail(e);
      }
      switch (e.getReason()) {
        case InvalidOption r -> {
          assertThat(r.option()).isEqualTo("abc%def");
        }
        default -> fail(e);
      }
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat(cmd.hasOpt("abc%def")).isFalse();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("abc%def")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("abc%def")).isEmpty();
  }

  @Test
  void testParse_illegalLongOptIfFirstCharIsNumber() {
    var args = new String[]{"--1abc"};
    var cliargs = new CliArgs("app", args);

    var result = cliargs.parse();

    assertThat(result.exception().toString()).isEqualTo(
      "com.github.sttk.reasonedexception.ReasonedException: " +
      "OptionHasInvalidChar{option=1abc}"
    );

    try {
      result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case OptionHasInvalidChar r -> {
          assertThat(r.option()).isEqualTo("1abc");
        }
        default -> fail(e);
      }
      switch (e.getReason()) {
        case InvalidOption r -> {
          assertThat(r.option()).isEqualTo("1abc");
        }
        default -> fail(e);
      }
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("1abc")).isFalse();
    assertThat((Object)cmd.getOptArg("1abc")).isNull();
    assertThat((List<?>)cmd.getOptArgs("1abc")).isEmpty();
  }

  @Test
  void testParse_illegalLongOptIfFirstCharIsHyphen() {
    var args = new String[]{"---aaa=123"};
    var cliargs = new CliArgs("app", args);

    var result = cliargs.parse();

    assertThat(result.exception().toString()).isEqualTo(
      "com.github.sttk.reasonedexception.ReasonedException: " +
      "OptionHasInvalidChar{option=-aaa=123}"
    );

    try {
      result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case OptionHasInvalidChar r -> {
          assertThat(r.option()).isEqualTo("-aaa=123");
        }
        default -> fail(e);
      }
      switch (e.getReason()) {
        case InvalidOption r -> {
          assertThat(r.option()).isEqualTo("-aaa=123");
        }
        default -> fail(e);
      }
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("aaa")).isFalse();
    assertThat((Object)cmd.getOptArg("aaa")).isNull();
    assertThat((List<?>)cmd.getOptArgs("aaa")).isEmpty();
  }

  @Test
  void testParse_IllegalCharInShortOpt() {
    var cliargs = new CliArgs("app", "-s", "--alphabet", "-s@");

    var result = cliargs.parse();

    assertThat(result.exception().toString()).isEqualTo(
      "com.github.sttk.reasonedexception.ReasonedException: " +
      "OptionHasInvalidChar{option=@}"
    );

    try {
      result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case OptionHasInvalidChar r -> {
          assertThat(r.option()).isEqualTo("@");
        }
        default -> fail(e);
      }
      switch (e.getReason()) {
        case InvalidOption r -> {
          assertThat(r.option()).isEqualTo("@");
        }
        default -> fail(e);
      }
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).isEmpty();

    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat(cmd.hasOpt("@")).isFalse();

    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((Object)cmd.getOptArg("@")).isNull();

    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("@")).isEmpty();
  }

  @Test
  void testParse_useEndOptMark() {
    var cliargs = new CliArgs("app", "-s", "--", "-s", "--", "-s@", "xxx");

    var result = cliargs.parse();
    assertThat(result.exception()).isNull();

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("-s", "--", "-s@", "xxx");

    assertThat(cmd.hasOpt("s")).isTrue();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
  }

  @Test
  void testParse_singleHyphen() throws Exception {
    var cliargs = new CliArgs("app", "-");

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();
    
    var cmd = result.cmdOrThrow();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("-");

    assertThat(cmd.hasOpt("s")).isFalse();
    assertThat((Object)cmd.getOptArg("s")).isNull();
    assertThat((List<?>)cmd.getOptArgs("s")).isEmpty();
  }

  @Test
  void testParse_multipleArgs() throws Exception {
    var cliargs = new CliArgs("app", "--foo-bar", "-a", "--baz", "-bc=3",
      "qux", "-c=4", "quux");

    var result = cliargs.parse();

    assertThat(result.exception()).isNull();

    var cmd = result.cmdOrThrow();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("qux", "quux");

    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("b")).isTrue();
    assertThat(cmd.hasOpt("c")).isTrue();
    assertThat(cmd.hasOpt("foo-bar")).isTrue();
    assertThat(cmd.hasOpt("baz")).isTrue();

    assertThat((Object)cmd.getOptArg("a")).isNull();
    assertThat((Object)cmd.getOptArg("b")).isNull();
    assertThat((String)cmd.getOptArg("c")).isEqualTo("3");
    assertThat((Object)cmd.getOptArg("foo-bar")).isNull();
    assertThat((Object)cmd.getOptArg("baz")).isNull();

    assertThat((List<?>)cmd.getOptArgs("a")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("b")).isEmpty();
    @SuppressWarnings("unchecked")
    var c = (List<String>)cmd.getOptArgs("c");
    assertThat(c).containsExactly("3", "4");
    assertThat((List<?>)cmd.getOptArgs("foo-bar")).isEmpty();
    assertThat((List<?>)cmd.getOptArgs("baz")).isEmpty();
  }

  @Test
  void testParse_parseAllArgsEvenIfError() {
    var cliargs = new CliArgs("/path/to/app", "--foo", "--1", "-b2ar", "--3",
      "baz");

    var result = cliargs.parse();

    try {
      result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case OptionHasInvalidChar r -> {
          assertThat(r.option()).isEqualTo("1");
        }
        default -> fail(e);
      }
      switch (e.getReason()) {
        case InvalidOption r -> {
          assertThat(r.option()).isEqualTo("1");
        }
        default -> fail(e);
      }
    }

    var cmd = result.cmd();
    assertThat(cmd.getName()).isEqualTo("app");
    assertThat(cmd.getArgs()).containsExactly("baz");

    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.hasOpt("b")).isTrue();
    assertThat(cmd.hasOpt("a")).isTrue();
    assertThat(cmd.hasOpt("r")).isTrue();
    assertThat(cmd.hasOpt("1")).isFalse();
    assertThat(cmd.hasOpt("2")).isFalse();
    assertThat(cmd.hasOpt("3")).isFalse();
  }
}
