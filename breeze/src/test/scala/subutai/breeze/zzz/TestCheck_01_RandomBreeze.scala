package subutai.breeze.zzz

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen.posNum

object TestCheck_01_RandomBreeze extends Properties(name = "RandomBreeze") {

  property("randomLongList") = forAll(posNum[Long], posNum[Long], posNum[Int]) {
    (a, b, size) =>
      val low    = Math.min(a, b)
      val high   = Math.max(a, b)
      val sample = RandomBreeze.randomLongList(low, high, size)
      sample.size == size && sample.min >= low && sample.max <= high
  }

  property("randomLongListWithSum") = forAll(posNum[Int], posNum[Long]) {
    (size, sum) =>
      val sample = RandomBreeze.randomLongListWithSum(size, sum)
      sample.size == size && sample.sum == sum
  }

}
