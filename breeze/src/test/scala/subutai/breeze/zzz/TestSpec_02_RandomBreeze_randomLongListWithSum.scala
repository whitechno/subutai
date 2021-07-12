package subutai.breeze.zzz

import org.scalatest.flatspec.AnyFlatSpec

class TestSpec_02_RandomBreeze_randomLongListWithSum extends AnyFlatSpec {

  "randomLongListWithSum" should "generate list with sum" in {
    val (size, sum) = (10, 100L)

    val sample = RandomBreeze.randomLongListWithSum(size = size, sum = sum)
    //println(sample) // something like Vector(12, 17, 3, 10, 11, 7, 4, 17, 4, 15)
    assert(sample.size == size && sample.sum == sum)
  }

}
