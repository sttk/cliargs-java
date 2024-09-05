/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Base.isEmpty;
import static java.util.Collections.emptyList;

import com.github.sttk.linebreak.LineIter;
import com.github.sttk.linebreak.Term;
import com.github.sttk.linebreak.Unicode;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Is the class to print a help text with an {@link OptCfg} array.
 * <p>
 * This class can output with margins and indentation for each option and text block.
 */
public class Help {

  private int marginLeft;
  private int marginRight;
  private List<Block> blocks;

  /**
   * Constructs an instance of this class with 0 margins.
   */
  public Help() {
    this.marginLeft = 0;
    this.marginRight = 0;
    this.blocks = new ArrayList<>(2);
  }

  /**
   * Constructs an instance of this class with left and right margins.
   *
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public Help(int marginLeft, int marginRight) {
    this.marginLeft = marginLeft;
    this.marginRight = marginRight;
    this.blocks = new ArrayList<>(2);
  }

  /**
   * Adds a text for a help text block to this help instance.
   *
   * The indent width of this help text block is set to <i>auto indentation</i>.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param text  A text for a help text block.
   */
  public void addText(String text) {
    addTextWithIndentAndMargins(text, 0, 0, 0);
  }

  /**
   * Adds a text and an indent width for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param text  A text for a help text block.
   * @param indent  An indent size.
   */
  public void addTextWithIndent(String text, int indent) {
    addTextWithIndentAndMargins(text, indent, 0, 0);
  }

  /**
   * Adds a text and margins for a help text block to this help instance.
   *
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   * The indent width of this help text block is set to *auto indentation*.
   *
   * @param text  A text for a help text block.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextWithMargins(String text, int marginLeft, int marginRight) {
    addTextWithIndentAndMargins(text, 0, marginLeft, marginRight);
  }

  /**
   * Adds a text and an indent width and margins for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   *
   * @param text  A text for a help text block.
   * @param indent  An indent size.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextWithIndentAndMargins(
    String text, int indent, int marginLeft, int marginRight
  ) {
    marginLeft = this.marginLeft + marginLeft;
    marginRight = this.marginRight + marginRight;
    var body = new BlockBody(0, text);
    this.blocks.add(new Block(indent, marginLeft, marginRight, body));
  }

  /**
   * Adds texts for a help text block to this help instance.
   *
   * The indent width of this help text block is set to *auto indentation*.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param texts  Texts for a help text block.
   */
  public void addTexts(String ...texts) {
    addTextsWithIndentAndMargins(texts, 0, 0, 0);
  }

  /**
   * Adds texts for a help text block to this help instance.
   *
   * The indent width of this help text block is set to *auto indentation*.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param texts  Texts for a help text block.
   */
  public void addTexts(List<String> texts) {
    addTextsWithIndentAndMargins(texts, 0, 0, 0);
  }

  /**
   * Adds texts and an indent width for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of a help text generated by this instance equals them which specified at the
   * `new` function.
   *
   * @param texts  Texts for a help text block.
   * @param indent  An indent size.
   */
  public void addTextsWithIndent(String[] texts, int indent) {
    addTextsWithIndentAndMargins(texts, indent, 0, 0);
  }

  /**
   * Adds texts and an indent width for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of a help text generated by this instance equals them which specified at the
   * `new` function.
   *
   * @param texts  Texts for a help text block.
   * @param indent  An indent size.
   */
  public void addTextsWithIndent(List<String> texts, int indent) {
    addTextsWithIndentAndMargins(texts, indent, 0, 0);
  }

