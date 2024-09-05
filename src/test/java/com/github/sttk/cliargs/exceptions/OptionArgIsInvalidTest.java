package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class OptionArgIsInvalidTest {

  @Test
  void testConstructor() {
    var cause = new NumberFormatException();
    var exc = new OptionArgIsInvalid("fooBar", "foo-bar", "x23", "invalid number", cause);
    assertThat(exc.storeKey).isEqualTo("fooBar");
    assertThat(exc.option).isEqualTo("foo-bar");
    assertThat(exc.optArg).isEqualTo("x23");
    assertThat(exc.details).isEqualTo("invalid number");
    assertThat(exc.getMessage()).isEqualTo(
      "OptionArgIsInvalid{storeKey:fooBar,option:foo-bar,optArg:x23,details:invalid number," +
      "cause:java.lang.NumberFormatException}");
    assertThat(exc.getCause()).isEqualTo(cause);
  }

  @Test
  void testBehaviorAsInvalidOption() {
    InvalidOption exc = new OptionArgIsInvalid("fooBar", "foo-bar", "x", "invalid number", null);
    assertThat(exc.option()).isEqualTo("foo-bar");
  }
}
