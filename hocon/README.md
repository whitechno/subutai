HOCON and typesafe config
=========================
Experiments with HOCON and typesafe config

[HOCON](https://github.com/lightbend/config/blob/master/HOCON.md), 
or Human-Optimized Config Object Notation is a format for human-readable data, 
and a superset of [JSON](https://json.org/).

## Definitions

 - a _key_ is a string JSON would have to the left of `:` and a _value_ is
   anything JSON would have to the right of `:`. i.e. the two
   halves of an object _field_.

 - a _value_ is any "value" as defined in the JSON spec, plus
   unquoted strings and substitutions as defined in this spec.

 - a _simple value_ is any value excluding an object or array
   value.

 - a _field_ is a key, any separator such as ':', and a value.

 - references to a _file_ ("the file being parsed") can be
   understood to mean any byte stream being parsed, not just
   literal files in a filesystem.


ClassLoader
===========

## How to print out the current project classpath

```scala
import java.lang.ClassLoader
val cl = ClassLoader.getSystemClassLoader
cl.asInstanceOf[java.net.URLClassLoader].getURLs.foreach(println)

cl.asInstanceOf[java.net.URLClassLoader].getURLs
  .map(_.toString).sorted.foreach(println)
```
