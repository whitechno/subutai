package subutai.graphz.algos.BellmanFord

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import subutai.graphz.{ RawWeightedDiGraph, SimpleEdge, SimpleGraph, SimpleVertex }

class TestSpec_01_BellmanFord extends AnyFlatSpec with Matchers {

  import subutai.graphz.SimpleGraphImplicits._

  "BellmanFordRunner" should "converge on simple path graph" in {
    val rawGraph = RawWeightedDiGraph(
      vertices = Seq("F"),
      edges = Seq(
        "A" -> Seq(("B", -3)),
        "B" -> Seq(("C", 1)),
        "C" -> Seq(("D", 1)),
        "D" -> Seq(("E", 1))
      )
    )
    val graph: SimpleGraph[String, Double] = rawGraph.graph
    graph shouldEqual new SimpleGraph[String, Double](
      vertices = Array(
        SimpleVertex(0, "F"),
        SimpleVertex(1, "A"),
        SimpleVertex(2, "B"),
        SimpleVertex(3, "C"),
        SimpleVertex(4, "D"),
        SimpleVertex(5, "E")
      ),
      edges = Array(
        SimpleEdge(1, 2, -3),
        SimpleEdge(2, 3, 1),
        SimpleEdge(3, 4, 1),
        SimpleEdge(4, 5, 1)
      )
    )
    // println(graph.pp)
    graph.pp shouldBe
      """F[0], A[1], B[2], C[3], D[4], E[5]
        |[1]>-3.0>[2], [2]>1.0>[3], [3]>1.0>[4], [4]>1.0>[5]""".stripMargin

    val rawSource = "A"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 5
    bfiter.convergedIter shouldBe 1
    bfiter.pp shouldBe
      "[_]>~>[0]INF, [_]>~>*[1]0.0, [1]>~>[2]-3.0, [2]>~>[3]-2.0, " +
      "[3]>~>[4]-1.0, [4]>~>[5]0.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe true
  }

  it should """converge slower on the same simple path graph 
       but with edges listed in different order""" in {
    val rawGraph = RawWeightedDiGraph(
      vertices = Seq("F"),
      edges = Seq[(String, Seq[(String, Double)])](
        "A" -> Seq(("B", -3)),
        "B" -> Seq(("C", 1)),
        "C" -> Seq(("D", 1)),
        "D" -> Seq(("E", 1))
      ).reverse
    )
    // println(rawGraph.graph.pp)
    rawGraph.graph.pp shouldBe
      """F[0], A[1], B[2], C[3], D[4], E[5]
        |[4]>1.0>[5], [3]>1.0>[4], [2]>1.0>[3], [1]>-3.0>[2]""".stripMargin

    val rawSource = "A"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 5
    bfiter.convergedIter shouldBe 4
    bfiter.pp shouldBe
      "[_]>~>[0]INF, [_]>~>*[1]0.0, [1]>~>[2]-3.0, [2]>~>[3]-2.0, " +
      "[3]>~>[4]-1.0, [4]>~>[5]0.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe true
  }

  it should "converge on some graph G" in {
    val rawGraph = RawWeightedDiGraph(
      edges = Seq(
        "S"  -> Seq(("V1", 9), ("V2", 3)),
        "V1" -> Seq(("V2", 6), ("V4", 2)),
        "V2" -> Seq(("V1", 2), ("V3", 1)),
        "V3" -> Seq(("V2", 2), ("V4", 2))
      )
    )
    // println(rawGraph.graph.pp)
    rawGraph.graph.pp shouldBe
      """S[0], V1[1], V2[2], V3[3], V4[4]
        |[0]>9.0>[1], [0]>3.0>[2], [1]>6.0>[2], [1]>2.0>[4], """.stripMargin +
      """[2]>2.0>[1], [2]>1.0>[3], [3]>2.0>[2], [3]>2.0>[4]"""

    val rawSource = "S"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 4
    bfiter.convergedIter shouldBe 1
    bfiter.pp shouldBe
      "[_]>~>*[0]0.0, [2]>~>[1]5.0, [0]>~>[2]3.0, [2]>~>[3]4.0, [3]>~>[4]6.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe true
  }

