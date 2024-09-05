package com.github.sttk.cliargs.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;

@SuppressWarnings("missing-explicit-ctor")
public class FloatValidatorTest {

  @Test
  void testValidate_ok() {
    try {
      new FloatValidator().validate("fooBar", "foo-bar", "987.6543210");
    } catch (OptionArgIsInvalid e) {
      fail(e);
    }
  }

  @Test
  void testValidate_fail() {
    try {
      new FloatValidator().validate("fooBar", "foo-bar", "12.3x");
      fail();
    } catch (OptionArgIsInvalid e) {
      assertThat(e.storeKey).isEqualTo("fooBar");
      assertThat(e.option).isEqualTo("foo-bar");
      assertThat(e.optArg).isEqualTo("12.3x");
      assertThat(e.details).isEqualTo("invalid Float");
      assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      assertThat(e.getMessage()).isEqualTo("OptionArgIsInvalid{" +
        "storeKey:fooBar,option:foo-bar,optArg:12.3x,details:invalid Float," +
        "cause:java.lang.NumberFormatException: For input string: \"12.3x\"}");
    }
  }
}
