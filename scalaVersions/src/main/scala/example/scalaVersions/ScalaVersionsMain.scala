package example.scalaVersions

object ScalaVersionsMain extends App {
  //sys.props.toSeq.foreach(println)
  println("Scala version: " + VersionNumber.run)

}

object VersionNumber {
  def run      = util.Properties.versionNumberString
  def runPrint1 = println("Test 1 Scala version: " + run)
}