  it should """converge slower on the same graph G 
       but with edges listed in different order""" in {
    val rawGraph = RawWeightedDiGraph(
      edges = Seq[(String, Seq[(String, Double)])](
        "S"  -> Seq(("V1", 9), ("V2", 3)),
        "V1" -> Seq(("V2", 6), ("V4", 2)),
        "V2" -> Seq(("V1", 2), ("V3", 1)),
        "V3" -> Seq(("V2", 2), ("V4", 2))
      ).reverse
    )
    // println(rawGraph.graph.pp)
    rawGraph.graph.pp shouldBe
      """S[0], V1[1], V2[2], V3[3], V4[4]
        |[3]>2.0>[2], [3]>2.0>[4], [2]>2.0>[1], [2]>1.0>[3], """.stripMargin +
      """[1]>6.0>[2], [1]>2.0>[4], [0]>9.0>[1], [0]>3.0>[2]"""

    val rawSource = "S"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 4
    bfiter.convergedIter shouldBe 3
    bfiter.pp shouldBe
      "[_]>~>*[0]0.0, [2]>~>[1]5.0, [0]>~>[2]3.0, [2]>~>[3]4.0, [3]>~>[4]6.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe true

  }

  it should "run on graph with two negative cycles I" in {
    val rawGraph = RawWeightedDiGraph(
      edges = Seq(
        "S"  -> Seq(("X", 1)),
        "X"  -> Seq(("Y", 1)),
        "Y"  -> Seq(("Z", -2)),
        "Z"  -> Seq(("X", 0)),
        "Y"  -> Seq(("ZZ", -2)),
        "ZZ" -> Seq(("X", 0))
      )
    )
    // println(rawGraph.graph.pp)
    rawGraph.graph.pp shouldBe
      """S[0], X[1], Y[2], Z[3], ZZ[4]
        |[0]>1.0>[1], [1]>1.0>[2], [2]>-2.0>[3], [3]>0.0>[1], """.stripMargin +
      """[2]>-2.0>[4], [4]>0.0>[1]"""

    val rawSource = "S"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 4
    bfiter.convergedIter shouldBe 4
    bfiter.pp shouldBe
      "[_]>~>*[0]0.0, [3]>~>[1]-3.0, [1]>~>[2]-1.0, [2]>~>[3]-3.0, [2]>~>[4]-3.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe false
    // println(bfiter.negativeWeightCycles.pp)
    bfiter.negativeWeightCycles.pp shouldBe "[1]>1.0>[2]"
  }

  it should "run on graph with two negative cycles II" in {
    val rawGraph = RawWeightedDiGraph(
      edges = Seq(
        "S"  -> Seq(("Y", 1)),
        "Y"  -> Seq(("Z", -2)),
        "Z"  -> Seq(("S", 0)),
        "S"  -> Seq(("YY", 1)),
        "YY" -> Seq(("ZZ", -2)),
        "ZZ" -> Seq(("S", 0))
      )
    )
    // println(rawGraph.graph.pp)
    rawGraph.graph.pp shouldBe
      """S[0], Y[1], YY[2], Z[3], ZZ[4]
        |[0]>1.0>[1], [1]>-2.0>[3], [3]>0.0>[0], [0]>1.0>[2], """.stripMargin +
      """[2]>-2.0>[4], [4]>0.0>[0]"""

    val rawSource = "S"
    val verbose   = false

    val bfiter = BellmanFordRunner.run(
      rawGraph  = rawGraph,
      rawSource = rawSource,
      verbose   = verbose
    )
    val totalIters = bfiter.n - 1
    totalIters shouldBe 4
    bfiter.convergedIter shouldBe 4
    bfiter.pp shouldBe
      "[4]>~>*[0]-8.0, [0]>~>[1]-5.0, [0]>~>[2]-6.0, [1]>~>[3]-7.0, [2]>~>[4]-8.0"
    bfiter.negativeWeightCycles.isEmpty shouldBe false
    // println(bfiter.negativeWeightCycles.pp)
    bfiter.negativeWeightCycles.pp shouldBe "[0]>1.0>[1], [0]>1.0>[2]"
    bfiter.negativeWeightCycles shouldBe Array(
      SimpleEdge(0, 1, 1),
      SimpleEdge(0, 2, 1)
    )
  }

}
