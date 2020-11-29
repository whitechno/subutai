package zzz.scalacheck

import org.scalacheck.{ Gen, Prop }
import org.scalacheck.Prop.{ forAll, propBoolean }

object TestMain_01_QuickExamples_Intro extends Run {
  run()
  override def run(): Unit = {
    println("|1> propConcatLists:")
    val propConcatLists: Prop = forAll { (l1: List[Int], l2: List[Int]) =>
      l1.size + l2.size == (l1 ::: l2).size
    }
    propConcatLists.check() /* + OK, passed 100 tests. */

    println("|2> propSqrt:")
    val propSqrt: Prop = forAll { (n: Int) => scala.math.sqrt(n * n) == n }
    propSqrt.check()
    /*
  ! Falsified after 0 passed tests.
  > ARG_0: -1
     */

    println("|3> propReverseList:")
    val propReverseList: Prop = forAll { l: List[String] => l.reverse.reverse == l }
    propReverseList.check() /* + OK, passed 100 tests. */

    println("|4> propConcatStringEnds:")
    val propConcatStringEnds: Prop = forAll { (s1: String, s2: String) =>
      (s1 + s2).endsWith(s2)
    }
    propConcatStringEnds.check() /* + OK, passed 100 tests. */

    println("|5> propConcatStringLength:")
    val propConcatStringLength: Prop = forAll { (a: String, b: String) =>
      (a + b).length > a.length && (a + b).length > b.length
    }
    propConcatStringLength.check()
    /*
  ! Falsified after 0 passed tests.
  > ARG_0: ""
  > ARG_1: ""
     */

    // give forAll a specific data generator
    println("|6> propSmallInteger:")
    val smallInteger: Gen[Int] = Gen.choose(min = 0, max = 100)
    val propSmallInteger: Prop = forAll(smallInteger) { n => n >= 0 && n <= 100 }
    propSmallInteger.check() /* + OK, passed 100 tests. */

    // Conditional Properties

    // Sometimes, a specification takes the form of an implication,
    // using the implication operator ==>
    println("|7> propMakeList:")
    val propMakeList = forAll { n: Int =>
      (n >= 0 && n < 10000) ==> (List.fill(n)("").length == n)
    }
    propMakeList.check() /* + OK, passed 100 tests. */

    println("|8> propTrivial:")
    val propTrivial = forAll { n: Int => (n == 0) ==> (n == 0) }
    propTrivial.check()
    /* ! Gave up after only 54 passed tests. 501 tests were discarded. */
  }
  /* Combining Properties
  val p1 = forAll(...)
  val p2 = forAll(...)
  val p3 = p1 && p2
  val p4 = p1 || p2
  val p5 = p1 == p2 // will hold if p1 holds exactly when p2 holds and vice versa
  val p6 = all(p1, p2) // same as p1 && p2
  val p7 = atLeastOne(p1, p2) // same as p1 || p2
   */

}
