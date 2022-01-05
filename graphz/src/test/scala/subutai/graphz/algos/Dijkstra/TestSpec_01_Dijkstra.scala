package subutai.graphz.algos.Dijkstra

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import subutai.graphz.RawWeightedDiGraph

class TestSpec_01_Dijkstra extends AnyFlatSpec with Matchers {

  import subutai.graphz.SimpleGraphImplicits._

  "DijkstraRunner" should "" in {
    val rawGraph = RawWeightedDiGraph(
      edges = Seq(
        "0" -> Seq(("1", 7), ("2", 9), ("5", 14)),
        "1" -> Seq(("2", 10), ("3", 15)),
        "2" -> Seq(("3", 11), ("5", 2)),
        "3" -> Seq(("4", 6)),
        "4" -> Seq(("5", 9))
      )
    )
    println(rawGraph.graph.pp)
  }

}
