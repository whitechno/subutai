package subutai.graphz

/**
 * Represents Weighted Directed Graph in a compact format.
 * @param vertices
 *   Vertex labels as Strings, must be unique. If vertex is in edges, it could be
 *   omitted from vertices. Only single disconnected vertices must always be included
 *   here.
 * @param edges
 *   Directed edges with weights represented as adjacency lists.
 */
case class RawWeightedDiGraph(
    vertices: Seq[String]                       = Nil,
    edges: Seq[(String, Seq[(String, Double)])] = Nil
) {
  // all vertex labels, appropriately sorted
  lazy val vs: Array[String] = {
    vertices ++ (edges.map(_._1) ++ edges.flatMap(_._2.map(_._1))).distinct.sorted
  }.distinct.toArray

  // vertex label to id map
  lazy val vm: Map[String, Int] = vs.zipWithIndex.toMap

  // vertices as an array of SimpleVertex
  private def svs: Array[SimpleVertex[String]] =
    vs.zipWithIndex.map { case (v, id) => SimpleVertex(id, v) }

  // edges as an array of SimpleEdge
  private def ses: Array[SimpleEdge[Double]] = {
    for {
      (srcV, dsts) <- edges
      (dstV, w)    <- dsts
    } yield SimpleEdge(vm(srcV), vm(dstV), w)
  }.toArray // .sortBy { se => (se.srcId, se.dstId) }

  // full graph as SimpleGraph
  lazy val graph = new SimpleGraph[String, Double](vertices = svs, edges = ses)
}
