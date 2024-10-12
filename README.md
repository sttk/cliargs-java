# [cliargs-java][repo-url] [![Maven Central][mvn-img]][mvn-url] [![GitHub.io][io-img]][io-url] [![CI Status][ci-img]][ci-url] [![MIT License][mit-img]][mit-url]

A library to parse command line arguments for Java application.

This library provides the following functionalities:

- Supports [POSIX][posix-args] & [GNU][gnu-args] like short and long options.
    - This library supports `--` option.
    - This library doesn't support numeric short option.
    - This library supports not `-ofoo` but `-o=foo` as an alternative to `-o foo` for short option.
- Supports parsing with option configurations.
- Supports parsing with an object which stores option values and has annotations of fields.
- Is able to parse command line arguments including sub commands.
- Generates help text from option configurations.

## Install

This package can be installed from [Maven Central Repository][mvn-url].

The examples of declaring that repository and the dependency on this package in Maven `pom.xml` and Gradle `build.gradle` are as follows:

### for Maven

```xml
  <dependencies>
    <dependency>
      <groupId>io.github.sttk</groupId>
      <artifactId>cliargs</artifactId>
      <version>0.2.0</version>
    </dependency>
  </dependencies>
```

### for Gradle

```
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.github.sttk:cliargs:0.2.0'
}
```

## Usage

### Parse without configurations

This library provides the method `Cmd#parse` which parses command line arguments without configurations.
This method automatically divides command line arguments to command options and command arguments.

Command line arguments starting with `-` or `--` are command options, and others are command arguments.
If you want to specify a value to an option, follows `"="` and the value after the option, like `foo=123`.

All command line arguments after `--` are command arguments, even they starts
with `-` or `--`.

```java
  var osArgs = new String[]{"--foo-bar", "hoge", "--baz=1", "-z=2", "-xyz=3", "fuga"};
  var cmd = new Cmd("path/to/app", osArgs);
  try {
    cmd.parse();
  } catch (InvalidOption e) {
    ...
  }

  cmd.name();             // "app"
  cmd.args();             // ["hoge", "fuga"]
  cmd.hasOpt("foo-bar");  // true
  cmd.hasOpt("baz");      // true
  cmd.hasOpt("x");        // true
  cmd.hasOpt("y");        // true
  cmd.hasOpt("z");        // true
  cmd.optArg("foo-bar");  // null
  cmd.optArg("baz");      // "1"
  cmd.optArg("x");        // null
  cmd.optArg("y");        // null
  cmd.optArg("z");        // "2"
  cmd.optArgs("foo-bar"); // []
  cmd.optArgs("baz");     // [1]
  cmd.optArgs("x");       // []
  cmd.optArgs("y");       // []
  cmd.optArgs("z");       // ["2", "3"]
```

### Parse with configurations

This library provides the method `Cmd#parseWith` which parses command line arguments with option configurations.
This method takes an `OptCfg` array as the argument, and divides command line arguments to command options and command arguments with this configurations.

And option configuration has fields: `storeKey`, `names`, `hasArg`, `isArray`, `type`, `defaults`, `desc`, `argInHelp`, and `validator`.

`storeKey` field is to specify the key name to store the option value in the option map.
If this field is not specified the first element of `names` field is set instead.

`names` field is a string array and is to specify the option names, that are long options or short options.
The order of elements in this field is used in a help text.
If you want to prioritize the output of short option name first in the help text, like `-f, --foo-bar`, but use the long option name as the key in the option map, write `storekey` and `names` fields as follows:
`OptCfg(field("foo-bar", names("f", "foo-bar"))`.

`hasArg` field is to specify the option requires one or more values.

`isArray` field is to specify the option can have multiple values.

`defaults` field is to specify an array which is used as default one or more values if the
option is not specified in command line arguments.

`desc` field is to specify a description of the option for help text.

`argInHelp` field is to specify a text which is output after option name and aliases as an option value in help text.

`validator` field is to specify an instance of a functional interface which validates an option argument string as a value of the desired type.

