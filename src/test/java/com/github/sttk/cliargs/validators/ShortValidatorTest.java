package com.github.sttk.cliargs.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;

@SuppressWarnings("missing-explicit-ctor")
public class ShortValidatorTest {

  @Test
  void testValidate_ok() {
    try {
      new ShortValidator().validate("fooBar", "foo-bar", "123");
    } catch (OptionArgIsInvalid e) {
      fail(e);
    }
  }

  @Test
  void testValidate_fail() {
    try {
      new ShortValidator().validate("fooBar", "foo-bar", "123x");
      fail();
    } catch (OptionArgIsInvalid e) {
      assertThat(e.storeKey).isEqualTo("fooBar");
      assertThat(e.option).isEqualTo("foo-bar");
      assertThat(e.optArg).isEqualTo("123x");
      assertThat(e.details).isEqualTo("invalid Short");
      assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      assertThat(e.getMessage()).isEqualTo("OptionArgIsInvalid{" +
        "storeKey:fooBar,option:foo-bar,optArg:123x,details:invalid Short," +
        "cause:java.lang.NumberFormatException: For input string: \"123x\"}");
    }
  }
}
