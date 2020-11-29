package zzz.scalacheck

import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary

object TestMain_03_QuickExamples_Generators extends Run {
  run()
  override def run(): Unit = {

    /*
  Suppose you need a generator which generates a tuple that contains two random
  integer values, one of them being at least twice as big as the other.
  The following definition does this:
     */
    val myGen: Gen[(Int, Int)] = for {
      n <- Gen.choose(min = 10, max = 20)
      m <- Gen.choose(min = 2 * n, max = 500)
    } yield (n, m)
    println("|1> myGen: " + myGen.sample)

    /*
  You can create generators that picks one value out of a selection of values.
  The following generator generates a vowel:
     */
    val vowel: Gen[Char] = Gen.oneOf(t0 = 'A', t1 = 'E', tn = 'I', 'O', 'U', 'Y')
    println("|2> vowel: " + vowel.sample)

    /*
  The oneOf method creates a generator that randomly picks one of its parameters
  each time it generates a value. Notice that plain values are implicitly converted
  to generators (which always generates that value) if needed.

  The distribution is uniform,
  but if you want to control it you can use the frequency combinator below.
  Now, the vowel generator will generate E:s more often than Y:s.
  Roughly, 4/14 of the values generated will be E:s, and 1/14 of them will be Y:s.
     */
    val vowelWithFrequency: Gen[Char] = Gen.frequency(
      (3, 'A'),
      (4, 'E'),
      (2, 'I'),
      (3, 'O'),
      (1, 'U'),
      (1, 'Y')
    )
    println("|3> vowelWithFrequency: " + vowelWithFrequency.sample)

    // Sized Generators
    /*
  When ScalaCheck uses a generator to generate a value,
  it feeds it with some parameters.
  One of the parameters the generator is given, is a size value,
  which some generators use to generate their values.
  If you want to use the size parameter in your own generator,
  you can use the Gen.sized method:
     */
    println("|4> matrix:")
    def matrix[T](g: Gen[T]): Gen[Seq[Seq[T]]] = Gen.sized { size =>
      println("size=" + size)
      val side = scala.math.sqrt(size).asInstanceOf[Int]
      Gen.listOfN(side, Gen.listOfN(side, g))
    }
    val matrixSample: Option[Seq[Seq[Int]]] = matrix(arbitrary[Int]).sample
    matrixSample.foreach(_.foreach(println))

    // Conditional Generators
    /*
  Conditional generators can be defined using Gen.suchThat in the following way:
     */
    val smallEvenInteger = Gen.choose(min = 0, max = 200).suchThat(_ % 2 == 0)
    println("|5> smallEvenInteger: " + smallEvenInteger.sample)

    // Generating Containers

    /*
  There is a special generator, [[Gen.containerOf]], that generates containers
  such as lists and arrays.
  They take another generator as argument, that is responsible for generating
  the individual items.
  By default, ScalaCheck supports generation of List,
  Stream (Scala 2.10 - 2.12, deprecated in 2.13), LazyList (Scala 2.13),
  Set, Array, and ArrayList (from java.util).
  You can add support for additional containers by adding
  implicit Buildable instances. See Buildable.scala for examples.
  There is also [[Gen.nonEmptyContainerOf]] for generating non-empty containers,
  and [[Gen.containerOfN]] for generating containers of a given size.
     */
    val genIntList: Gen[List[Int]] =
      Gen.containerOf[List, Int](Gen.oneOf(t0 = 1, t1 = 3, tn = 5))
    println("|6> genIntList: " + genIntList.sample)

    val genStringList: Gen[List[String]] =
      Gen.containerOf[List, String](Gen.alphaStr)
    println("|7> genStringList: " + genStringList.sample)

    val genBoolSet: Gen[Set[Boolean]] = Gen.containerOf[Set, Boolean](true)
    println("|8> genBoolSet: " + genBoolSet.sample)

    /*
  To generate a container
  by picking an arbitrary number of elements use [[Gen.someOf]],
  or by picking one or more elements with [[Gen.atLeastOne]].
     */
    val zeroOrMoreDigits = Gen.someOf(1 to 9)
    println("|9> zeroOrMoreDigits: " + zeroOrMoreDigits.sample)

    val oneOrMoreDigits = Gen.atLeastOne(1 to 9)
    println("|10> oneOrMoreDigits: " + oneOrMoreDigits.sample)

    /*
  [[Gen.pick]] randomly pick n elements from a container:
     */
    val fiveDice = Gen.pick(n = 5, l = 1 to 6)
    println("|11> fiveDice: " + fiveDice.sample)
    val threeLetters = Gen.pick(n = 3, l = 'A' to 'Z')
    println("|12> threeLetters: " + threeLetters.sample)

    /*
  Note that Gen.someOf, Gen.atLeastOne, and Gen.pick only randomly select elements.
  They do not generate permutations of the result with elements in different orders.
  To make your generator artificially permute the order of elements,
  you can run scala.util.Random.shuffle on each of the generated containers
  with the map method.
     */
    import scala.util.Random
    println("Random.shuffle(Seq(1,2,3)) = " + Random.shuffle(Seq(1, 2, 3)))
    val threeLettersPermuted = threeLetters.map(Random.shuffle(_))
    println("|13> threeLettersPermuted: " + threeLettersPermuted.sample)
  }
}
