package zzz.scalacheck

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

object TestMain_04_QuickExamples_TreeGenerator extends Run {
  run()

  // Generating Case Classes

  sealed abstract class Tree
  case class Node(left: Tree, right: Tree, v: Int) extends Tree
  case object Leaf extends Tree

  def genLeaf: Gen[Leaf.type] = Gen.const(Leaf)
  def genNode: Gen[Node] = for {
    v     <- arbitrary[Int]
    left  <- genTree
    right <- genTree
  } yield Node(left, right, v)

  def genTree: Gen[Tree] = Gen.oneOf(genLeaf, Gen.lzy(genNode))

  def genTrees: Gen[List[Tree]] = Gen.listOfN(3, genTree)

  override def run(): Unit = {
    println("|1> genTree: " + genTree.sample)

    println("|2> genTrees:")
    val genTreesSample: Option[List[Tree]] = genTrees.sample
    genTreesSample.foreach(_.foreach(println))
  }

}
