Experiments with scalacheck
===========================

https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md

ScalaCheck is a tool for testing Scala and Java programs, based on property 
specifications and automatic test data generation. The basic idea is that you define 
a property that specifies the behaviour of a method or some unit of code, and 
ScalaCheck checks that the property holds. All test data are generated automatically 
in a random fashion, so you don't have to worry about any missed cases.
