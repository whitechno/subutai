package subutai.graphz

import scala.reflect.ClassTag

class SimpleGraph[VD: ClassTag, ED: ClassTag](
    @transient val vertices: Array[SimpleVertex[VD]] = Array.empty[SimpleVertex[VD]],
    @transient val edges: Array[SimpleEdge[ED]]      = Array.empty[SimpleEdge[ED]]
) extends Serializable

case class SimpleVertex[
    @specialized(Char, Int, Boolean, Byte, Long, Float, Double) VD
](
    var id: Int  = 0,
    var attr: VD = null.asInstanceOf[VD]
) extends Serializable

case class SimpleEdge[
    @specialized(Char, Int, Boolean, Byte, Long, Float, Double) ED
](
    var srcId: Int = 0,
    var dstId: Int = 0,
    var attr: ED   = null.asInstanceOf[ED]
) extends Serializable
