package subutai.breeze.zzz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalacheck.Gen.posNum
import org.scalacheck.Prop.forAll

class TestSpec_01_RandomBreeze_randomLongList extends AnyFlatSpec {

  "randomLongList" should "generate correct list of numbers" in {
    val (start, end, size) = (1, 3, 5)

    val sample = RandomBreeze.randomLongList(low = start, high = end, size = size)
    //println(sample) // something like Vector(2, 1, 1, 2, 1)
    assert(sample.size == size && sample.min >= start && sample.max <= end)
  }

  // This test must fail because of `sample.size != size`
  // But, it doesn't work - with or without the `it should "" in { ... }`
  // "Falsified" scalacheck test doesn't lead to failed scalatest test.
  // We cannot just run scalacheck's `forAll` in the scalatest's `AnyFlatSpec`.
  // Instead, scalacheck's "extends Properties" should be used.
  it should "generate '! Falsified after 0 passed tests'" in {
    forAll(posNum[Long], posNum[Long], posNum[Int]) { (a, b, size) =>
      val start  = Math.min(a, b)
      val end    = Math.max(a, b)
      val sample = RandomBreeze.randomLongList(start, end, size)
      sample.size != size && sample.min >= start && sample.max <= end
    }.check()
  }
}