```java
import com.github.sttk.cliargs.OptCfg.NamedParam.*;
...
  var osArgs = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "fuga"};
  var cmd = new Cmd("path/to/app", osArgs);

  var optCfgs = new OptCfg[] {
    new OptCfg(
      storeKey("FooBar"),
      names("foo-bar"),
      desc("This is description of foo-bar.")
    ),
    new OptCfg(
      names("baz", "z"),
      hasArg(true),
      isArray(true),
      type(Integer.class),
      defaults(9, 8),
      desc("This is description of baz."),
      argInHelp("<text>")
    ),
    new OptCfg(
      names("*"),
      desc("(Any options are accepted)")
    )
  };

  try {
    cmd.parseWith(optCfgs);
  } catch (InvalidOption e) {
    ...
  }

  cmd.getName();             // "app"
  cmd.getArgs();             // ["hoge", "fuga"]
  cmd.hasOpt("FooBar");      // true
  cmd.hasOpt("baz");         // true
  cmd.hasOpt("x");           // true, due to "*" config
  cmd.getOptArg("FooBar");   // null
  cmd.getOptArg("baz");      // 1
  cmd.getOptArg("x");        // null
  cmd.getOptArgs("FooBar");  // []
  cmd.getOptArgs("baz");     // [1, 2]
  cmd.getOptArgs("x");       // []
```

This library provides `Help` class which generates a help text from an `OptCfg` array.
The following help text is generated from the above `optCfgs`.

```java
  var help = new Help();
  help.addText("This is the usage description.");
  help.addOptsWithMargins(optCfgs, 2, 0);
  help.print();

  // (stdout)
  // This is the usage description.
  //   --foo-bar, -f     This is description of foo-bar.
  //   --baz, -z <text>  This is description of baz.
```

### Parse for an option store with `@Opt` annoatation

This library provides the method `Cmd#parseFor` which takes an option store object as the argument, and puts option values by parsing command line arguments to it.
The `@Opt` annotations can be attached to the fields of the option store for their option
configurations.

This `@Opt` annotations can have the attributes: `cfg`, `desc`, and `arg`.
`cfg` can be specified the option name, aliases and default value(s).
The format of the `cfg` attribute is as follows:

```java
  @Opt(cfg="name")                   // only name
  @Opt(cfg="name,alias1,alias2")     // with two aliases
  @Opt(cfg="name=value")             // with a default value
  @Opt(cfg="name=[value1,value2]")   // with default values for array
  @Opt(cfg="name=:[value1:value2]")  // with default values and separator is ':'
```

`desc` is what to specify a option description.
And `arg` is what to specify a text for an option argument value in a help text.

```java
  var osArgs = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "fuga"};
  var cmd = new Cmd("path/to/app", osArgs);

  class Options {
    @Opt(cfg="foo-bar", desc="This is description of foo-bar.")
    boolean fooBar;

    @Opt(cfg="baz,z=[9,8,7]" desc="This is description of baz."), arg="<num>")
    int[] baz;

    @Opt(cfg="qux,x=", desc="This is description of qux.")
    boolean qux;
  }

  var options = new Options();

  try {
    cmd.parseFor(options);
  } catch (InvalidOption | FailToSetOptionStoreField e) {
    ...
  }

  var optCfgs = cmd.optCfgs();

  cmd.getName();             // "app"
  cmd.getArgs();             // ["hoge", "fuga"]
  cmd.hasOpt("FooBar");      // true
  cmd.hasOpt("Baz");         // true
  cmd.hasOpt("Qux");         // true
  cmd.getOptArg("FooBar");   // null
  cmd.getOptArg("Baz");      // 1
  cmd.getOptArg("Qux");      // null
  cmd.getOptArgs("FooBar");  // []
  cmd.getOptArgs("Baz");     // [1, 2]
  cmd.getOptArgs("Qux");     // []

  options.fooBar;   // true
  options.baz;      // [1, 2]
  options.qux;      // true

  optCfgs;  // OptCfg[] {
            //   OptCfg{
            //     storeKey: "fooBar"
            //     names: ["foo-bar"]
            //     hasArg: false
            //     isArray: false,
            //     type: null
            //     defaults: []
            //     desc: "This is description of foo-bar."
            //     argInHelp: ""
            //   },
            //   OptCfg{
            //     storeKey: "baz"
            //     names: ["baz", "z"]
            //     hasArg: true
            //     isArray: true,
            //     type: int.class
            //     defaults: [9, 8, 7]
            //     desc: "This is description of baz."
            //     argInHelp: "<num>"
            //   },
            //   OptCfg{
            //     storeKey: "qux"
            //     names: ["qux", "x"]
            //     hasArg: false
            //     isArray: false,
            //     type: null
            //     defaults: []
            //     desc: "This is description of qux."
            //     argInHelp: ""
            //   },
            // }
```

The following help text is generated from the above `optCfgs`.

