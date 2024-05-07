package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.github.sttk.exception.ReasonedException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("missing-explicit-ctor")
public class CmdTest {

  @Test
  void testConstructor_nameAndArgsAndOpts() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);
    assertThat(cmd.getName()).isEqualTo("foo");
    assertThat(cmd.getArgs()).containsExactly("a0", "a1", "a2");

    List<Integer> o0 = cmd.getOptArgs("o0");
    List<?> o1 = cmd.getOptArgs("o1");
    List<?> o2 = cmd.getOptArgs("o2");
    assertThat(o0).containsExactly(123, 456);
    assertThat(o1).isEmpty();
    assertThat(o2).isEmpty();
  }

  @Test
  void testHasOptArg() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);
    assertThat(cmd.hasOpt("o0")).isTrue();
    assertThat(cmd.hasOpt("o1")).isTrue();
    assertThat(cmd.hasOpt("o2")).isFalse();
  }

  @Test
  void testGetOptArg() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);

    Integer o0 = cmd.getOptArg("o0");
    Integer o1 = cmd.getOptArg("o1");
    Integer o2 = cmd.getOptArg("o2");
    assertThat(o0).isEqualTo(123);
    assertThat(o1).isNull();
    assertThat(o2).isNull();
  }

  @Test
  void testGetOptArgs_returnedListIsUnmodifiable() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);

    List<Integer> o0 = cmd.getOptArgs("o0");
    List<String> o1 = cmd.getOptArgs("o1");
    List<Object> o2 = cmd.getOptArgs("o2");
    assertThat(o0).containsExactly(123, 456);
    assertThat(o1).isEmpty();
    assertThat(o2).isEmpty();

    try {
      o0.add(789);
      fail();
    } catch (UnsupportedOperationException e) {}
  }

  @Test
  void testGetArgs_returnedListIsUnmodifiable() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);

    List<Integer> o0 = cmd.getOptArgs("o0");
    List<String> o1 = cmd.getOptArgs("o1");
    List<Object> o2 = cmd.getOptArgs("o2");
    assertThat(o0).containsExactly(123, 456);
    assertThat(o1).isEmpty();
    assertThat(o2).isEmpty();

    var a = cmd.getArgs();
    try {
      a.add("xxx");
      fail();
    } catch (UnsupportedOperationException e) {}
  }

  @Test
  void testToString() {
    var args = new ArrayList<>(List.of("a0", "a1", "a2"));

    var opts = new HashMap<String, List<?>>();
    opts.put("o0", new ArrayList<Integer>(List.of(123, 456)));
    opts.put("o1", new ArrayList<String>());

    var cmd = new Cmd("foo", args, opts);

    assertThat(cmd.toString()).isEqualTo(
      "Cmd{name=foo, args=[a0, a1, a2], opts={o0=[123, 456], o1=[]}}"
    );
  }
}
