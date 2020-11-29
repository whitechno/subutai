package zzz.scalacheck

import org.scalacheck.Prop.{ all, forAll, propBoolean }

object TestMain_02_QuickExamples_Labeling extends Run {
  run()
  override def run(): Unit = {
    /*
    Labeling Properties
    Sometimes it can be difficult to decide exactly what is wrong
    when a property fails, especially if the property is complex,
    with many conditions.
    In such cases, you can label the different parts of the property,
    so ScalaCheck can tell you exactly what part is failing.
    Look at the following example, where the different conditions of the property
    have been labeled differently:
     */

    def myMagicFunction(n: Int, m: Int): Int = n + m

    println("|1> complexProp:")
    val complexProp = forAll { (m: Int, n: Int) =>
      val res = myMagicFunction(n, m)
      (res >= m)    :| "result > #1" &&
      (res >= n)    :| "result > #2" &&
      (res < m + n) :| "result not sum"
    }
    complexProp.check()
    /*
    ! Falsified after 0 passed tests.
    > Labels of failing property:
    result not sum
    > ARG_0: 0
    > ARG_1: 0
    > ARG_0_ORIGINAL: 2147483647
    > ARG_1_ORIGINAL: 2147483647
     */

    // It is also possible to write the label before the conditions like this:
    println("|2> complexProp2:")
    val complexProp2 = forAll { (m: Int, n: Int) =>
      val res = myMagicFunction(n, m)
      ("result > #1"    |: res >= m) &&
      ("result > #2"    |: res >= n) &&
      ("result not sum" |: res < m + n)
    }
    complexProp2.check()

    /*
    The labeling operator can also be used to inspect intermediate values
    used in the properties, which can be very useful when trying to understand
    why a property fails. ScalaCheck always presents the generated property
    arguments (ARG_0, ARG_1, etc), but sometimes you need to quickly see
    the value of an intermediate calculation.
    See the following example, which tries to specify multiplication
    in a somewhat naive way:
     */
    println("|3> propMul:")
    val propMul = forAll { (n: Int, m: Int) =>
      val res = n * m
      ("evidence = " + res) |: all(
        "div1" |: m != 0 ==> (res / m == n),
        "div2" |: n != 0 ==> (res / n == m),
        "lt1"  |: res > m,
        "lt2"  |: res > n
      )
    }
    propMul.check()
    /*
    ! Falsified after 0 passed tests.
    > Labels of failing property:
    lt1
    evidence = 0
    > ARG_0: 0
    > ARG_1: 0
    > ARG_0_ORIGINAL: 1
    > ARG_1_ORIGINAL: -2147483648
     */
  }
}
