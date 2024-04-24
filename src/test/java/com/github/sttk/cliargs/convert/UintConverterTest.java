package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.reasonedexception.ReasonedException;

public class UintConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(Integer.class),
      defaults(12, 34),
      converter(new UintConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Integer.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(12);
    assertThat(optCfg.defaults.get(1)).isEqualTo(34);
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(UintConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new UintConverter().convert("123", "f", "foo"))
      .isEqualTo(123);
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new UintConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case UintConverter.InvalidIntegerFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }

  @Test
  void testConvert_negative() throws Exception {
    try {
      new UintConverter().convert("-123", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case UintConverter.IntegerIsNegative r -> {
          assertThat(r.optArg()).isEqualTo("-123");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
