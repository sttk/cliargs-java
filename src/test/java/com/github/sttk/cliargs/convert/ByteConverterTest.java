package com.github.sttk.cliargs.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import com.github.sttk.cliargs.OptCfg;
import com.github.sttk.reasonedexception.ReasonedException;

public class ByteConverterTest {

  @Test
  void testConstructOptCfg() {
    @SuppressWarnings("unchecked")
    var optCfg = new OptCfg(
      type(Byte.class),
      defaults((byte)0x30, (byte)-0x31),
      converter(new ByteConverter())
    );

    assertThat(optCfg.storeKey).isNull();
    assertThat(optCfg.names).isEmpty();
    assertThat(optCfg.hasArg).isTrue();
    assertThat(optCfg.isArray).isFalse();
    assertThat(optCfg.type).isEqualTo(Byte.class);
    assertThat(optCfg.defaults).hasSize(2);
    assertThat(optCfg.defaults.get(0)).isEqualTo((byte)0x30);
    assertThat(optCfg.defaults.get(1)).isEqualTo((byte)-0x31);
    assertThat(optCfg.desc).isNull();
    assertThat(optCfg.argInHelp).isNull();
    assertThat(optCfg.converter).isInstanceOf(ByteConverter.class);
  }

  @Test
  void testConvert_normally() throws Exception {
    assertThat(new ByteConverter().convert("123", "f", "foo"))
      .isEqualTo((byte)123);
  }

  @Test
  void testConvert_invalidFormat() throws Exception {
    try {
      new ByteConverter().convert("aaa", "f", "foo");
      fail();
    } catch (ReasonedException e) {
      switch (e.getReason()) {
        case ByteConverter.InvalidByteFormat r -> {
          assertThat(r.optArg()).isEqualTo("aaa");
          assertThat(r.option()).isEqualTo("f");
          assertThat(r.storeKey()).isEqualTo("foo");
        }
        default -> fail(e);
      }
    }
  }
}
