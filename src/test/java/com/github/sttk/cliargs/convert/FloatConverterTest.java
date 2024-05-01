package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.exception.ReasonedException;

@SuppressWarnings("missing-explicit-ctor")
public class FloatConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(Float.class),
      defaults(12.34f, -56.78f),
      converter(new FloatConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Float.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(12.34f);
    assertThat(optCfg.defaults.get(1)).isEqualTo(-56.78f);
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(FloatConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new FloatConverter().convert("-1.23", "f", "foo"))
      .isEqualTo(-1.23f);
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new FloatConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case FloatConverter.InvalidFloatFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
