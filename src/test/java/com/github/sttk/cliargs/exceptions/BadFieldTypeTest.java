package com.github.sttk.cliargs.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import java.util.Date;

@SuppressWarnings("missing-explicit-ctor")
public class BadFieldTypeTest {

  @Test
  void testConstructor() {
    var exc = new BadFieldType("fooBar", Date.class);
    assertThat(exc.field).isEqualTo("fooBar");
    assertThat(exc.type).isEqualTo(Date.class);
    assertThat(exc.getMessage()).isEqualTo("BadFieldType{field:fooBar,type:java.util.Date}");
    assertThat(exc.getCause()).isNull();
  }
}
