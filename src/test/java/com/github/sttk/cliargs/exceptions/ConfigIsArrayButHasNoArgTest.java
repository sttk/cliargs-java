package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class ConfigIsArrayButHasNoArgTest {

  @Test
  void testConstructor() {
    var exc = new ConfigIsArrayButHasNoArg("fooBar", "foo-bar");
    assertThat(exc.storeKey).isEqualTo("fooBar");
    assertThat(exc.name).isEqualTo("foo-bar");
    assertThat(exc.getMessage())
      .isEqualTo("ConfigIsArrayButHasNoArg{storeKey:fooBar,name:foo-bar}");
    assertThat(exc.getCause()).isNull();
  }

  @Test
  void testBehaviorAsInvalidOption() {
    InvalidOption exc = new ConfigIsArrayButHasNoArg("fooBar", "foo-bar");
    assertThat(exc.option()).isEqualTo("foo-bar");
  }
}
