package com.github.sttk.cliargs;

import static org.assertj.core.api.Assertions.assertThat;
import static com.github.sttk.cliargs.Base.isAllowedCodePoint;
import static com.github.sttk.cliargs.Base.isAllowedFirstCodePoint;
import static com.github.sttk.cliargs.Base.isEmpty;
import static com.github.sttk.cliargs.Base.isBlank;
import static com.github.sttk.cliargs.OptCfg.Param.*;

import com.github.sttk.linebreak.Term;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

@SuppressWarnings("missing-explicit-ctor")
public class HelpTest {

  @Nested
  class TestsOfMakeOptTitle {
    @Test
    void testWhenCfgHasOnlyOneLongName() {
      var cfg = new OptCfg(names("foo-bar"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("--foo-bar");
    }

    @Test
    void testWhenCfgHasOnlyOneShortName() {
      var cfg = new OptCfg(names("f"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("-f");
    }

    @Test
    void testWhenCfgHasMultipleNames() {
      var cfg = new OptCfg(names("f", "b", "foo-bar"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("-f, -b, --foo-bar");

      cfg = new OptCfg(names("foo-bar", "f", "b"));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("--foo-bar, -f, -b");
    }

    @Test
    void testWhenCfgHasNoNameButStoreKey() {
      var cfg = new OptCfg(storeKey("Foo_Bar"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("--Foo_Bar");
    }

    @Test
    void testWhenCfgNamesContainsEmptyName() {
      var cfg = new OptCfg(names("", "f"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("-f");

      cfg = new OptCfg(names("", "foo-bar"));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("--foo-bar");

      cfg = new OptCfg(names("", "f", "", "b", ""));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("-f,     -b");

      cfg = new OptCfg(names("", "foo", "", "bar", ""));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("--foo,     --bar");
    }

    @Test
    void testWhenCfgNamesAreAllEmptyAndHasStoreKey() {
      var cfg = new OptCfg(storeKey("FooBar"), names("", ""));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(8);
      assertThat(body.text).isEqualTo("--FooBar");

      cfg = new OptCfg(storeKey("F"), names("", ""));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(8);
      assertThat(body.text).isEqualTo("-F");

      cfg = new OptCfg(storeKey(""), names("", ""));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(8);
      assertThat(body.text).isEqualTo("");
    }

    @Test
    void testWithArgInHelp() {
      var cfg = new OptCfg(names("foo-bar"), argInHelp("<n>"));
      var body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(0);
      assertThat(body.text).isEqualTo("--foo-bar <n>");

      cfg = new OptCfg(names("", "foo-bar"), argInHelp("txt"));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("--foo-bar txt");

      cfg = new OptCfg(names("", "f", "", "b", ""), argInHelp("num"));
      body = Help.makeOptTitle(cfg);
      assertThat(body.firstIndent).isEqualTo(4);
      assertThat(body.text).isEqualTo("-f,     -b num");
    }
  }

  @Nested
  class TestsOfCreateOptsHelp {
    @Test
    void testWhenCfgHasOnlyOneLongName() {
      var cfgs = List.of(new OptCfg(names("foo-bar")));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("--foo-bar");
      assertThat(indentBox[0]).isEqualTo(11);
    }

    @Test
    void testWhenCfgHasOnlyOneLongNameAndDesc() {
      var cfgs = List.of(new OptCfg(names("foo-bar"), desc("The description of foo-bar.")));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("--foo-bar  The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(11);
    }

    @Test
    void testWhenCfgHasOnlyOneLongNameAndDescAndArgInHelp() {
      var cfgs = List.of(new OptCfg(
        names("foo-bar"),
        desc("The description of foo-bar."),
        argInHelp("<num>")
      ));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("--foo-bar <num>  The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(17);
    }

    @Test
    void testWhenCfgHasOnlyOneShortName() {
      var cfgs = List.of(new OptCfg(names("f")));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("-f");
      assertThat(indentBox[0]).isEqualTo(4);
    }

    @Test
    void testWhenCfgHasOnlyOneShortNameAndDesc() {
      var cfgs = List.of(new OptCfg(
        names("f"),
        desc("The description of f.")
      ));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("-f  The description of f.");
      assertThat(indentBox[0]).isEqualTo(4);
    }

    @Test
    void testWhenCfgHasOnlyOneShortNameAndDescAndArgInHelp() {
      var cfgs = List.of(new OptCfg(
        names("f"),
        desc("The description of f."),
        argInHelp("<n>")
      ));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo("-f <n>  The description of f.");
      assertThat(indentBox[0]).isEqualTo(8);
    }

    @Test
    void testWhenIndentIsPositiveAndLongerThanTitle() {
      var cfgs = List.of(new OptCfg(
        names("foo-bar"),
        desc("The description of foo-bar."),
        argInHelp("<num>")
      ));

      int[] indentBox = new int[]{19};
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo(
        "--foo-bar <num>    The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(19);
    }

    @Test
    void testWhenIndentIsPositiveAndShorterThanTitle() {
      var cfgs = List.of(new OptCfg(
        names("foo-bar"),
        desc("The description of foo-bar."),
        argInHelp("<num>")
      ));

      int[] indentBox = new int[]{16};
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo(
        "--foo-bar <num>\n                The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(16);

      indentBox[0] = 10;
      bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(0);
      assertThat(bodies.get(0).text).isEqualTo(
        "--foo-bar <num>\n          The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(10);
    }

    @Test
    void testWhenNamesContainsEmptyStrings() {
      var cfgs = List.of(new OptCfg(
        names("", "", "f", "", "foo-bar", ""),
        desc("The description of foo-bar."),
        argInHelp("<num>")
      ));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(8);
      assertThat(bodies.get(0).text).isEqualTo(
        "-f,     --foo-bar <num>  The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(8 + 25);

      indentBox[0] = 35;  // longer than title width
      bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(8);
      assertThat(bodies.get(0).text).isEqualTo(
        "-f,     --foo-bar <num>    The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(35);

      indentBox[0] = 33;  // equal to title width
      bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(8);
      assertThat(bodies.get(0).text).isEqualTo(
        "-f,     --foo-bar <num>  The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(33);

      indentBox[0] = 32;  // shorter than title width
      bodies = Help.createOptsHelp(cfgs, indentBox);

      assertThat(bodies.size()).isEqualTo(1);
      assertThat(bodies.get(0).firstIndent).isEqualTo(8);
      assertThat(bodies.get(0).text).isEqualTo(
        "-f,     --foo-bar <num>\n" + " ".repeat(32) + "The description of foo-bar.");
      assertThat(indentBox[0]).isEqualTo(32);
    }

    @Test
    void testIgnoreOptCfgsOfWhichStoreKeyAndNamesAreEmpty() {
      var cfgs = List.of(new OptCfg());

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);

      indentBox = new int[]{4};
      bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);
    }

    @Test
    void testIgnoreOptCfgsOfWhichStoreKeyOrNameIsAsterisk() {
      var cfgs = List.of(new OptCfg(storeKey("*")));

      int[] indentBox = new int[1];
      var bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);

      indentBox = new int[]{4};
      bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);

      cfgs = List.of(new OptCfg(names("*")));

      indentBox = new int[1];
      bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);

      indentBox = new int[]{4};
      bodies = Help.createOptsHelp(cfgs, indentBox);
      assertThat(bodies.size()).isEqualTo(0);
    }
  }

  @Nested
  class TestsOfHelp {
    @Test
    void testDefaultConstructor() {
      var help = new Help();
      var iter = help.iter();
      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextIfOneLineWithZeroWrapping() {
      var help = new Help();
      help.addText("abc");

      var iter = help.iter();
      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("abc");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextIfOneLineWithWrapping() {
      int termCols = Term.getCols();
      String text = "a".repeat(termCols) + "123456";

      var help = new Help();
      help.addText(text);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      String line = iter.next();
      assertThat(line).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("123456");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextIfMultiLInesWithWrapping() {
      int termCols = Term.getCols();
      String text = "a".repeat(termCols) + "123456\n" + "b".repeat(termCols) + "789";;

      var help = new Help();
      help.addText(text);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      String line = iter.next();
      assertThat(line).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("123456");

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("b".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("789");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testConstructorTakingMargins() {
      int termCols = Term.getCols();
      String text = "a".repeat(termCols - 5 - 3) + "12345\n" +
        "b".repeat(termCols - 5 - 3) + "6789";;

      var help = new Help(5, 3);
      help.addText(text);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      String line = iter.next();
      assertThat(line).isEqualTo("     " + "a".repeat(termCols - 5 - 3));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     12345");

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     " + "b".repeat(termCols - 5 - 3));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     6789");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextWithMargins() {
      int termCols = Term.getCols();
      var text = "a".repeat(termCols - 5 - 3) + "12345\n" +
        "b".repeat(termCols - 5 - 3) + "6789";

      var help = new Help();
      help.addTextWithMargins(text, 5, 3);
      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      var line = iter.next();
      assertThat(line).isEqualTo("     " + "a".repeat(termCols - 5 - 3));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     12345");

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     " + "b".repeat(termCols - 5 - 3));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("     6789");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextWithIndent() {
      int termCols = Term.getCols();
      var text = "a".repeat(termCols) + "12345\n" +
        "b".repeat(termCols - 8) + "6789";

      var help = new Help();
      help.addTextWithIndent(text, 8);
      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      var line = iter.next();
      assertThat(line).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("        12345");

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("        " + "b".repeat(termCols - 8));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("        6789");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextWithIndentAndMargins() {
      int termCols = Term.getCols();
      var text = "a".repeat(termCols - 1 - 2) + "12345\n" +
        "b".repeat(termCols - 8 - 1 -2) + "6789";

      var help = new Help();
      help.addTextWithIndentAndMargins(text, 8, 1, 2);
      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      var line = iter.next();
      assertThat(line).isEqualTo(" " + "a".repeat(termCols - 1 - 2));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("         12345");

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("         " + "b".repeat(termCols - 8 - 1 - 2));

      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("         6789");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextIfTextIsEmpty() {
      var help = new Help();
      help.addText("");
      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      var line = iter.next();
      assertThat(line).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextMultipleTimes() {
      int termCols = Term.getCols();

      var text = "a".repeat(termCols - 4 - 3) +
        "b".repeat(termCols - 4 - 5 - 3) +
        "c".repeat(termCols - 4 - 5 - 3);

      var help = new Help(1, 1);
      help.addTextWithIndentAndMargins(text, 5, 3, 2);

      text = "d".repeat(termCols - 2 - 2) +
        "e".repeat(termCols - 2 - 5 - 2) +
        "f".repeat(termCols - 2 - 5 - 2);

      help.addTextWithIndentAndMargins(text, 5, 1, 1);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      var line = iter.next();
      assertThat(line).isEqualTo("    " + "a".repeat(termCols - 7));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("         " + "b".repeat(termCols - 12));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("         " + "c".repeat(termCols - 12));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("  " + "d".repeat(termCols - 4));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("       " + "e".repeat(termCols - 9));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      line = iter.next();
      assertThat(line).isEqualTo("       " + "f".repeat(termCols - 9));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.hasNext()).isFalse();
    }

    @Test
    void testAddTextsIfArrayIsEmpty() {
      var help = new Help();
      help.addTexts(new String[0]);

      var iter = help.iter();

      assertThat(iter.hasNext()).isFalse();
      var line = iter.next();
      assertThat(line).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextsIfListIsEmpty() {
      var help = new Help();
      help.addTexts(new ArrayList<>(0));

      var iter = help.iter();

      assertThat(iter.hasNext()).isFalse();
      var line = iter.next();
      assertThat(line).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      line = iter.next();
      assertThat(line).isEqualTo("");
    }

    @Test
    void testAddTextsIfArrayHasAText() {
      int termCols = Term.getCols();

      var texts = new String[]{
        "a".repeat(termCols) + "b".repeat(termCols) + "c".repeat(termCols)
      };

      var help = new Help();
      help.addTexts(texts);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("b".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("c".repeat(termCols));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsIfListHasAText() {
      int termCols = Term.getCols();

      var texts = new ArrayList<String>();
      texts.add("a".repeat(termCols) + "b".repeat(termCols) + "c".repeat(termCols));

      var help = new Help();
      help.addTexts(texts);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("b".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("c".repeat(termCols));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsIfArrayHasMultipleTexts() {
      int termCols = Term.getCols();

      var texts = new String[]{
        "a".repeat(termCols) + "b".repeat(termCols) + "c".repeat(termCols),
        "d".repeat(termCols) + "e".repeat(termCols) + "f".repeat(termCols)
      };

      var help = new Help();
      help.addTexts(texts);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("b".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("c".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("d".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("e".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("f".repeat(termCols));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsIfListHasMultipleTexts() {
      int termCols = Term.getCols();

      var texts = new ArrayList<String>();
      texts.add("a".repeat(termCols) + "b".repeat(termCols) + "c".repeat(termCols));
      texts.add("d".repeat(termCols) + "e".repeat(termCols) + "f".repeat(termCols));

      var help = new Help();
      help.addTexts(texts);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("b".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("c".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("d".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("e".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("f".repeat(termCols));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithIndent_array() {
      int termCols = Term.getCols();

      var texts = new String[]{
        "a".repeat(termCols) + "b".repeat(termCols - 5) + "c".repeat(termCols - 5),
        "d".repeat(termCols) + "e".repeat(termCols - 5) + "f".repeat(termCols - 5)
      };

      var help = new Help();
      help.addTextsWithIndent(texts, 5);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "b".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "c".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("d".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "e".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "f".repeat(termCols - 5));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithIndent_list() {
      int termCols = Term.getCols();

      var texts = new ArrayList<String>();
      texts.add("a".repeat(termCols) + "b".repeat(termCols - 5) + "c".repeat(termCols - 5));
      texts.add("d".repeat(termCols) + "e".repeat(termCols - 5) + "f".repeat(termCols - 5));

      var help = new Help();
      help.addTextsWithIndent(texts, 5);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("a".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "b".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "c".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("d".repeat(termCols));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "e".repeat(termCols - 5));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("     " + "f".repeat(termCols - 5));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithMargins_array() {
      int termCols = Term.getCols();

      var texts = new String[]{
        "a".repeat(termCols - 3 - 3) + "b".repeat(termCols - 3 - 3) + "c".repeat(termCols - 3 - 3),
        "d".repeat(termCols - 3 - 3) + "e".repeat(termCols - 3 - 3) + "f".repeat(termCols - 3 - 3)
      };

      var help = new Help(1, 1);
      help.addTextsWithMargins(texts, 2, 2);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "a".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "b".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "c".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "d".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "e".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "f".repeat(termCols - 6));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithMargins_list() {
      int termCols = Term.getCols();

      var texts = List.of(
        "a".repeat(termCols - 3 - 3) + "b".repeat(termCols - 3 - 3) + "c".repeat(termCols - 3 - 3),
        "d".repeat(termCols - 3 - 3) + "e".repeat(termCols - 3 - 3) + "f".repeat(termCols - 3 - 3)
      );

      var help = new Help(1, 1);
      help.addTextsWithMargins(texts, 2, 2);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "a".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "b".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "c".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "d".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "e".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "f".repeat(termCols - 6));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithIndentAndMargins_array() {
      int termCols = Term.getCols();

      var texts = new String[]{
        "a".repeat(termCols - 3 - 3) + "b".repeat(termCols - 3 - 5 - 3) +
        "c".repeat(termCols - 3 - 5 - 3),
        "d".repeat(termCols - 3 - 3) + "e".repeat(termCols - 3 - 5 - 3) +
        "f".repeat(termCols - 3 - 5 - 3)
      };

      var help = new Help(1, 1);
      help.addTextsWithIndentAndMargins(texts, 5, 2, 2);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "a".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "b".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "c".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "d".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "e".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "f".repeat(termCols - 11));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddTextsWithIndentAndMargins_list() {
      int termCols = Term.getCols();

      var texts = List.of(
        "a".repeat(termCols - 3 - 3) + "b".repeat(termCols - 3 - 5 - 3) +
        "c".repeat(termCols - 3 - 5 - 3),
        "d".repeat(termCols - 3 - 3) + "e".repeat(termCols - 3 - 5 - 3) +
        "f".repeat(termCols - 3 - 5 - 3)
      );

      var help = new Help(1, 1);
      help.addTextsWithIndentAndMargins(texts, 5, 2, 2);

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "a".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "b".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "c".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("   " + "d".repeat(termCols - 6));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "e".repeat(termCols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        " + "f".repeat(termCols - 11));

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithNoWrapping() {
      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("This is a description of option."))
      });

      var iter = help.iter();
      assertThat(iter.next()).isEqualTo("--foo-bar  This is a description of option.");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithWrapping() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("a".repeat(cols - 11) + " bcdef"))
      });

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("--foo-bar  " + "a".repeat(cols - 11));

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("           bcdef");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testConstructorTakingMarginsAndAddOpts() {
      int cols = Term.getCols();

      var help = new Help(4, 2);

      help.addOpts(new OptCfg[]{
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 11 - 4 - 2) + " " + "b".repeat(cols - 11 - 4 - 2) + "ccc")
        )
      });

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("    --foo-bar  " + "a".repeat(cols - 11 - 4 - 2));
      assertThat(iter.next()).isEqualTo("               " + "b".repeat(cols - 11 - 4 - 2));
      assertThat(iter.next()).isEqualTo("               " + "ccc");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithMargins_array() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithMargins(new OptCfg[]{
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 11 - 5 - 4) + " " + "b".repeat(cols - 11 - 5 - 4) + "ccc")
        )
      }, 5, 4);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("     --foo-bar  " + "a".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "b".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "ccc");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithMargins_list() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithMargins(List.of(
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 11 - 5 - 4) + " " + "b".repeat(cols - 11 - 5 - 4) + "ccc")
        )
      ), 5, 4);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("     --foo-bar  " + "a".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "b".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "ccc");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithMarginsByBothConstructorAndAddTextWithMargins() {
      int cols = Term.getCols();

      var help = new Help(4, 2);
      help.addOptsWithMargins(new OptCfg[]{
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 11 - 5 - 4) + " " + "b".repeat(cols - 11 - 5 - 4) + "ccc")
        )
      }, 1, 2);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("     --foo-bar  " + "a".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "b".repeat(cols - 11 - 5 - 4));
      assertThat(iter.next()).isEqualTo("                " + "ccc");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithIndentIfIndentIsLongerThanTitle() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithIndent(new OptCfg[]{
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 12) + " " + "b".repeat(cols - 12) + "ccc")
        )
      }, 12);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("--foo-bar   " + "a".repeat(cols - 12));
      assertThat(iter.next()).isEqualTo("            " + "b".repeat(cols - 12));
      assertThat(iter.next()).isEqualTo("            " + "ccc");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithIndentIfIndentIsShorterThanTitle() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithIndent(new OptCfg[]{
        new OptCfg(
          names("foo-bar"),
          desc("a".repeat(cols - 10) + " " + "b".repeat(10))
        )
      }, 10);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("--foo-bar");
      assertThat(iter.next()).isEqualTo("          " + "a".repeat(cols - 10));
      assertThat(iter.next()).isEqualTo("          " + "b".repeat(10));
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithIndentAndMargins_array() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithIndentAndMargins(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("a".repeat(cols)))
      }, 6, 4, 2);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("    --foo-bar");
      assertThat(iter.next()).isEqualTo("          " + "a".repeat(cols - 6 - 4 -2));
      assertThat(iter.next()).isEqualTo("          " + "a".repeat(6 + 4 + 2));
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithIndentAndMargins_list() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithIndentAndMargins(List.of(
        new OptCfg(names("foo-bar"), desc("a".repeat(cols)))
      ), 6, 4, 2);

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("    --foo-bar");
      assertThat(iter.next()).isEqualTo("          " + "a".repeat(cols - 6 - 4 -2));
      assertThat(iter.next()).isEqualTo("          " + "a".repeat(6 + 4 + 2));
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsIfOptsAreMultiple() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(
          names("foo-bar", "f"),
          hasArg(true),
          desc("a".repeat(cols - 22) + " " + "b".repeat(cols - 22) + "ccc"),
          argInHelp("<text>")
        ),
        new OptCfg(
          names("baz", "b"),
          desc("d".repeat(cols - 22) + " " + "e".repeat(cols - 22) + "fff")
        )
      });

      var iter = help.iter();

      assertThat(iter.next()).isEqualTo("--foo-bar, -f <text>  " + "a".repeat(cols - 22));
      assertThat(iter.next()).isEqualTo("                      " + "b".repeat(cols - 22));
      assertThat(iter.next()).isEqualTo("                      " + "ccc");
      assertThat(iter.next()).isEqualTo("--baz, -b             " + "d".repeat(cols - 22));
      assertThat(iter.next()).isEqualTo("                      " + "e".repeat(cols - 22));
      assertThat(iter.next()).isEqualTo("                      " + "fff");
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsIfNamesAreEmptyAndStoreKeyIsSpecified() {
      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(storeKey("foo"), desc("description")),
        new OptCfg(storeKey("bar"), names("", ""), desc("description"))
      });

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("--foo          description");

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("        --bar  description");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsIfStoreKeyIsAnyOption() {
      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(storeKey("foo"), desc("description")),
        new OptCfg(storeKey("*"), desc("any option"))
      });

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("--foo  description");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsIfFirstElementOfNamesIsAnyOption() {
      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("description")),
        new OptCfg(names("*"), desc("any option"))
      });

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("--foo-bar  description");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithIndentIfIndentIsLongerThanLineWidth() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithIndent(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("description")),
        new OptCfg(names("baz"), desc("description"))
      }, cols + 1);

      var iter = help.iter();

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsWithMarginsIfSumOfMarginsAreEqualToLineWidth() {
      int cols = Term.getCols();

      var help = new Help();
      help.addOptsWithMargins(new OptCfg[]{
        new OptCfg(names("foo-bar"), desc("description")),
        new OptCfg(names("baz"), desc("description"))
      }, cols - 1, 1);

      var iter = help.iter();

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testAddOptsIfNamesContainsEmptyStrings() {
      var help = new Help();
      help.addOpts(new OptCfg[]{
        new OptCfg(names("", "f", "foo-bar", "", ""), desc("description")),
        new OptCfg(names("b", "", "z", "baz"), desc("description"))
      });

      var iter = help.iter();

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("    -f, --foo-bar  description");

      assertThat(iter.hasNext()).isTrue();
      assertThat(iter.next()).isEqualTo("-b,     -z, --baz  description");

      assertThat(iter.hasNext()).isFalse();
      assertThat(iter.next()).isEqualTo("");
    }

    @Test
    void testHelpOfCurl() {
      // The source of the following text is the output of `curl --help` in
      // curl 7.87.0. (https://curl.se/docs/copyright.html)
      var help = new Help();
      help.addText("Usage: curl [options...] <url>");

      help.addOptsWithMargins(new OptCfg[]{
        new OptCfg(
          storeKey("data"),
          names("d", "data"),
          hasArg(true),
          desc("HTTP POST data"),
          argInHelp("<data>")),
        new OptCfg(
          storeKey("fail"),
          names("f", "fail"),
          desc("Fail fast with no output on HTTP errors")),
        new OptCfg(
          storeKey("help"),
          names("h", "help"),
          hasArg(true),
          desc("Get help for commands"),
          argInHelp("<category>")),
        new OptCfg(
          storeKey("include"),
          names("i", "include"),
          desc("Include protocol response headers in the output")),
        new OptCfg(
          storeKey("output"),
          names("o", "output"),
          hasArg(true),
          desc("Write to file instead of stdout"),
          argInHelp("<file>")),
        new OptCfg(
          storeKey("removeName"),
          names("O", "remove-name"),
          desc("Write output to a file named as the remote file")),
        new OptCfg(
          storeKey("silent"),
          names("s", "silent"),
          desc("Silent mode")),
        new OptCfg(
          storeKey("uploadFile"),
          names("T", "upload-file"),
          hasArg(true),
          desc("Transfer local FILE to destination"),
          argInHelp("<file>")),
        new OptCfg(
          storeKey("user"),
          names("u", "user"),
          hasArg(true),
          desc("Server user and password"),
          argInHelp("<user:password>")),
        new OptCfg(
          storeKey("userAgent"),
          names("A", "user-agent"),
          hasArg(true),
          desc("Send User-Agent <name> to server"),
          argInHelp("<name>")),
        new OptCfg(
          storeKey("verbose"),
          names("v", "verbose"),
          desc("Make the operation more talkative")),
        new OptCfg(
          storeKey("version"),
          names("V", "version"),
          desc("Show version number and quit")),
      }, 1, 0);

      help.addText(
        "\n" +
        "This is not the full help, this menu is stripped into categories.\n" +
        "Use \"--help category\" to get an overview of all categories.\n" +
        "For all options use the manual or \"--help all\".");
      
      help.print();
    }
  }
}
