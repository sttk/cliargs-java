package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import com.github.sttk.cliargs.exceptions.InvalidOption;
import java.util.Optional;

@SuppressWarnings("missing-explicit-ctor")
public class CmdTest {

  @Test
  void should_create_a_new_instance() {
    var cmd = new Cmd("/path/to/app");
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");

    try {
      cmd.parse();
    } catch (InvalidOption e) {
      fail(e);
    }
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");
  }

  @Test
  void should_get_command_arguments() {
    String[] osArgs = {"--foo", "--bar=baz", "--bar=qux", "quux", "corge"};
    var cmd = new Cmd("/path/to/app", osArgs);
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");
    assertThat(cmd.args()).isEmpty();

    try {
      cmd.args().add("xxx");
      fail();
    } catch (UnsupportedOperationException e) {}

    try {
      cmd.parse();
    } catch (InvalidOption e) {
      fail(e);
    }
    assertThat(cmd.toString())
      .isEqualTo("Cmd{name=app, args=[quux, corge], opts={bar=[baz, qux], foo=[]}}");
    assertThat(cmd.args()).containsExactly("quux", "corge");

    try {
      cmd.args().add("xxx");
      fail();
    } catch (UnsupportedOperationException e) {}
  }

  @Test
  void should_check_option_is_specified() {
    String[] osArgs = {"--foo", "--bar=baz", "--bar=qux", "quux", "corge"};
    var cmd = new Cmd("/path/to/app", osArgs);
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");
    assertThat(cmd.hasOpt("foo")).isFalse();
    assertThat(cmd.hasOpt("bar")).isFalse();
    assertThat(cmd.hasOpt("baz")).isFalse();

    try {
      cmd.parse();
    } catch (InvalidOption e) {
      fail(e);
    }
    assertThat(cmd.args()).containsExactly("quux", "corge");
    assertThat(cmd.toString())
      .isEqualTo("Cmd{name=app, args=[quux, corge], opts={bar=[baz, qux], foo=[]}}");
    assertThat(cmd.hasOpt("foo")).isTrue();
    assertThat(cmd.hasOpt("bar")).isTrue();
    assertThat(cmd.hasOpt("baz")).isFalse();
  }

  @Test
  void should_get_single_option_argument() {
    String[] osArgs = {"--foo", "--bar=baz", "--bar=qux", "quux", "corge"};
    var cmd = new Cmd("/path/to/app", osArgs);
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");
    assertThat(cmd.optArg("foo")).isEqualTo(Optional.empty());
    assertThat(cmd.optArg("bar")).isEqualTo(Optional.empty());
    assertThat(cmd.optArg("baz")).isEqualTo(Optional.empty());

    try {
      cmd.parse();
    } catch (InvalidOption e) {
      fail(e);
    }
    assertThat(cmd.args()).containsExactly("quux", "corge");
    assertThat(cmd.toString())
      .isEqualTo("Cmd{name=app, args=[quux, corge], opts={bar=[baz, qux], foo=[]}}");
    assertThat(cmd.optArg("foo")).isEqualTo(Optional.empty());
    assertThat(cmd.optArg("bar")).isEqualTo(Optional.of("baz"));
    assertThat(cmd.optArg("baz")).isEqualTo(Optional.empty());
  }

  @Test
  void should_get_multiple_option_arguments() {
    String[] osArgs = {"--foo", "--bar=baz", "--bar=qux", "quux", "corge"};
    var cmd = new Cmd("/path/to/app", osArgs);
    assertThat(cmd.name()).isEqualTo("app");
    assertThat(cmd.toString()).isEqualTo("Cmd{name=app, args=[], opts={}}");
    assertThat(cmd.optArgs("foo")).isEqualTo(Optional.empty());
    assertThat(cmd.optArgs("bar")).isEqualTo(Optional.empty());
    assertThat(cmd.optArgs("baz")).isEqualTo(Optional.empty());

    try {
      cmd.parse();
    } catch (InvalidOption e) {
      fail(e);
    }
    assertThat(cmd.args()).containsExactly("quux", "corge");
    assertThat(cmd.toString())
      .isEqualTo("Cmd{name=app, args=[quux, corge], opts={bar=[baz, qux], foo=[]}}");
    assertThat(cmd.optArgs("foo").get()).isEmpty();
    assertThat(cmd.optArgs("bar").get()).containsExactly("baz", "qux");
    assertThat(cmd.optArgs("baz")).isEqualTo(Optional.empty());

    try {
      cmd.optArgs("bar").get().add("xxx");
      fail();
    } catch (UnsupportedOperationException e) {}
  }
}
