package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.reasonedexception.ReasonedException;
import java.math.BigDecimal;

public class BigDecimalConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(BigDecimal.class),
      defaults(new BigDecimal("12"), new BigDecimal("-34")),
      converter(new BigDecimalConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(BigDecimal.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(new BigDecimal("12"));
    assertThat(optCfg.defaults.get(1)).isEqualTo(new BigDecimal("-34"));
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(BigDecimalConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new BigDecimalConverter().convert("-123", "f", "foo"))
      .isEqualTo(new BigDecimal("-123"));
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new BigDecimalConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case BigDecimalConverter.InvalidBigDecimalFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
