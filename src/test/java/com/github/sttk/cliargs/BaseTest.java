package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.sttk.cliargs.Base.isAllowedCodePoint;
import static com.github.sttk.cliargs.Base.isAllowedFirstCodePoint;
import static com.github.sttk.cliargs.Base.isEmpty;
import static com.github.sttk.cliargs.Base.isBlank;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

@SuppressWarnings("missing-explicit-ctor")
public class BaseTest {

  @Nested
  class CodePointTest {
    @Test
    void testIsAllowedCodePoint() {
      assertThat(isAllowedCodePoint(",".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("-".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint(".".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("/".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("0".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint("9".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint(":".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("@".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("A".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint("Z".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint("[".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("`".codePointAt(0))).isFalse();;
      assertThat(isAllowedCodePoint("a".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint("z".codePointAt(0))).isTrue();;
      assertThat(isAllowedCodePoint("{".codePointAt(0))).isFalse();;
    }
  
    @Test
    void testIsAllowedFirstCodePoint() {
      assertThat(isAllowedFirstCodePoint(",".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("-".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint(".".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("/".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("0".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("9".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint(":".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("@".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("A".codePointAt(0))).isTrue();;
      assertThat(isAllowedFirstCodePoint("Z".codePointAt(0))).isTrue();;
      assertThat(isAllowedFirstCodePoint("[".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("`".codePointAt(0))).isFalse();;
      assertThat(isAllowedFirstCodePoint("a".codePointAt(0))).isTrue();;
      assertThat(isAllowedFirstCodePoint("z".codePointAt(0))).isTrue();;
      assertThat(isAllowedFirstCodePoint("{".codePointAt(0))).isFalse();;
    }
  }

  @Nested
  class IsEmpty {
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
}
