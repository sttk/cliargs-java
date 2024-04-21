package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.sttk.cliargs.unicode.Ascii.isAlNumMarks;
import static com.github.sttk.cliargs.unicode.Ascii.isAlphabets;

import org.junit.jupiter.api.Test;

public class AsciiTest {

  @Test
  void testIsAlNumMarks() {
    assertThat(isAlNumMarks(",".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("-".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks(".".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("/".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("0".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks("9".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks(":".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("@".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("A".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks("Z".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks("[".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("`".codePointAt(0))).isFalse();;
    assertThat(isAlNumMarks("a".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks("z".codePointAt(0))).isTrue();;
    assertThat(isAlNumMarks("{".codePointAt(0))).isFalse();;
  }

  @Test
  void testIsAlphabets() {
    assertThat(isAlphabets(",".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("-".codePointAt(0))).isFalse();;
    assertThat(isAlphabets(".".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("/".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("0".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("9".codePointAt(0))).isFalse();;
    assertThat(isAlphabets(":".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("@".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("A".codePointAt(0))).isTrue();;
    assertThat(isAlphabets("Z".codePointAt(0))).isTrue();;
    assertThat(isAlphabets("[".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("`".codePointAt(0))).isFalse();;
    assertThat(isAlphabets("a".codePointAt(0))).isTrue();;
    assertThat(isAlphabets("z".codePointAt(0))).isTrue();;
    assertThat(isAlphabets("{".codePointAt(0))).isFalse();;
  }
}
