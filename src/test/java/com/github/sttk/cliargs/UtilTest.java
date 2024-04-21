package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.orEmpty;
import static com.github.sttk.cliargs.Util.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

public class UtilTest {

  @Test
  void testOrEmpty_string() {
    assertThat(orEmpty("abc")).isEqualTo("abc");
    assertThat(orEmpty("")).isEqualTo("");
    assertThat(orEmpty(null)).isEqualTo("");
  }

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
}
