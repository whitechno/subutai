package subutai.hocon.classloader

// hocon / test:runMain subutai.hocon.classloader.PrintClassLoaderMain
private object PrintClassLoaderMain extends App {
  import PrintClassLoader._

  println("\n")
  println(getClass)
  printClassLoader(getClass.getClassLoader).foreach(println)

  println("\n")
  printThisClassLoader.foreach(println)

  println("\n")
  printSystemClassLoader.foreach(println)
}
