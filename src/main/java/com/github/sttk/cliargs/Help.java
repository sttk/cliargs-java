/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs;

import static com.github.sttk.cliargs.Util.isEmpty;
import static java.util.Collections.emptyList;

import com.github.sttk.linebreak.LineIter;
import com.github.sttk.linebreak.Unicode;
import com.github.sttk.linebreak.Term;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Is the class to print a help text with {@link OptCfg} objects.
 * <p>
 * This class can output with margins and indentation for each option and text
 * block.
 */
public class Help {

  private int marginLeft;
  private int marginRight;
  private List<Block> blocks;

  /**
   * Is the constructor which create a new instance of {@link Help} class.
   * <p>
   * This constructor can takes variadic parameters of which the first is
   * left margin, and the second is right margin.
   *
   * @param wrapOpts  The variadic parameter of the left margin width and the
   *     right margin width.
   */
  public Help(int ...wrapOpts) {
    if (wrapOpts.length > 0) {
      this.marginLeft = wrapOpts[0];
    }
    if (wrapOpts.length > 1) {
      this.marginRight = wrapOpts[1];
    }
    this.blocks = new ArrayList<>();
  }

  /**
   * Creates an iterator to output the help text line by line.
   *
   * @return  An iterator for lines of the help text.
   */
  public Iterator<String> iter() {
    int lineWidth = Term.getCols();
    return new HelpIter(this.blocks, lineWidth);
  }

  /**
   * Registers a text as a text block.
   * <p>
   * This method can take the variadic parameters of which the first is indent
   * width, te second is left margin width, and the third is right margin
   * width.
   * These margins are added to the margins specified by the constructor.
   *
   * @param text  A text.
   * @param wrapOpts  The variadic parameter of the indent width, the left
   *     margin width, and the right margin width.
   */
  public void addText(String text, int ...wrapOpts) {
    if (text == null) {
      text = "";
    }

    var b = new Block();
    b.marginLeft = this.marginLeft;
    b.marginRight = this.marginRight;

    if (wrapOpts.length > 0) {
      b.indent = wrapOpts[0];
    }
    if (wrapOpts.length > 1) {
      b.marginLeft += wrapOpts[1];
    }
    if (wrapOpts.length > 2) {
      b.marginRight += wrapOpts[2];
    }
    b.texts = new ArrayList<>(1);
    b.texts.add(text);
    this.blocks.add(b);
  }

  /**
   * Registers multiple texts as a text block.
   * <p>
   * This method can take the variadic parameters of which the first is indent
   * width, te second is left margin width, and the third is right margin
   * width.
   * These margins are added to the margins specified by the constructor.
   *
   * @param texts  multiple texts.
   * @param wrapOpts  The variadic parameter of the indent width, the left
   *     margin width, and the right margin width.
   */
  public void addTexts(List<String> texts, int ...wrapOpts) {
    if (texts == null) {
      texts = emptyList();
    }

    var b = new Block();
    b.marginLeft = this.marginLeft;
    b.marginRight = this.marginRight;

    if (wrapOpts.length > 0) {
      b.indent = wrapOpts[0];
    }
    if (wrapOpts.length > 1) {
      b.marginLeft += wrapOpts[1];
    }
    if (wrapOpts.length > 2) {
      b.marginRight += wrapOpts[2];
    }
    b.texts = new ArrayList<>(texts);
    this.blocks.add(b);
  }