```java
  var help = new Help();
  help.addText("This is the usage description.")
  help.addOptsWithIndentAndMargins(optCfgs, 12, 1, 0);
  help.print();

  // (stdout)
  // This is the usage description.
  //  --foo-bar   This is description of foo-bar.
  //  --baz, -z <num>
  //              This is description of baz.
  //  --qux       This is description of qux.
```

### Parse command line arguments including sub commands

This library provides the methods `Cmd#parseUntilSubCmd`, `Cmd#parseUntilSubCmdWith`, `Cmd#parseUntilSubCmdFor` that parses command line arguments until a sub command is found.
The return of those methods is an `Optional<Cmd>` object.
If no sub command is found, the returned object is empty.

```java
  var osArgs = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "abcd"};

  var cmd = new Cmd("path/to/app", osArgs);

  var optional = cmd.parseUntilSubCmdWith(topOptCfgs);
  
  if (optional.isEmpty()) {
    ...  // no sub command
  }
  var subCmd = optional.get();

  switch subCmd.name() {
  case "hoge":
    subCmd.parseWith(hogeOptCfgs);
    ...
    break;
  case "fuga":
    subCmd.parseWith(fugaOptCfgs);
    ...
    break;
  }
```

And the help text can be generated as follows:

```java
  var help = new Help();
  help.addText("This is the usage of this command.");
  help.addText("\nOPTIONS.");
  help.addOptsWithIndentAndMargins(topOptCfgs, 12, 2, 0);
  help.addText("\nSUB COMMANDS:");
  help.addText("hoge");
  help.addOptsWithIndentAndMargins(hogeOptCfgs, 12, 2, 0);
  help.addText("\nfuga");
  help.addOptsWithIndentAndMargins(fugaOptCfgs, 12, 2, 0);
  help.print();

  // (stdout)
  // This is the usage of this command.
  //
  // OPTIONS:
  //   --foo-bar  The description of foo-bar option.
  //   ...
  //
  // SUB COMMANDS:
  // hoge
  //   -z, --baz  The description of baz option.
  //   -x         The description of x option.
  //   ...
  //
  // fuga
  //   ...
```

## Native build

This library supports native build with GraalVM.
However, since it utilizes reflection for the option store object passed to `Cmd#parseFor`, the reflection configurations for the class of this object need to be specified in `reflect-config.json`. The configuration are as follows:

```json
[
  {
    "name":"pkg.path.to.OptionStore",
    "allDeclaredFields":true
  }
]
```

See the following pages to setup native build environment on Linux/macOS or Windows.
- [Setup native build environment on Linux/macOS](https://www.graalvm.org/latest/reference-manual/native-image/)
- [Setup native build environment on Windows](https://www.graalvm.org/latest/docs/getting-started/windows/#prerequisites-for-native-image-on-windows)

And see the following pages to build native image with Maven or Gradle.
- [Native image building with Maven plugin](https://graalvm.github.io/native-build-tools/latest/maven-plugin.html)
- [Native image building with Gradle plugin](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)


## Supporting JDK versions

This framework supports JDK 21 or later.

### Actually checked JDK versions:

- GraalVM 21.0.4+8.1 (build 21.0.4+8-LTS-jvmci-23.1-b41)
- GraalVM 22.0.2+9.1 (build 22.0.2+9-jvmci-b01)
- GraalVM 23+37.1 (build 23+37-jvmci-b01)

## License

Copyright (C) 2023-2024 Takayuki Sato

This program is free software under MIT License.<br>
See the file LICENSE in this distribution for more details.


[repo-url]: https://github.com/sttk/cliargs-java
[mvn-img]: https://img.shields.io/badge/maven_central-0.2.0-276bdd.svg
[mvn-url]: https://central.sonatype.com/artifact/io.github.sttk/cliargs/0.2.0
[io-img]: https://img.shields.io/badge/github.io-Javadoc-4d7a97.svg
[io-url]: https://sttk.github.io/cliargs-java/
[ci-img]: https://github.com/sttk/cliargs-java/actions/workflows/java-ci.yml/badge.svg?branch=main
[ci-url]: https://github.com/sttk/cliargs-java/actions
[mit-img]: https://img.shields.io/badge/license-MIT-green.svg
[mit-url]: https://opensource.org/licenses/MIT

[posix-args]: https://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html#Argument-Syntax
[gnu-args]: https://www.gnu.org/prep/standards/html_node/Command_002dLine-Interfaces.html
