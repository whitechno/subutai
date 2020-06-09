package subutai.hocon.classloader

object PrintClassLoader {

  // java.lang.Object: public final Class<?> getClass()
  // java.lang.Class<T>: public ClassLoader getClassLoader()

  def printClassLoader(cl: java.lang.ClassLoader): Array[String] =
    cl.asInstanceOf[java.net.URLClassLoader]
      .getURLs
      .map(_.toString)
      .sorted

  def printSystemClassLoader: Array[String] = {
    println("\n ClassLoader.getSystemClassLoader:\n")
    val cl: java.lang.ClassLoader = ClassLoader.getSystemClassLoader
    printClassLoader(cl)
  }

  def printThisClassLoader: Array[String] = {
    val c: java.lang.Class[_] = getClass
    println("getClass: " + c)
    val cl: java.lang.ClassLoader = c.getClassLoader
    println("getClassLoader: ")
    printClassLoader(cl)
  }

}
