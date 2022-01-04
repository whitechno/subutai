package subutai.graphz.algos.BellmanFord

import subutai.graphz.RawWeightedDiGraph

object BellmanFordRunner {

  def run(
      rawGraph: RawWeightedDiGraph,
      rawSource: String,
      verbose: Boolean = false
  ): BellmanFordIterator = {
    val n      = rawGraph.graph.vertices.length
    val edges  = rawGraph.graph.edges
    val source = rawGraph.vm(rawSource)

    if (verbose)
      println(s"\n* initialize Bellman-Ford iterator with source = ${source}")
    val bfiter = BellmanFordIterator.init(n = n, edges = edges, source = source)
    if (verbose) println(bfiter.pp)

    bfiter.run(verbose = verbose)

    bfiter
  }

}
