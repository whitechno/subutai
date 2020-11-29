package zzz.scalacheck

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

object TestCheck_01_StringSpecification
    extends Properties(name = "01_StringSpecification") {

  // Grouping properties
  // to specify several related properties, perhaps for all methods in a class

  property("startsWith") = forAll { (a: String, b: String) => (a + b).startsWith(a) }

  property("endsWith") = forAll { (a: String, b: String) => (a + b).endsWith(b) }

  property("concatenate") = forAll { (a: String, b: String) =>
    (a + b).length >= a.length && (a + b).length >= b.length
  }

  property("substring2") = forAll { (a: String, b: String) =>
    (a + b).substring(a.length) == b
  }

  property("substring3") = forAll { (a: String, b: String, c: String) =>
    (a + b + c).substring(a.length, a.length + b.length) == b
  }

  /*
  There is a Properties.include method you can use if you want to group
  several different property collections into a single one.
  You could for example create one property collection for your application
  that consists of all the property collections of your individual classes:

  object MyAppSpecification extends Properties("MyApp") {
    include(StringSpecification)
    include(...)
    include(...)
  }
   */

}
