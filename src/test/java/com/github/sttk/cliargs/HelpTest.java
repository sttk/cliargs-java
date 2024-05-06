package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import static com.github.sttk.cliargs.OptCfg.NamedParam.*;
import static java.util.Collections.emptyList;
import com.github.sttk.exception.ReasonedException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("missing-explicit-ctor")
public class HelpTest {

  @Test
  void testHelp_empty() {
    var help = new Help();
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_oneLine_withNoWrapping() {
    var help = new Help();
    help.addText("abc");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("abc");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");

    help = new Help();
    help.addText("");
    iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");

    help = new Help();
    help.addText((String) null);
    iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_multiLines_withNoWrapping() {
    var help = new Help();
    help.addText("abc\ndef");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("abc");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("def");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_oneLine_withWrapping() {
    var help = new Help();
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("i12345");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_multiLines_withWrapping() {
    var help = new Help();
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345\n6789j123456789k123456789l123456789m123456789n123456789o123456789p123456789q12345678");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("i12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("6789j123456789k123456789l123456789m123456789n123456789o123456789p123456789q12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_withMarginLeftByConstructorArg() {
    var help = new Help(5);
    help.addText("abc\ndef");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     abc");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     def");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_withMarginLeftAndMarginRightByConstructorArg() {
    var help = new Help(5, 3);
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345\n6789");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     a123456789b123456789c123456789d123456789e123456789f123456789g123456789h1");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     23456789i12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     6789");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_withMarginLeftByAddTextArg() {
    var help = new Help();
    help.addText("abc\ndef", 0, 5);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     abc");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     def");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testHelp_addText_withMarginLeftAndMarginRightByAddTextArg() {
    var help = new Help();
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345\n6789", 0, 5, 3);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     a123456789b123456789c123456789d123456789e123456789f123456789g123456789h1");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     23456789i12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     6789");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddText_Indent() {
    var help = new Help();
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345\n6789", 8);
    help.addText("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789i12345\n6789", 5);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("        i12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("        6789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("a123456789b123456789c123456789d123456789e123456789f123456789g123456789h123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     i12345");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     6789");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddTexts_zeroText() {
    var help = new Help();
    help.addTexts(emptyList());
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");

    help = new Help();
    help.addTexts(null);
    iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddTexts_oneText() {
    var help = new Help();
    help.addTexts(List.of(
      "a12345678 b12345678 c12345678 d12345678 " +
      "e12345678 f12345678 g12345678 h12345678 i123"
    ));
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo(
      "a12345678 b12345678 c12345678 d12345678 " +
      "e12345678 f12345678 g12345678 h12345678"
    );

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("i123");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddTexts_multipleTexts() {
    var help = new Help();
    help.addTexts(List.of(
      "a12345678 b12345678 c12345678 d12345678 " +
      "e12345678 f12345678 g12345678 h12345678 i123",
      "j12345678 k12345678 l12345678 m12345678 " +
      "n12345678 o12345678 p12345678 q12345678 r123"
    ));
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo(
      "a12345678 b12345678 c12345678 d12345678 " +
      "e12345678 f12345678 g12345678 h12345678"
    );

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("i123");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo(
      "j12345678 k12345678 l12345678 m12345678 " +
      "n12345678 o12345678 p12345678 q12345678"
    );

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("r123");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddTexts_withIndent() {
    var help = new Help();
    help.addTexts(List.of(
      "a12345678  123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789",
      "b1234      123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789"
    ), 11);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("a12345678  123456789 123456789 123456789 123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("           123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("b1234      123456789 123456789 123456789 123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("           123456789 123456789");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddTexts_withMargins() {
    var help = new Help(2, 2);
    help.addTexts(List.of(
      "a12345678  123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789",
      "b1234      123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789"
    ), 11, 5, 3);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("       a12345678  123456789 123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                  123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("       b1234      123456789 123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                  123456789 123456789 123456789 123456789");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_zeroOpts() {
    var help = new Help();
    help.addOpts(new OptCfg[0]);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withNoWrapping() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(names("foo-bar"), desc("This is a description of option."))
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar  This is a description of option.");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withWrapping() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f", "foo", "b", "bar"),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678")
      )
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar, -f, --foo, -b, --bar  a12345678 b12345678 c12345678 d12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                                 e12345678 f12345678 g12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withMarginsByConstructorArg() {
    var help = new Help(5, 3);
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      )
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     --foo-bar, -f <text>  a12345678 b12345678 c12345678 d12345678 e12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                           f12345678 g12345678 h12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withMarginsByAddOptsArg() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      )
    }, 0, 5, 3);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     --foo-bar, -f <text>  a12345678 b12345678 c12345678 d12345678 e12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                           f12345678 g12345678 h12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withMarginsByConstructorArgAndAddOptsArg() {
    var help = new Help(2, 2);
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      )
    }, 0, 3, 1);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     --foo-bar, -f <text>  a12345678 b12345678 c12345678 d12345678 e12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                           f12345678 g12345678 h12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withIndentLongerThanTitle() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      )
    }, 25);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar, -f <text>     a12345678 b12345678 c12345678 d12345678 e12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                         f12345678 g12345678 h12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_oneOpts_withIndentShorterThanTitle() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      )
    }, 10);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar, -f <text>");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("          a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("          h12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_multipleOpts() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar", "f"),
        hasArg(true),
        desc("a12345678 b12345678 c12345678 d12345678 e12345678 f12345678 g12345678 h12345678"),
        argInHelp("<text>")
      ),
      new OptCfg(
        names("baz", "b"),
        desc("i12345678 j12345678 k12345678 l12345678 m12345678 n12345678 o12345678 p12345678")
      )
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar, -f <text>  a12345678 b12345678 c12345678 d12345678 e12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                      f12345678 g12345678 h12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--baz, -b             i12345678 j12345678 k12345678 l12345678 m12345678");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                      n12345678 o12345678 p12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_StoreKeyIsAnyOption() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar"),
        desc("a12345678 b12345678")
      ),
      new OptCfg(
        storeKey("*"),
        desc("c12345678 d12345678")
      )
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar  a12345678 b12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_FirstElementInNamesIsAnyOption() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar"),
        desc("a12345678 b12345678")
      ),
      new OptCfg(
        names("*"),
        desc("c12345678 d12345678")
      )
    });
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar  a12345678 b12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_StoreKeyIsAnyOption_withIndent() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        storeKey("*"),
        desc("c12345678 d12345678")
      ),
      new OptCfg(
        names("foo-bar"),
        desc("a12345678 b12345678")
      )
    }, 5);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     a12345678 b12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_FirstElementInNamesIsAnyOption_withIndent() {
    var help = new Help();
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("*"),
        desc("c12345678 d12345678")
      ),
      new OptCfg(
        names("foo-bar"),
        desc("a12345678 b12345678")
      )
    }, 5);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("--foo-bar");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("     a12345678 b12345678");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testNewHelp_ifLineWidthLessThanSumOfMargins() {
    var help = new Help(71, 10);
    help.addText("a23456789 b23456789 c23456789");
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddText_ifLineWidthLessThanSumOfMargins() {
    var help = new Help(10, 40);
    help.addText("abcdefg", 10, 10, 10);
    help.addText("hijklmn", 10, 10);
    help.addText("opqurstu", 10, 10, 11);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                    hijklmn");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testAddOpts_ifLineWidthLessThanSunOfMargins() {
    var help = new Help(10, 30);
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("foo-bar"),
        desc("This is a description of option.")
      )
    }, 10, 10, 20);
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("baz"),
        desc("This is a description of option.")
      )
    }, 10, 10);
    help.addOpts(new OptCfg[] {
      new OptCfg(
        names("qux"),
        desc("This is a description of option.")
      )
    }, 10, 10, 21);
    var iter = help.iter();

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                    --baz     This is a");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                              description of");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("                              option.");

    assertThat(iter.hasNext()).isTrue();
    assertThat(iter.next()).isEqualTo("");

    assertThat(iter.hasNext()).isFalse();
    assertThat(iter.next()).isEqualTo("");
  }

  @Test
  void testPrint_curl() {
    // The source of the following text is the output of `curl --help` in
    // curl 7.87.0. (https://curl.se/docs/copyright.html)

    var help = new Help();

    help.addText("Usage: curl [options...] <url>");

    help.addOpts(new OptCfg[]{
      new OptCfg(
        storeKey("Data"),
        names("d", "data"),
        desc("HTTP POST data"),
        hasArg(true),
        argInHelp("<data>")
      ),
      new OptCfg(
        storeKey("Fail"),
        names("f", "fail"),
        desc("Fail fast with no output on HTTP errors")
      ),
      new OptCfg(
        storeKey("Help"),
        names("h", "help"),
        desc("Get help for commands"),
        hasArg(true),
        argInHelp("<category>")
      ),
      new OptCfg(
        storeKey("Include"),
        names("i", "include"),
        desc("Include protocol response headers in the output")
      ),
      new OptCfg(
        storeKey("Output"),
        names("o", "output"),
        desc("Write to file instead of stdout"),
        hasArg(true),
        argInHelp("<file>")
      ),
      new OptCfg(
        storeKey("RemoteName"),
        names("O", "remote-name"),
        desc("Write output to a file named as the remote file")
      ),
      new OptCfg(
        storeKey("Silent"),
        names("s", "silent"),
        desc("Silent mode")
      ),
      new OptCfg(
        storeKey("UploadFile"),
        names("T", "upload-file"),
        desc("Transfer local FILE to destination"),
        hasArg(true),
        argInHelp("<file>")
      ),
      new OptCfg(
        storeKey("User"),
        names("u", "user"),
        desc("Server user and password"),
        hasArg(true),
        argInHelp("<user:password>")
      ),
      new OptCfg(
        storeKey("UserAgent"),
        names("A", "user-agent"),
        desc("Send User-Agent <name> to server"),
        hasArg(true),
        argInHelp("<name>")
      ),
      new OptCfg(
        storeKey("Verbose"),
        names("v", "verbose"),
        desc("Make the operation more talkative")
      ),
      new OptCfg(
        storeKey("Version"),
        names("V", "version"),
        desc("Show version number and quit")
      )
    }, 0, 1);

    help.addText("\n" +
      "This is not the full help, this menu is stripped into categories.\n" +
      "Use \"--help category\" to get an overview of all categories.\n" +
      "For all options use the manual or \"--help all\"."
    );

    help.print();
  }
}
