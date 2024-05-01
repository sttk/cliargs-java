package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.exception.ReasonedException;
import java.math.BigInteger;

public class BigIntConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(BigInteger.class),
      defaults(new BigInteger("12"), new BigInteger("-34")),
      converter(new BigIntConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(BigInteger.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo(new BigInteger("12"));
    assertThat(optCfg.defaults.get(1)).isEqualTo(new BigInteger("-34"));
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(BigIntConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new BigIntConverter().convert("-123", "f", "foo"))
      .isEqualTo(new BigInteger("-123"));
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new BigIntConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case BigIntConverter.InvalidBigIntFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
