package zzz.scalacheck

import org.scalacheck.{ Arbitrary, Gen, Prop }, Arbitrary.arbitrary, Prop.forAll

object TestMain_05_QuickExamples_Arbitrary extends Run {
  run()

  override def run(): Unit = {
    /* [[org.scalacheck.Arbitrary.arbitrary]]
  generates arbitrary values of any supported type
     */
    val evenInteger: Gen[Int] = arbitrary[Int].suchThat(_ % 2 == 0)
    println("|1> evenInteger: " + evenInteger.sample)
    val evenIntegers: Gen[List[Int]] =
      Gen.containerOfN[List, Int](n = 3, g = evenInteger)
    println("|2> evenIntegers: " + evenIntegers.sample)

    val squares: Gen[List[Long]] = for {
      xs <- arbitrary[List[Int]]
    } yield xs.map(x => x.toLong * x)
    println("|3> squares: " + squares.sample)
  }
}
