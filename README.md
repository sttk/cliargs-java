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
      <version>0.1.0</version>
    </dependency>
  </dependencies>
```

### for Gradle

```
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.github.sttk:cliargs:0.1.0'
}
```

## Usage

### Parse without configurations

This library provides the method `CliArgs#parse` which parses command line arguments without configurations.
This method automatically divides command line arguments to options and command arguments.

Command line arguments starting with `-` or `--` are options, and others are command arguments.
If you want to specify a value to an option, follows `"="` and the value after the option, like `foo=123`.

All command line arguments after `--` are command arguments, even they starts
with `-` or `--`.

```java
  var args = new String[]{"--foo-bar", "hoge", "--baz=1", "-z=2", "-xyz=3", "fuga"};
  var cliargs = new CliArgs("path/to/app", args);
  var result = cliargs.parse();

  if (result.exception() != null) {
    var cmd = result.cmd();
    cmd.getName();             // "app"
    cmd.getArgs();             // ["hoge", "fuga"]
    cmd.hasOpt("foo-bar");     // true
    cmd.hasOpt("baz");         // true
    cmd.hasOpt("x");           // true
    cmd.hasOpt("y");           // true
    cmd.hasOpt("z");           // true
    cmd.getOptArg("foo-bar");  // null
    cmd.getOptArg("baz");      // "1"
    cmd.getOptArg("x");        // null
    cmd.getOptArg("y");        // null
    cmd.getOptArg("z");        // "2"
    cmd.getOptArgs("foo-bar"); // []
    cmd.getOptArgs("baz");     // [1]
    cmd.getOptArgs("x");       // []
    cmd.getOptArgs("y");       // []
    cmd.getOptArgs("z");       // ["2", "3"]
  }
```

Or

```java
  var args = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-xyz=3", "fuga"};
  var cliargs = new CliArgs("path/to/app", args);
  try {
    var cmd = cliargs.parse().cmdOrThrow();
    cmd.getName();             // "app"
    cmd.getArgs();             // ["hoge", "fuga"]
    cmd.hasOpt("foo-bar");     // true
    // ...

  } catch (ReasonedException e) {
    ...
  }
```


### Parse with configurations

This library provides the method `CliArgs#parseWith` which parses command line arguments with configurations.
This method takes an array of option configurations: `OptCfg[]` as the argument, and divides command line arguments to options and command arguments with this configurations.

And option cnfiguration has fields: `storeKey`, `names`, `hasArg`, `isArray`, `type`, `defaults`, `decc`, `argInHelp`, `converter`, and `postparser`.

`storeKey` field is specified the key name to store the option value in the option map.
If this field is not specified the first element of `names` field is set instead.

`names` field is a string array and specified the option names, that are both long options and short options.
The order of elements in this field is used in a help text.
If you want to prioritize the output of short option name first in the help text, like `-f, --foo-bar`, but use the long option name as the key in the option map, write `storekey` and `names` fields as follows:
`OptCfg(field("foo-bar", names("f", "foo-bar"))`.

`hasArg` field indicates the option requires one or more values.

`isArray` field indicates the option can have multiple values.

`types` field is set the data type of the option value(s).

`defaults` field is an array which is used as default one or more values if the
option is not specified in command line arguments.

`desc` field is a description of the option for help text.

`argInHelp` field is a text which is output after option name and aliases as an option value in help text.

`converter` field is a functional interface which converts an option argument string to the instance of the class specified by `type` field.

`postparser` field is a functional interface which processes option argument(s) after parsing if this field is specified.

```java
  var args = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "fuga"};
  var cliargs = new CliArgs("path/to/app", args);

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

  var result = cliargs.parseWith(optCfgs);
  if (result.exception() != null) {
    var cmd = result.cmd();
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
  }
```

This library provides `Help` class which generates help text from an `OptCfg` array.
The following help text is generated from the above `optCfgs`.

```java
  var help = new Help();
  help.addText("This is the usage description.");
  help.addOpts(optCfgs, 0, 2);
  help.print();

  // (stdout)
  // This is the usage description.
  //   --foo-bar, -f     This is description of foo-bar.
  //   --baz, -z <text>  This is description of baz.
```

### Parse for an option store with `@Opt` annoatation

