package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class OptionTakesNoArgTest {

  @Test
  void testConstructor() {
    var exc = new OptionTakesNoArg("foo-bar", "fooBar");
    assertThat(exc.option).isEqualTo("foo-bar");
    assertThat(exc.storeKey).isEqualTo("fooBar");
    assertThat(exc.getMessage()).isEqualTo("OptionTakesNoArg{option:foo-bar,storeKey:fooBar}");
    assertThat(exc.getCause()).isNull();
  }

  @Test
  void testBehaviorAsInvalidOption() {
    InvalidOption exc = new OptionTakesNoArg("foo-bar", "fooBar");
    assertThat(exc.option()).isEqualTo("foo-bar");
  }
}
