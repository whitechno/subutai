package zzz.scalacheck

import org.scalacheck.{ Gen, Prop }, Prop.{ classify, collect, forAll }

object TestMain_07_QuickExamples_CollectingGeneratedData extends Run {
  run()

  override def run(): Unit = {
    /*
  It is possible to collect statistics about what kind of test data that has been
  generated during property evaluation. This is useful if you want to inspect the
  test case distribution, and make sure your property tests all different kinds of
  cases, not just trivial ones.

  For example, you might have a method that operates on lists, and which behaves
  differently if the list is sorted or not. Then it is crucial to know if ScalaCheck
  tests the method with both sorted and unsorted lists. Let us first define an
  ordered method to help us state the property.
     */
    def ordered(l: List[Int]): Boolean = l.size > 1 && l == l.sorted
    /*
  Now state the property, using Prop.classify to collect interesting information
  on the generated data. The property itself is not very exciting in this example,
  we just state that a double reverse should return the original list.
     */
    val myProp: Prop = forAll { l: List[Int] =>
      classify(c = ordered(l), ifTrue = "ordered") {
        classify(c = l.length > 5, ifTrue = "large", ifFalse = "small") {
          l.reverse.reverse == l
        }
      }
    }
    println("|1> myProp:")
    myProp.check()
    /*
  + OK, passed 100 tests.
  > Collected test data:
  72% large
  21% small
  7% small, ordered
     */

    /*
  We can also collect data directly, using the Prop.collect method.
  In this dummy property, we just want to see if ScalaCheck distributes
  the generated data evenly:
     */
    val dummyProp = forAll(Gen.choose(min = 1, max = 10)) { n =>
      collect(n) {
        n == n
      }
    }
    println("|2> dummyProp:")
    dummyProp.check()
    /*
  + OK, passed 100 tests.
  > Collected test data:
  14% 2
  14% 3
  12% 5
  12% 9
  11% 8
  9% 1
  8% 7
  8% 6
  7% 4
  5% 10
  As we can see, the frequency for each number is around 10%, which seems reasonable.
     */

  }
}
