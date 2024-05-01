package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import com.github.sttk.exception.ReasonedException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultTest {

  record MyReason() {}

  @Test
  void testConstructor() {
    var args = new ArrayList<String>();
    var opts = new HashMap<String, List<?>>();

    var cmd = new Cmd("app", args, opts);
    var cfgs = new OptCfg[0];
    var exc = new ReasonedException(new MyReason());

    var result = new Result(cmd, cfgs, exc);
    assertThat(result.cmd()).isEqualTo(cmd);
    assertThat(result.optCfgs()).isEqualTo(cfgs);
    assertThat(result.exception()).isEqualTo(exc);
  }

  @Test
  void testCmdOrThrow() {
    var args = new ArrayList<String>();
    var opts = new HashMap<String, List<?>>();

    var cmd = new Cmd("app", args, opts);
    var cfgs = new OptCfg[0];
    var exc = new ReasonedException(new MyReason());

    var result = new Result(cmd, cfgs, null);
    try {
      cmd = result.cmdOrThrow();
      assertThat(result.cmd()).isEqualTo(cmd);
      assertThat(result.optCfgs()).isEqualTo(cfgs);
      assertThat(result.exception()).isNull();
    } catch (ReasonedException e) {
      fail(e);
    }

    result = new Result(cmd, cfgs, exc);
    try {
      cmd = result.cmdOrThrow();
      fail();
    } catch (ReasonedException e) {
      assertThat(result.cmd()).isEqualTo(cmd);
      assertThat(result.optCfgs()).isEqualTo(cfgs);
      assertThat(result.exception()).isEqualTo(exc);
      assertThat(e).isEqualTo(exc);
    }
  }
}
