package zzz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.collection.immutable.TreeSet
import zzz.iterator.IteratorImplicits.IteratorOps

/** Experiments with [[scala.collection.immutable.TreeSet]].
  * {{{sbt> stateful-iterator/testOnly *TreeSetSpec}}}
  */
class TreeSetSpec extends AnyFlatSpec with Matchers {
  // buckets: 0:[0,10), 1:[10,20), 2:[20,30), ...
  type Bucket = Int
  val BUCKET_SIZE: Int           = 10
  def int2bucket(x: Int): Bucket = x / BUCKET_SIZE

  "int2bucket" should "00 compute bucket index" in {
    int2bucket(0) shouldBe 0
    int2bucket(255) shouldBe 25
  }

  "TreeSet" should "01 provide ordering to the collection" in {
    val startPoints = Seq(1, 100, 10, 10, 100)
    // TreeSet(100, 10, 1)
    val startPointsReversed =
      TreeSet.empty[Int](Ordering[Int].reverse) ++ startPoints

    startPointsReversed.toSeq shouldBe Seq(100, 10, 1)

    startPointsReversed.keysIteratorFrom(0).toList shouldBe Seq()
    startPointsReversed.keysIteratorFrom(1).toList shouldBe Seq(1)
    startPointsReversed.keysIteratorFrom(5).toList shouldBe Seq(1)
    startPointsReversed.keysIteratorFrom(10).toList shouldBe Seq(10, 1)
    startPointsReversed.keysIteratorFrom(90).toList shouldBe Seq(10, 1)
    startPointsReversed.keysIteratorFrom(100).toList shouldBe Seq(100, 10, 1)
  }

  /** Assumes that `start >= end`, but doesn't enforce it. */
  case class Span(start: Int, end: Int)

  /** True if the given spans share any points. */
  def overlaps(a: Span, b: Span): Boolean = a.end >= b.start && b.end >= a.start

  /** If the given spans overlap, return their intersect, exactly like the set
    * intersection operation. Otherwise, return [[None]].
    */
  def slice(original: Span, theSlice: Span): Option[Span] =
    if (overlaps(original, theSlice))
      Some(
        Span(
          start = math.max(original.start, theSlice.start),
          end   = math.min(original.end, theSlice.end)
        )
      )
    else None

  "Span slice" should "02 produce the intersect or None" in {
    slice(Span(1, 2), Span(3, 4)) shouldBe None
    slice(Span(1, 3), Span(3, 3)) shouldBe Some(Span(3, 3))
    slice(Span(1, 4), Span(3, 4)) shouldBe Some(Span(3, 4))
    slice(Span(4, 4), Span(3, 4)) shouldBe Some(Span(4, 4))
    slice(Span(4, 5), Span(3, 4)) shouldBe Some(Span(4, 4))
    slice(Span(5, 6), Span(3, 4)) shouldBe None
  }

  /** Selects spans soon after points. */
  def spansSoonAfterPoints1(
      spans: TraversableOnce[Span],
      pointsInReverseOrder: TreeSet[Int],
      soon: Int
  ): TraversableOnce[(Span, Int)] = for {
    span       <- spans
    priorPoint <- pointsInReverseOrder.keysIteratorFrom(span.start).nextOption()
    if span.start <= priorPoint + soon
  } yield (span, priorPoint)

  "spansSoonAfterPoints1" should "03 select spans that are soon after points" in {
    val startPointsReversed = // TreeSet(100, 10, 1)
      TreeSet.empty[Int](Ordering[Int].reverse) ++ Seq(1, 10, 100)
    val soon        = 11
    val inputSpans  = Seq(Span(1, 2), Span(11, 40), Span(21, 22), Span(22, 23))
    val outputSpans = spansSoonAfterPoints1(inputSpans, startPointsReversed, soon)
    // println(outputSpans.toList)
    outputSpans.toList shouldBe List(
      (Span(1, 2), 1),
      (Span(11, 40), 10),
      (Span(21, 22), 10)
    )
  }

  it should "04 select only spans that start on or soon after startPoint" in {
    val startPointsReversed =
      TreeSet.empty[Int](Ordering[Int].reverse) ++ Seq(10)
    val soon        = 1
    val inputSpans  = Seq(Span(9, 9), Span(10, 10), Span(11, 11), Span(12, 12))
    val outputSpans = spansSoonAfterPoints1(inputSpans, startPointsReversed, soon)
    // println(outputSpans.toList)
    outputSpans.toList shouldBe List((Span(10, 10), 10), (Span(11, 11), 10))
  }

  /** Selects and slices spans soon after points. */
  def spansSoonAfterPoints2(
      spans: TraversableOnce[Span],
      pointsInReverseOrder: TreeSet[Int],
      soon: Int
  ): TraversableOnce[(Span, Int)] = for {
    span       <- spans
    priorPoint <- pointsInReverseOrder.keysIteratorFrom(span.start).nextOption()
    slicedSpan <- slice(span, Span(priorPoint, priorPoint + soon))
  } yield (slicedSpan, priorPoint)

  "spansSoonAfterPoints2" should
    "05 select and slice spans that are soon after points" in {
      val startPointsReversed = // TreeSet(100, 10, 1)
        TreeSet.empty[Int](Ordering[Int].reverse) ++ Seq(1, 10, 100)
      val soon        = 11
      val inputSpans  = Seq(Span(1, 2), Span(11, 40), Span(21, 22), Span(22, 23))
      val outputSpans = spansSoonAfterPoints2(inputSpans, startPointsReversed, soon)
      // println(outputSpans.toList)
      outputSpans.toList shouldBe List(
        (Span(1, 2), 1),
        (Span(11, 21), 10),
        (Span(21, 21), 10)
      )
    }

}
