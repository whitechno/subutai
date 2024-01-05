package subutai.graphz

/** Represents Directed Graph in a compact format.
  * @param vertices
  *   Vertex labels as Strings, must be unique. If vertex is in edges, it could be
  *   omitted from vertices. Only single disconnected vertices must always be
  *   included here.
  * @param edges
  *   Directed edges represented as adjacency lists.
  */
case class RawDiGraph(
    vertices: Seq[String]             = Nil,
    edges: Seq[(String, Seq[String])] = Nil
) {
  def toWeighted: RawWeightedDiGraph = RawWeightedDiGraph(
    vertices = vertices,
    edges    = edges.map { case (srcId, dstIds) => srcId -> dstIds.map(_ -> 1d) }
  )
}