  /**
   * Adds texts and an indent width and margins for a help text block to this help instance.
   *
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   * The indent width of this help text block is set to *auto indentation*.
   *
   * @param texts  Texts for a help text block.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextsWithMargins(String[] texts, int marginLeft, int marginRight) {
    addTextsWithIndentAndMargins(texts, 0, marginLeft, marginRight);
  }

  /**
   * Adds texts and an indent width and margins for a help text block to this help instance.
   *
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   * The indent width of this help text block is set to *auto indentation*.
   *
   * @param texts  Texts for a help text block.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextsWithMargins(List<String> texts, int marginLeft, int marginRight) {
    addTextsWithIndentAndMargins(texts, 0, marginLeft, marginRight);
  }

  /**
   * Adds texts and an indent width and margins for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   *
   * @param texts  Texts for a help text block.
   * @param indent  An indent size.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextsWithIndentAndMargins(
    String[] texts, int indent, int marginLeft, int marginRight
  ) {
    marginLeft = this.marginLeft + marginLeft;
    marginRight = this.marginRight + marginRight;
    var bodies = new ArrayList<BlockBody>();
    for (var s : texts) {
      bodies.add(new BlockBody(0, s));
    }
    this.blocks.add(new Block(indent, marginLeft, marginRight, bodies));
  }

  /**
   * Adds texts and an indent width and margins for a help text block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   *
   * @param texts  Texts for a help text block.
   * @param indent  An indent size.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addTextsWithIndentAndMargins(
    List<String> texts, int indent, int marginLeft, int marginRight
  ) {
    marginLeft = this.marginLeft + marginLeft;
    marginRight = this.marginRight + marginRight;
    var bodies = new ArrayList<BlockBody>();
    for (var s : texts) {
      bodies.add(new BlockBody(0, s));
    }
    this.blocks.add(new Block(indent, marginLeft, marginRight, bodies));
  }

  /**
   * Adds {@link OptCfg}(s) for a help option block to this help instance.
   *
   * The indent width of this help text block is set to *auto indentation*.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param cfgs  An array of {@link OptCfg}
   */
  public void addOpts(OptCfg[] cfgs) {
    addOptsWithIndentAndMargins(cfgs, 0, 0, 0);
  }

