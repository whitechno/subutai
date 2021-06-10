HOCON and typesafe config
=========================

Experiments with HOCON and [typesafe config](https://github.com/lightbend/config).

[HOCON](https://github.com/lightbend/config/blob/master/HOCON.md), or Human-Optimized
Config Object Notation is a format for human-readable data, and a superset
of [JSON](https://json.org/).

Definitions
-----------

- a _key_ is a string JSON would have to the left of `:` and a _value_ is anything
  JSON would have to the right of `:`. i.e. the two halves of an object _field_.

- a _value_ is any "value" as defined in the JSON spec, plus unquoted strings and
  substitutions as defined in this spec.

- a _simple value_ is any value excluding an object or array value.

- a _field_ is a key, any separator such as ':', and a value.

- references to a _file_ ("the file being parsed") can be understood to mean any byte
  stream being parsed, not just literal files in a filesystem.

reference.conf vs application.conf
----------------------------------

- `reference.conf` is a file for library developers.  
  All properties required by the library should be mentioned there and filled with
  reasonable default values when possible.
- `application.conf` is a file for application developers.  
  You can set values for properties used by libraries in case they are not the same
  as defaults. Also you can declare your own properties and refer to them from the
  code.

When `ConfigFactory.load` is called all `reference.conf` and `application.conf` files
are merged into one configuration and `application.conf` has higher precedence. The
design intent is to have `application.conf` separate from `reference.conf`,
specifically so it can be ordered on top.

If you are not building a library that will be reused in several places you don't
need to touch `reference.conf` at all. Just put all properties in `application.conf`.


circe-config
============
https://github.com/circe/circe-config

Small library for translating between HOCON, Java properties, and JSON documents and
circe's JSON AST.

At a high-level it can be used as a circe powered front-end for the Typesafe config
library to enable boilerplate free loading of settings into Scala types. More
generally it provides parsers and printers for interoperating with Typesafe config's
JSON AST.

To use this library configure your sbt project with the following line:

```
libraryDependencies += "io.circe" %% "circe-config" % "0.8.0"
```

SHocon
======

[SHocon](https://github.com/akka-js/shocon)
is a simple, pure-Scala, alternative implementation of the HOCON specification.

SHocon ships with a native, Scala-idiomatic API, and a shim that mimics the Typesafe
Config Java API.

Usage
-----
Add these lines to your `project/plugins.sbt`:

```
addSbtPlugin("org.akka-js" % "sbt-shocon" % "1.0.0")
```

and in `build.sbt`:

```
val root = project.in(file("."))
  .enablePlugins(ShoconPlugin)
  .settings(
    libraryDependencies += "org.akka-js" %% "shocon" % "1.0.0",
    // for Scala.js/Native or cross projects use %%% instead:
    // libraryDependencies += "org.akka-js" %%% "shocon" % "1.0.0"

    // add dependency on shocon file generation task
    // (not required, but otherwise you need to call shoconConcat manually before compilation!)
    compile in Compile := (compile in Compile).dependsOn(shoconConcat).value

    /* ... */
  )
```

Loading of default configuration
--------------------------------
In contrast to Typesafe config, which loads configuration files dynamically at run
time,
`shocon` compiles the default configuration returned by `ConfigFactory.load()`
statically into the code. This includes all `reference.conf` files found in
the `resources` directory of the project itself, as well as all `reference.conf`
files found in JARs on which the project depends. If there is an `application.conf`
file in the resources directory of the project, this one will be included as well (
after all `reference.conf` files).

The resulting HOCON configuration file is assembled
in `target/scala-VERSION/shocon.conf`.

Since version `0.3.1` the parse phase is aggressively moved at compile time, please
note that runtime parsing cost a lot in terms of performances.

ShoconPlugin settings
---------------------
You can control the contents of the included default configuration with the following
sbt settings:

- `shoconLoadFromJars`: set to `false`, if you don't want to include
  any `reference.conf`
  files found in JARs
- `shoconFilter: Function1[(String,InputStream), Boolean]`: set this setting to a
  filter function that return `true` for all configuration files to be included; the
  first element in the tuple passed to the function is the absolute URL of the
  configuration file.

<hr/>

ClassLoader
===========

## How to print out the current project classpath

```scala
import java.lang.ClassLoader

val cl = ClassLoader.getSystemClassLoader
cl.asInstanceOf[java.net.URLClassLoader].getURLs.foreach(println)

cl.asInstanceOf[java.net.URLClassLoader]
  .getURLs
  .map(_.toString)
  .sorted
  .foreach(println)
```
