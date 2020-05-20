val cl = ClassLoader.getSystemClassLoader
val urlcl = cl.asInstanceOf[java.net.URLClassLoader]
urlcl.getURLs.map(_.toString).sorted.toList
  //.mkString("\n")
  .foreach(println)
