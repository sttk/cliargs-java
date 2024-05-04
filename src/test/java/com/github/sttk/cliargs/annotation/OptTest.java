package com.github.sttk.cliargs.annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class OptTest {

  class MyOptions {
    @Opt
    private boolean flag;

    @Opt(cfg="foo-bar,f")
    private boolean fooBar;

    @Opt(cfg="=[1234,56]")
    private int[] baz;

    @Opt(desc="The description for option: qux")
    private String qux;

    @Opt(arg="<num>")
    private String[] quux;

    @Opt(cfg="abc-def=XXXX", arg="<val>",
      desc="This option can be specified a string value.")
    private String abcDef;
  }

  @Test
  void testOpt_noAttribute() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("flag");
    assertThat(field.getName()).isEqualTo("flag");
    assertThat(field.getType()).isEqualTo(boolean.class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("");
    assertThat(annotation.desc()).isEqualTo("");
    assertThat(annotation.arg()).isEqualTo("");
  }

  @Test
  void testOpt_onlyNamesAttribute() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("fooBar");
    assertThat(field.getName()).isEqualTo("fooBar");
    assertThat(field.getType()).isEqualTo(boolean.class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("foo-bar,f");
    assertThat(annotation.desc()).isEqualTo("");
    assertThat(annotation.arg()).isEqualTo("");
  }

  @Test
  void testOpt_onlyDefaultsAttribute() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("baz");
    assertThat(field.getName()).isEqualTo("baz");
    assertThat(field.getType()).isEqualTo(int[].class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("=[1234,56]");
    assertThat(annotation.desc()).isEqualTo("");
    assertThat(annotation.arg()).isEqualTo("");
  }

  @Test
  void testOpt_onlyDescAttribute() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("qux");
    assertThat(field.getName()).isEqualTo("qux");
    assertThat(field.getType()).isEqualTo(String.class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("");
    assertThat(annotation.desc()).isEqualTo("The description for option: qux");
    assertThat(annotation.arg()).isEqualTo("");
  }

  @Test
  void testOpt_onlyArgInHelpAttribute() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("quux");
    assertThat(field.getName()).isEqualTo("quux");
    assertThat(field.getType()).isEqualTo(String[].class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("");
    assertThat(annotation.desc()).isEqualTo("");
    assertThat(annotation.arg()).isEqualTo("<num>");
  }

  @Test
  void testOpt_allAttributes() throws Exception {
    var myOpts = new MyOptions();

    var field = MyOptions.class.getDeclaredField("abcDef");
    assertThat(field.getName()).isEqualTo("abcDef");
    assertThat(field.getType()).isEqualTo(String.class);

    Opt annotation = field.getAnnotation(Opt.class);
    assertThat(annotation).isNotNull();
    assertThat(annotation.cfg()).isEqualTo("abc-def=XXXX");
    assertThat(annotation.desc()).isEqualTo(
      "This option can be specified a string value.");
    assertThat(annotation.arg()).isEqualTo("<val>");
  }
}