  /**
   * Adds {@link OptCfg}(s) for a help option block to this help instance.
   *
   * The indent width of this help text block is set to *auto indentation*.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param cfgs  A list of {@link OptCfg}.
   */
  public void addOpts(List<OptCfg> cfgs) {
    addOptsWithIndentAndMargins(cfgs, 0, 0, 0);
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width for a help option block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param cfgs  An array of {@link OptCfg}.
   * @param indent  An indent size.
   */
  public void addOptsWithIndent(OptCfg[] cfgs, int indent) {
    addOptsWithIndentAndMargins(cfgs, indent, 0, 0);
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width for a help option block to this help instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of a help text generated by this instance equals them which specified at a
   * constructor.
   *
   * @param cfgs  A list of {@link OptCfg}.
   * @param indent  An indent size.
   */
  public void addOptsWithIndent(List<OptCfg> cfgs, int indent) {
    addOptsWithIndentAndMargins(cfgs, indent, 0, 0);
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width and margins for a help option block to this help
   * instance.
   *
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   * The indent width of this help text block is set to *auto indentation*.
   *
   * @param cfgs  An array of {@link OptCfg}
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addOptsWithMargins(OptCfg[] cfgs, int marginLeft, int marginRight) {
    addOptsWithIndentAndMargins(cfgs, 0, marginLeft, marginRight);
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width and margins for a help option block to this help
   * instance.
   *
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   * The indent width of this help text block is set to *auto indentation*.
   *
   * @param cfgs  A list of {@link OptCfg}.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addOptsWithMargins(List<OptCfg> cfgs, int marginLeft, int marginRight) {
    addOptsWithIndentAndMargins(cfgs, 0, marginLeft, marginRight);
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width and margins for a help option block to this help
   * instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   *
   * @param cfgs  An array of {@link OptCfg}
   * @param indent  An indent size.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addOptsWithIndentAndMargins(
    OptCfg[] cfgs, int indent, int marginLeft, int marginRight
  ) {
    marginLeft = this.marginLeft + marginLeft;
    marginRight = this.marginRight + marginRight;
    int[] indentBuf = new int[]{indent};
    var bodies = createOptsHelp(List.of(cfgs), indentBuf);
    this.blocks.add(new Block(indentBuf[0], marginLeft, marginRight, bodies));
  }

  /**
   * Adds {@link OptCfg}(s) and an indent width and margins for a help option block to this help
   * instance.
   *
   * The indent width is the number of spaces inserted at the beginning of each line from the
   * second line.
   * The margins of this help text block generated by this instance equals the sum of them
   * specified as parameters of this method and them which specified at a constructor.
   *
   * @param cfgs  A list of {@link OptCfg}.
   * @param indent  An indent size.
   * @param marginLeft  A left margin.
   * @param marginRight  A right margin.
   */
  public void addOptsWithIndentAndMargins(
    List<OptCfg> cfgs, int indent, int marginLeft, int marginRight
  ) {
    marginLeft = this.marginLeft + marginLeft;
    marginRight = this.marginRight + marginRight;
    int[] indentBuf = new int[]{indent};
    var bodies = createOptsHelp(cfgs, indentBuf);
    this.blocks.add(new Block(indentBuf[0], marginLeft, marginRight, bodies));
  }

  /**
   * Creates a {@link HelpIter} instance which is an iterator that outputs a help text line by
   * line.
   *
   * @return  An iterator of each line of a help text.
   */
  public Iterator<String> iter() {
    if (this.blocks.isEmpty()) {
      return new HelpIter(0, this.blocks);
    }
    int lineWidth = Term.getCols();
    return new HelpIter(lineWidth, this.blocks);
  }

  /**
   * Outputs a help text to the standard output.
   */
  public void print() {
    var iter = this.iter();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }

  static List<BlockBody> createOptsHelp(List<OptCfg> cfgs, int[] indentBox) {
    var ret = new ArrayList<BlockBody>(cfgs.size());
    final String ANY_OPT = "*";

    int indent = indentBox[0];

    if (indent > 0) {
      for (var cfg : cfgs) {
        var storeKey = cfg.storeKey;
        if (isEmpty(storeKey)) {
          storeKey = cfg.names.stream().filter(nm -> !isEmpty(nm)).findFirst().orElse("");
        }

        if (isEmpty(storeKey)) {
          continue;
        }

        if (storeKey == ANY_OPT) {
          continue;
        }

        var body = makeOptTitle(cfg);

        int width = body.firstIndent + Unicode.getTextWidth(body.text);

        if (! isEmpty(cfg.desc)) {
          if (width + 2 > indent) {
            body.text += "\n" + " ".repeat(indent) + cfg.desc;
          } else {
            body.text += " ".repeat(indent - width) + cfg.desc;
          }
        }

        ret.add(body);
      }
    } else {
      var widths = new ArrayList<Integer>(ret.size());
      indent = 0;

      for (var cfg : cfgs) {
        var storeKey = cfg.storeKey;
        if (isEmpty(storeKey)) {
          storeKey = cfg.names.stream().filter(nm -> !isEmpty(nm)).findFirst().orElse("");
        }

        if (isEmpty(storeKey)) {
          continue;
        }

        if (storeKey == ANY_OPT) {
          continue;
        }

        var body = makeOptTitle(cfg);

        int width = body.firstIndent + Unicode.getTextWidth(body.text);
        if (indent < width) {
          indent = width;
        }

        ret.add(body);
        widths.add(width);
      }

      indent += 2;
      indentBox[0] = indent;

      int i = 0;
      for (var cfg : cfgs) {
        var storeKey = cfg.storeKey;
        if (isEmpty(storeKey)) {
          storeKey = cfg.names.stream().filter(nm -> !isEmpty(nm)).findFirst().orElse("");
        }

        if (isEmpty(storeKey)) {
          continue;
        }

        if (storeKey == ANY_OPT) {
          continue;
        }

        if (! isEmpty(cfg.desc)) {
          ret.get(i).text += " ".repeat(indent - widths.get(i)) + cfg.desc;
        }

        i += 1;
      }
    }

    return ret;
  }

  static BlockBody makeOptTitle(OptCfg cfg) {
    int headSpaces = 0;
    int lastSpaces = 0;
    String title = "";
    boolean useStoreKey = true;

    int n = cfg.names.size();

    for (int i = 0; i < n; i++) {
      String name = cfg.names.get(i);

      switch (name.length()) {
      case 0:
        if (isEmpty(title)) {
          headSpaces += 4;
        } else if (i != n - 1) {
          lastSpaces += 4;
        } else {
          lastSpaces += 2;
        }
        break;
      case 1:
        if (lastSpaces > 0) {
          title += "," + " ".repeat(lastSpaces - 1);
        }
        lastSpaces = 0;
        title += "-" + name;
        if (i != n - 1) {
          lastSpaces += 2;
        }
        useStoreKey = false;
        break;
      default:
        if (lastSpaces > 0) {
          title += "," + " ".repeat(lastSpaces - 1);
        }
        lastSpaces = 0;
        title += "--" + name;
        if (i != n - 1) {
          lastSpaces += 2;
        }
        useStoreKey = false;
        break;
      }
    }

    if (useStoreKey) {
      switch (cfg.storeKey.length()) {
      case 0:
        break;
      case 1:
        title += "-" + cfg.storeKey;
        break;
      default:
        title += "--" + cfg.storeKey;
        break;
      }
    }

    if (! isEmpty(cfg.argInHelp)) {
      title += " " + cfg.argInHelp;
    }

    return new BlockBody(headSpaces, title);
  }
}

class Block {
  final int indent;
  final int marginLeft;
  final int marginRight;
  final List<BlockBody> bodies;

  Block(int indent, int marginLeft, int marginRight, BlockBody body) {
    this.indent = indent;
    this.marginLeft = marginLeft;
    this.marginRight = marginRight;
    this.bodies = new ArrayList<>();
    this.bodies.add(body);
  }

  Block(int indent, int marginLeft, int marginRight, List<BlockBody> bodies) {
    this.indent = indent;
    this.marginLeft = marginLeft;
    this.marginRight = marginRight;
    this.bodies = new ArrayList<>(bodies);
  }
}

class BlockBody {
  final int firstIndent;
  String text;

  BlockBody(int firstIndent, String text) {
    this.firstIndent = firstIndent;
    this.text = text;
  }
}

class HelpIter implements Iterator<String> {
  int lineWidth;
  List<Block> blocks;
  BlockIter blockIter;
  byte hasNext;  // 0:to be checked, 1:has next, 2:no next

  HelpIter(int lineWidth, List<Block> blocks) {
    this.lineWidth = lineWidth;
    this.blocks = blocks;
    if (blocks.isEmpty()) {
      this.blockIter = new BlockIter();
      this.hasNext = 2;
    } else {
      this.blockIter = new BlockIter(this.blocks.get(0), this.lineWidth);
      this.hasNext = 0;
    }
  }

  @Override
  public boolean hasNext() {
    switch (this.hasNext) {
    case 0:
      if (this.blockIter.hasNext()) {
        this.hasNext = 1;
        return true;
      }
      if (this.blocks.size() <= 1) {
        this.hasNext = 2;
        return false;
      }
      this.blocks.remove(0);
      this.blockIter = new BlockIter(this.blocks.get(0), this.lineWidth);
      this.hasNext = 1;
      return true;
    case 1:
      return true;
    default:
      return false;
    }
  }

  @Override
  public String next() {
    if (hasNext()) {
      this.hasNext = 0;
      return this.blockIter.next();
    } else {
      return "";
    }
  }
}

class BlockIter implements Iterator<String> {
  List<BlockBody> bodies;
  String indent;
  String margin;
  LineIter lineIter;
  byte hasNext;  // 0:to be checked, 1:has next, 2:no next

  BlockIter() {
    this.bodies = new ArrayList<>();
    this.indent = "";
    this.margin = "";
    this.lineIter = new LineIter("", 0);
    this.hasNext = 0;
  }

  BlockIter(Block block, int lineWidth) {
    int printWidth = lineWidth - block.marginLeft - block.marginRight;
    if (block.bodies.isEmpty() || printWidth <= block.indent) {
      this.bodies = emptyList();
      this.indent = "";
      this.margin = "";
      this.lineIter = new LineIter("", 0);
      this.hasNext = 2;
    } else {
      var body = block.bodies.get(0);
      var lineIter = new LineIter(body.text, printWidth);
      lineIter.setIndent(" ".repeat(body.firstIndent));
      this.bodies = block.bodies;
      this.indent = " ".repeat(block.indent);
      this.margin = " ".repeat(block.marginLeft);
      this.lineIter = lineIter;
      this.hasNext = 0;
    }
  }

  @Override
  public boolean hasNext() {
    switch (this.hasNext) {
    case 0:
      if (this.lineIter.hasNext()) {
        this.hasNext = 1;
        return true;
      }
      if (!this.bodies.isEmpty()) {
        this.bodies.remove(0);
      }
      if (this.bodies.size() < 1) {
        this.hasNext = 2;
        return false;
      }
      var body = this.bodies.get(0);
      this.lineIter.init(body.text);
      this.lineIter.setIndent(" ".repeat(body.firstIndent));
      this.hasNext = 1;
      return true;
    case 1:
      return true;
    default:
      return false;
    }
  }

  @Override
  public String next() {
    if (hasNext()) {
      this.hasNext = 0;
      var text = this.margin + this.lineIter.next();
      this.lineIter.setIndent(this.indent);
      return text;
    } else {
      return "";
    }
  }
}
