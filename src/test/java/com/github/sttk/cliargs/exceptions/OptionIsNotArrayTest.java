package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class OptionIsNotArrayTest {

  @Test
  void testConstructor() {
    var exc = new OptionIsNotArray("foo-bar", "fooBar");
    assertThat(exc.option).isEqualTo("foo-bar");
    assertThat(exc.storeKey).isEqualTo("fooBar");
    assertThat(exc.getMessage()).isEqualTo("OptionIsNotArray{option:foo-bar,storeKey:fooBar}");
    assertThat(exc.getCause()).isNull();
  }

  @Test
  void testBehaviorAsInvalidOption() {
    InvalidOption exc = new OptionIsNotArray("foo-bar", "fooBar");
    assertThat(exc.option()).isEqualTo("foo-bar");
  }
}
