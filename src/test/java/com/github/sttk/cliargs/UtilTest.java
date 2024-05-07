package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static com.github.sttk.cliargs.Util.isBlank;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("missing-explicit-ctor")
public class UtilTest {

  @Test
  void testIsEmpty_string() {
    assertThat(isEmpty("abc")).isFalse();
    assertThat(isEmpty("")).isTrue();
    assertThat(isEmpty((String)null)).isTrue();
  }

  @Test
  void testIsEmpty_stringArray() {
    assertThat(isEmpty(new String[]{"abc"})).isFalse();
    assertThat(isEmpty(new String[]{""})).isFalse();
    assertThat(isEmpty(new String[0])).isTrue();
    assertThat(isEmpty((String[]) null)).isTrue();
  }

  @Test
  void testIsEmpty_list() {
    assertThat(isEmpty(List.of("abc"))).isFalse();
    assertThat(isEmpty(List.of(""))).isFalse();
    assertThat(isEmpty(new ArrayList<String>())).isTrue();
    assertThat(isEmpty((List<Integer>) null)).isTrue();
  }

  @Test
  void testIsBlank_string() {
    assertThat(isBlank("abc")).isFalse();
    assertThat(isBlank("")).isTrue();
    assertThat(isBlank((String)null)).isTrue();
    assertThat(isBlank(" ")).isTrue();
    assertThat(isBlank("　")).isTrue();
    assertThat(isBlank("\t")).isTrue();
  }
}
