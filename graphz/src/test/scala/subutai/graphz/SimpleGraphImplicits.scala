package subutai.graphz

import org.scalactic.Equality
import org.scalactic.TripleEquals._

object SimpleGraphImplicits {

  // Graph Equality for SimpleGraph[String, Double]

  implicit val graphEq: Equality[SimpleGraph[String, Double]] =
    (a: SimpleGraph[String, Double], b: Any) =>
      b match {
        case g: SimpleGraph[_, _] => a.vertices === g.vertices && a.edges === g.edges
        case _                    => false
      }

  // PP = Pretty Print

  implicit class SimpleGraphPP(val graph: SimpleGraph[_, _]) extends AnyVal {
    def pp: String = graph.vertices.pp + "\n" + graph.edges.pp
  }

  implicit class SimpleVertexPP(val vertex: SimpleVertex[_]) extends AnyVal {
    def pp: String =
      if (vertex.attr == null) s"${vertex.id}" else s"${vertex.attr}[${vertex.id}]"
  }
  implicit class SimpleVerticesPP[VD](val vertices: Array[SimpleVertex[VD]])
      extends AnyVal {
    def pp: String = vertices.map(_.pp).mkString(", ")
  }

  implicit class SimpleEdgePP(val edge: SimpleEdge[_]) extends AnyVal {
    def pp: String =
      s"[${edge.srcId}]>" +
        (if (edge.attr == null) "-" else s"${edge.attr}") +
        s">[${edge.dstId}]"

  }
  implicit class SimpleEdgesPP[ED](val edges: Array[SimpleEdge[ED]]) extends AnyVal {
    def pp: String = edges.map(_.pp).mkString(", ")
  }
}
