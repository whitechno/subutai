package example.scalaVersions

// scalaVersions / runMain example.scalaVersions.ScalaVersionsMain
private object ScalaVersionsMain extends App {
  //sys.props.toSeq.foreach(println)
  println("ScalaVersions Main: " + VersionNumber.run)

}

object VersionNumber {
  def run: String     = util.Properties.versionNumberString
  def runPrint1: Unit = println("Test 1 Scala version: " + run)
}
