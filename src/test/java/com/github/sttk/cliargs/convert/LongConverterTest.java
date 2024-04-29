package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.reasonedexception.ReasonedException;

public class LongConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(Long.class),
      defaults(12L, -34L),
      converter(new LongConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Long.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(12L);
    assertThat(optCfg.defaults.get(1)).isEqualTo(-34L);
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(LongConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new LongConverter().convert("-123", "f", "foo"))
      .isEqualTo(-123L);
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new LongConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case LongConverter.InvalidLongFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
