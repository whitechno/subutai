package subutai.graphz.algos.BellmanFord

import subutai.graphz.SimpleEdge

class BellmanFordIterator(
    val n: Int,
    val edges: Array[SimpleEdge[Double]],
    val source: Int,
    val distances: Array[Double],
    val predecessors: Array[Option[Int]],
    var negativeWeightCycles: Array[SimpleEdge[Double]] = Array.empty,
    var convergedIter: Int                              = 0
) {
  def run(verbose: Boolean = false): Unit = {
    runBellmanFord(verbose = verbose)
    findNegativeWeightCycles()
  }

  def runBellmanFord(verbose: Boolean = false): Unit = {
    var prevDistances: List[Double] = distances.toList

    for (iter <- 1 until n) { // repeat n-1 times (|V|-1)
      for (SimpleEdge(u, v, w) <- edges) { // iterate over all edges
        if (distances(u) + w < distances(v)) {
          distances(v)    = distances(u) + w
          predecessors(v) = Some(u)
        }
      }
      // check if different from previous iteration
      if (distances.toList != prevDistances) {
        prevDistances = distances.toList
        convergedIter = iter
      } else if (convergedIter == iter - 1 && verbose)
        println(s"*** converged on iter ${convergedIter} ***")

      if (verbose) println(s"* iter ${iter}")
      if (verbose) println(this.pp)
    }
  }
  def findNegativeWeightCycles(): Unit = negativeWeightCycles = for {
    SimpleEdge(u, v, w) <- edges if distances(u) + w < distances(v)
  } yield SimpleEdge(u, v, w)

  def pp: String = distances
    .zip(predecessors)
    .zipWithIndex
    .map { case ((dist, preId), id) =>
      val idPP = if (id == source) s"*[${id}]" else s"[${id}]"
      s"[${preId.getOrElse("_")}]>~>${idPP}${BellmanFordIterator.pp(dist)}"
    }
    .mkString(", ")
}

object BellmanFordIterator {
  def init(
      n: Int,
      edges: Array[SimpleEdge[Double]],
      source: Int
  ): BellmanFordIterator = {
    val distances = Array.fill[Double](n)(Double.PositiveInfinity)
    distances(source) = 0
    val predecessors = Array.fill[Option[Int]](n)(None)
    new BellmanFordIterator(
      n            = n,
      edges        = edges,
      source       = source,
      distances    = distances,
      predecessors = predecessors
    )
  }

  private def pp(d: Double): String =
    if (d == Double.PositiveInfinity) "INF" else s"$d"
}
