package zzz.scalacheck

import org.scalacheck.{ Arbitrary, Gen, Prop }, Arbitrary.arbitrary, Prop.forAll

object TestMain_06_QuickExamples_ArbitraryTree extends Run {
  run()

  override def run(): Unit = {

    /*
    You can use [[arbitrary]] for any type that has an implicit Arbitrary instance.
    As mentioned earlier, ScalaCheck has default support for common types,
    but it is also possible to define your own implicit Arbitrary instances
    for unsupported types.

    See the following implicit Arbitrary definition for booleans,
    that comes from the ScalaCheck implementation:

  implicit lazy val arbBool: Arbitrary[Boolean] = Arbitrary(oneOf(true, false))

    To get support for your own type T you need to define an implicit def or val
    of type Arbitrary[T]. Use the factory method Arbitrary(...) to create
    the Arbitrary instance. This method takes one parameter of type Gen[T]
    and returns an instance of Arbitrary[T].
     */

    /*
    Now, let's say you have a custom type Tree[T] that you want to use
    as a parameter in your properties:
     */
    sealed abstract class Tree[T] {
      def merge(t: Tree[T]): Node[T] = Node(List(this, t))

      // [[leafCount]] counts all Leaves
      def leafCount: Int = this match {
        case Leaf(_)        => 1
        case Node(children) => children.foldRight(0)(_.leafCount + _)
      }

      // [[totCount]] counts all Leaves and Nodes
      def totCount: Int = this match {
        case Leaf(_)        => 1
        case Node(children) => 1 + children.foldRight(0)(_.totCount + _)
      }

      def branchCount: Int = this match {
        case Leaf(_)        => 0
        case Node(children) => children.foldRight(0)(1 + _.branchCount + _)
      }

      def longestBranchLength: Int = this match {
        case Leaf(_) => 0
        case Node(children) =>
          if (children.isEmpty) 0 else 1 + children.map(_.longestBranchLength).max
      }
    }
    case class Node[T](children: Seq[Tree[T]]) extends Tree[T]
    case class Leaf[T](elem: T) extends Tree[T]

    val bunch = Node(Seq(Leaf(1), Leaf(2), Leaf(3)))
    println(
      s"|1> bunch: leafCount = ${bunch.leafCount}, " +
        s"totCount = ${bunch.totCount}, " +
        s"branchCount = ${bunch.branchCount}, " +
        s"longestBranchLength = ${bunch.longestBranchLength}"
    ) // prints 3 & 4 & 1

    val noLeaves = Node(
      children = Seq(
        Node(
          children = Seq(
            Node(
              children = Seq.empty[Tree[Int]]
            )
          )
        )
      )
    )
    println(
      s"|2> noLeaves: leafCount = ${noLeaves.leafCount}, " +
        s"totCount = ${noLeaves.totCount}, " +
        s"branchCount = ${noLeaves.branchCount}, " +
        s"longestBranchLength = ${noLeaves.longestBranchLength}"
    ) // prints 0 & 3 & 2

    val merged = noLeaves.merge(bunch)
    println(s"|3> merged:\n${merged}")
    println(
      s"leafCount = ${merged.leafCount}, " +
        s"totCount = ${merged.totCount}, " +
        s"branchCount = ${merged.branchCount}, " +
        s"longestBranchLength = ${merged.longestBranchLength}"
    ) // prints 3 & 8 & 3

    /*
    When you specify an implicit generator for your type Tree[T],
    you also have to assume that there exists an implicit generator for the type T.
    You do this by specifying an implicit parameter of type Arbitrary[T],
    so you can use the generator arbitrary[T].
     */
    implicit def arbTree[T](implicit a: Arbitrary[T]): Arbitrary[Tree[T]] = {
      Arbitrary {
        val genLeaf: Gen[Leaf[T]] = for (e <- arbitrary[T]) yield Leaf(e)

        def genNode(sz: Int): Gen[Node[T]] = for {
          n <- Gen.choose(sz / 3, sz / 2)
          c <- Gen.listOfN(n, sizedTree(sz / 2))
        } yield Node(c)

        def sizedTree(sz: Int): Gen[Tree[T]] =
          if (sz <= 0) genLeaf
          else Gen.frequency((1, genLeaf), (3, genNode(sz)))

        Gen.sized { sz =>
          println(s"-------- Gen.sized sz = ${sz} --------")
          sizedTree(sz)
        }
      }
    }

    println("|4> arbitrary[Tree[Char]].sample:")
    val sampleTreeChar: Option[Tree[Char]] = arbitrary[Tree[Char]].sample
    sampleTreeChar.foreach { tree =>
      println(tree)
      println(
        s"tree leafCount = ${tree.leafCount}, " +
          s"totCount = ${tree.totCount}, " +
          s"branchCount = ${tree.branchCount}, " +
          s"longestBranchLength = ${tree.longestBranchLength}"
      )
    }

    /*
    As long as the implicit arbTree function is in scope,
    you can now write properties like this:
     */
    println("|5> propMergeTree:")
    val propMergeTree: Prop = forAll { (t1: Tree[Int], t2: Tree[Int]) =>
      val (t1LeafCount, t1TotCount, t1BranchCount, t1LongestBranchLength) =
        (t1.leafCount, t1.totCount, t1.branchCount, t1.longestBranchLength)
      println(
        s"t1LeafCount=${t1LeafCount}, t1TotCount=${t1TotCount}, " +
          s"t1BranchCount=${t1BranchCount}, " +
          s"t1LongestBranchLength=${t1LongestBranchLength}"
      )

      val (t2LeafCount, t2TotCount, t2BranchCount, t2LongestBranchLength) =
        (t2.leafCount, t2.totCount, t2.branchCount, t2.longestBranchLength)
      println(
        s"t2LeafCount=${t2LeafCount}, t2TotCount=${t2TotCount}, " +
          s"t2BranchCount=${t2BranchCount}, " +
          s"t2LongestBranchLength=${t2LongestBranchLength}"
      )

      val t1t2 = t1.merge(t2)
      t1t2.leafCount == t1LeafCount + t2LeafCount &&
      t1t2.totCount == 1 + t1TotCount + t2TotCount &&
      t1t2.branchCount == 2 + t1BranchCount + t2BranchCount &&
      t1t2.longestBranchLength == 1 + math.max(
        t1LongestBranchLength,
        t2LongestBranchLength
      )
    }
    propMergeTree.check()

    // explicitly giving Tree generator
    println("|6> t: Tree[String] => t.leafCount > 0:")
    forAll(arbitrary[Tree[String]]) { t: Tree[String] => t.leafCount > 0 }.check()
    /*
    ! Falsified after 1 passed tests.
    > ARG_0: Node(List())
     */
  }

}