This library provides the method `CliArgs#parseFor` which takes an option store object as the argument, and puts option values by parsing command line arguments to it.
The `@Opt` annotations are needed for the fields of the option store.

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
And `arg` is what to specify a text for an option argument value in help text.

```java
  var args = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "fuga"};
  var cliargs = new CliArgs("path/to/app", args);

  class Options {
    @Opt(cfg="foo-bar", desc="This is description of foo-bar.")
    boolean fooBar;

    @Opt(cfg="baz,z=[9,8,7]" desc="This is description of baz."), arg="<num>")
    int[] baz;

    @Opt(cfg="qux,x=", desc="This is description of qux.")
    boolean qux;
  }

  var options = new Options();

  var result = cliargs.parseFor(options);
  var cmd = result.cmd();
  var optCfgs = result.optCfgs();

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

The following help text is generated from the above optCfgs.

```java
  var help = new Help();
  help.addText("This is the usage description.")
  help.addOpts(optCfgs, 12, 1);
  help.print();

  // (stdout)
  // This is the usage description.
  //  --foo-bar   This is description of foo-bar.
  //  --baz, -z <num>
  //              This is description of baz.
  //  --qux       This is description of qux.
```

### Parse command line arguments including sub commands

This library provides the static method `CliArgs.findFirstArg` which returns the index of the first command argument.
If this index is negative, there is no command argument.
This static method enables to parse command line arguments with supporting sub
command, as follows:

```java
  var args = new String[]{"--foo-bar", "hoge", "--baz", "1", "-z=2", "-x", "abcd"};
  int index = CliArgs.findFirstArg(args);  // index => 1
  if (index < 0) throws ...;  // no sub command

  var topArgs = Arrays.copyOf(args, index);
  var subArgs = Arrays.copyOfRange(args, index + 1, args.length);

  var cliargs = new CliArgs("app", topArgs);
  var result = cliargs.parseFor(topOptions);
  var topOptCfgs = result.optCfgs();
  var topCmd = result.cmdOrThrow();

  cliargs = new CliArgs(args.get(index), subArgs);
  switch (args.get(index)) {
  case "hoge":
    result = cliargs.parseFor(hogeOptions);
    var hogeOptCfgs = result.optCfgs();
    var hogeCmd = result.cmdOrThrow();
    ...
    break;
  case "fuga":
    ...
  }
```

And the help text can be generated as follows:

```java
  var help = new Help();
  help.addText("This is the usage of this command.");
  help.addText("\nOPTIONS.");
  help.addOpts(topOptCfgs, 12, 2);
  help.addText("\nSUB COMMANDS:");
  help.addText(String.format("%12s%s", "hoge", "The description of hoge sub-command.");
  help.addOpts(hogeOpts, 12, 2);
  help.addText(String.format("%12s%s", "fuga", "The description of fuga sub-command.");
  help.addOpts(fugaOpts, 12, 2);
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
However, since it utilizes reflection for the option store object passed to `CliArgs#parseFor`, the reflection configurations for the class of this object need to be specified in `reflect-config.json`. The configuration are as follows:

```
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

- GraalVM CE 21.0.2+13.1 (openjdk version 21.0.2)
- GraalVM CE 22.0.1+8.1 (openjdk version 22.0.1)

## License

Copyright (C) 2023-2024 Takayuki Sato

This program is free software under MIT License.<br>
See the file LICENSE in this distribution for more details.


[repo-url]: https://github.com/sttk/cliargs-java
[mvn-img]: https://img.shields.io/badge/maven_central-0.1.0-276bdd.svg
[mvn-url]: https://central.sonatype.com/artifact/io.github.sttk/cliargs/0.1.0
[io-img]: https://img.shields.io/badge/github.io-Javadoc-4d7a97.svg
[io-url]: https://sttk.github.io/cliargs-java/
[ci-img]: https://github.com/sttk/cliargs-java/actions/workflows/java-ci.yml/badge.svg?branch=main
[ci-url]: https://github.com/sttk/cliargs-java/actions
[mit-img]: https://img.shields.io/badge/license-MIT-green.svg
[mit-url]: https://opensource.org/licenses/MIT

[posix-args]: https://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html#Argument-Syntax
[gnu-args]: https://www.gnu.org/prep/standards/html_node/Command_002dLine-Interfaces.html
