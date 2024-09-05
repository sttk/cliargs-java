package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import java.util.List;

@SuppressWarnings("missing-explicit-ctor")
public class FailToSetOptionStoreFieldTest {

  @Test
  void testConstructor() {
    var cause = new NumberFormatException();
    var exc = new FailToSetOptionStoreField("fooBar", int.class, List.of("123", "x"), cause);
    assertThat(exc.field).isEqualTo("fooBar");
    assertThat(exc.type).isEqualTo(int.class);
    assertThat(exc.optArgs).containsExactly("123", "x");
    assertThat(exc.getMessage())
      .isEqualTo("FailToSetOptionStoreField{field:fooBar,type:int,optArgs:[123, x],cause:java.lang.NumberFormatException}");
    assertThat(exc.getCause()).isEqualTo(cause);
  }
}
