/*
 * Copyright (C) 2024 Takayuki Sato. All Rights Reserved.
 * This program is free software under MIT License.
 * See the file LICENSE in this distribution for more details.
 */
package com.github.sttk.cliargs.annotations;

import com.github.sttk.cliargs.OptCfg;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is the annotation that is attached to fields of an option store class.
 * <p>
 * This annotation can specify values for the fields of an {@link OptCfg}
 * object: {@code names}, {@code defaults}, {@code desc} and {@code argInHelp}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Opt {

  /**
   * Gets the option name, aliases and default values.
   *
   * @return  The option name, aliases and default values.
   */
  String cfg() default "";

  /**
   * Gets the description of the option.
   *
   * @return  The description of the option.
   */
  String desc() default "";

  /**
   * Gets the display of the option argument in a help text.
   *
   * @return  The display of the option argument in a help text.
   */
  String arg() default "";
}
