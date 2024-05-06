package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

@SuppressWarnings("missing-explicit-ctor")
public class FindFirstArgTest {

  @Test
  void testFindFirstArg_findAtFirstIndex() {
    var args = new String[]{"foo-bar", "-a", "--baz", "--bcd"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(0);
    assertThat(args[i]).isEqualTo("foo-bar");
  }

  @Test
  void testFindFirstArg_findAtMiddleIndex() {
    var args = new String[]{"--corge", "foo-bar", "-a", "--baz", "--bcd"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(1);
    assertThat(args[i]).isEqualTo("foo-bar");
  }

  @Test
  void tstFindFirstArg_findAtLastIndex() {
    var args = new String[]{"--corge", "--foo-bar", "-a", "baz"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(3);
    assertThat(args[i]).isEqualTo("baz");
  }

  @Test
  void testFindFirstArg_returnFoundFirst() {
    var args = new String[]{"--corge", "foo-bar", "-a", "baz", "--bcd"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(1);
    assertThat(args[i]).isEqualTo("foo-bar");
  }

  @Test
  void testFindFirstArg_notFound() {
    var args = new String[]{"--corge", "--foo-bar", "-a", "--baz", "--bcd"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(-1);
  }

  @Test
  void testFindFirstArg_supportDoubleHyphens() {
    var args = new String[]{"--corge", "--foo-bar", "--", "--baz", "--bcd"};

    int i = CliArgs.findFirstArg(args);
    assertThat(i).isEqualTo(3);
    assertThat(args[i]).isEqualTo("--baz");
  }
}
