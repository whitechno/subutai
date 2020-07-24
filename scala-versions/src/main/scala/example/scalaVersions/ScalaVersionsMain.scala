package example.scalaVersions

// scala-versions / runMain example.scalaVersions.ScalaVersionsMain
private object ScalaVersionsMain extends App {
  //sys.props.toSeq.foreach(println)
  println("ScalaVersions Main: " + VersionNumber.run)
}

object VersionNumber {
  def run: String       = scala.util.Properties.versionNumberString
  def runPrint1(): Unit = println("Test 1 Scala version: " + run)
}
