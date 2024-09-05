package com.github.sttk.cliargs.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.cliargs.exceptions.OptionArgIsInvalid;

@SuppressWarnings("missing-explicit-ctor")
public class BigIntegerValidatorTest {

  @Test
  void testValidate_ok() {
    try {
      new BigIntegerValidator().validate("fooBar", "foo-bar", "9876543210");
    } catch (OptionArgIsInvalid e) {
      fail(e);
    }
  }

  @Test
  void testValidate_fail() {
    try {
      new BigIntegerValidator().validate("fooBar", "foo-bar", "123x");
      fail();
    } catch (OptionArgIsInvalid e) {
      assertThat(e.storeKey).isEqualTo("fooBar");
      assertThat(e.option).isEqualTo("foo-bar");
      assertThat(e.optArg).isEqualTo("123x");
      assertThat(e.details).isEqualTo("invalid BigInteger");
      assertThat(e.getCause()).isInstanceOf(NumberFormatException.class);
      assertThat(e.getMessage()).isEqualTo("OptionArgIsInvalid{" +
        "storeKey:fooBar,option:foo-bar,optArg:123x,details:invalid BigInteger," +
        "cause:java.lang.NumberFormatException: For input string: \"123x\"}");
    }
  }
}
