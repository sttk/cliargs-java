package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class UnconfiguredOptionTest {

  @Test
  void testConstructor() {
    var exc = new UnconfiguredOption("foo-bar");
    assertThat(exc.option).isEqualTo("foo-bar");
    assertThat(exc.getMessage()).isEqualTo("UnconfiguredOption{option:foo-bar}");
    assertThat(exc.getCause()).isNull();
  }

  @Test
  void testBehaviorAsInvalidOption() {
    InvalidOption exc = new UnconfiguredOption("foo-bar");
    assertThat(exc.option()).isEqualTo("foo-bar");
  }
}