  /**
   * Registers an {@link OptCfg} array and creates the option part in the help
   * text as a text block.
   * <p>
   * This method can take the variadic parameters of which the first is indent
   * width, te second is left margin width, and the third is right margin
   * width.
   * These margins are added to the margins specified by the constructor.
   *
   * @param optCfgs  An {@link OptCfg} array.
   * @param wrapOpts  The variadic parameter of the indent width, the left
   *     margin width, and the right margin width.
   */
  public void addOpts(OptCfg[] optCfgs, int ...wrapOpts) {
    if (optCfgs == null) {
      optCfgs = new OptCfg[0];
    }

    var b = new Block();
    b.marginLeft = this.marginLeft;
    b.marginRight = this.marginRight;
    if (wrapOpts.length > 0) {
      b.indent = wrapOpts[0];
    }
    if (wrapOpts.length > 1) {
      b.marginLeft += wrapOpts[1];
    }
    if (wrapOpts.length > 2) {
      b.marginRight += wrapOpts[2];
    }

    var texts = new ArrayList<String>(optCfgs.length);

    final var anyOption = "*";

    if (b.indent > 0) {
      int i = 0;
      for (var cfg : optCfgs) {
        var storeKey = cfg.storeKey;
        if (Objects.equals(storeKey, anyOption)) {
          continue;
        }

        var text = makeOptTitle(cfg);
        var width = Unicode.getTextWidth(text);
        if (width + 2 > b.indent) {
          text += "\n" + " ".repeat(b.indent) + cfg.desc;
        } else {
          text += " ".repeat(b.indent - width) + cfg.desc;
        }
        texts.add(text);
        i++;
      }
    } else {
      var widths = new int[optCfgs.length];
      int indent = 0;

      int i = 0;
      for (var cfg : optCfgs) {
        var storeKey = cfg.storeKey;
        if (storeKey == anyOption) {
          continue;
        }

        String text = makeOptTitle(cfg);
        widths[i] = Unicode.getTextWidth(text);
        if (indent < widths[i]) {
          indent = widths[i];
        }
        texts.add(text);
        i++;
      }
      indent += 2;

      b.indent = indent;

      i = 0;
      for (var cfg : optCfgs) {
        var storeKey = cfg.storeKey;
        if (storeKey == anyOption) {
          continue;
        }

        var text = texts.get(i) + " ".repeat(indent - widths[i]) + cfg.desc;
        texts.set(i, text);
        i++;
      }
    }

    b.texts = new ArrayList<>(texts);
    this.blocks.add(b);
  }

  private String makeOptTitle(OptCfg cfg) {
    var title = "";
    for (var name : cfg.names) {
      title += switch (name.length()) {
        case 0 -> "";
        case 1 -> ", -" + name;
        default -> ", --" + name;
      };
    }

    if (cfg.hasArg && ! isEmpty(cfg.argInHelp)) {
      title += " " + cfg.argInHelp;
    }

    return title.substring(2);
  }

  /**
   * Prints a help text to standard output.
   */
  public void print() {
    var iter = iter();

    while (iter.hasNext()) {
      var line = iter.next();
      System.out.println(line);
    }
  }
}

class Block {
  int indent;
  int marginLeft;
  int marginRight;
  List<String> texts;
}

class HelpIter implements Iterator<String> {
  Iterator<Block> blockIter;
  int lineWidth;

  Iterator<String> textIter;
  int printWidth;
  String indent;
  String margin;

  LineIter lineIter;

  HelpIter(List<Block> blocks, int lineWidth) {
    if (blocks.isEmpty()) {
      var b = new Block();
      b.texts = emptyList();
      blocks.add(b); // for outputing at least one line.
    }
    this.blockIter = new ArrayList<>(blocks).iterator();
    this.textIter = null;
    this.lineIter = null;
    this.lineWidth = lineWidth;
  }

  @Override
  public boolean hasNext() {
    if (this.lineIter != null && this.lineIter.hasNext()) {
      return true;
    }
    if (this.textIter != null && this.textIter.hasNext()) {
      return true;
    }
    return this.blockIter.hasNext();
  }

  @Override
  public String next() {
    if (this.lineIter != null && this.lineIter.hasNext()) {
      this.lineIter.setIndent(this.indent);
      var line = this.lineIter.next();
      return isEmpty(line) ? "" : (this.margin + line);
    }

    if (this.textIter != null && this.textIter.hasNext()) {
      var text = this.textIter.next();
      this.lineIter = new LineIter(text, this.printWidth);
      if (this.lineIter.hasNext()) {
        var line = this.lineIter.next();
        return isEmpty(line) ? "" : (this.margin + line);
      }
    }

    if (this.blockIter.hasNext()) {
      var b = this.blockIter.next();

      this.printWidth = this.lineWidth - b.marginLeft - b.marginRight;
      this.indent = " ".repeat(b.indent);
      this.margin = " ".repeat(b.marginLeft);

      if (this.printWidth <= b.indent) {
        this.textIter = null;
        return "";
      }
      this.textIter = b.texts.iterator();

      if (this.textIter.hasNext()) {
        var text = this.textIter.next();
        this.lineIter = new LineIter(text, this.printWidth);
        if (this.lineIter.hasNext()) {
          var line = this.lineIter.next();
          return isEmpty(line) ? "" : (this.margin + line);
        }
      }
    }

    return "";
  }
}
