package subutai.breeze.zzz

import scala.util.Random
import breeze.stats.distributions.Uniform
// As of Breeze 2, import this if you want consistent behavior with
// the same random numbers from execution to execution
// (modulo threading or other sources of non-deterministic behavior)
import breeze.stats.distributions.Rand.FixedSeed._

object RandomBreeze {

  /** Generates `size` random Long numbers between `low` and `high`. */
  def randomLongList(low: Long, high: Long, size: Int): IndexedSeq[Long] = {
    val uniform = new Uniform(low = low.toDouble, high = high.toDouble)
    uniform.sample(n = size).map(_.toLong)
  }

  /** Generates `size` random Long numbers with defined `sum`. */
  def randomLongListWithSum(size: Int, sum: Long): IndexedSeq[Long] = {
    val samples =
      0L +: randomLongList(low = 0L, high = sum, size = size - 1).sorted :+ sum
    Random.shuffle(xs = { for (i <- 1 to size) yield samples(i) - samples(i - 1) })
  }

}
