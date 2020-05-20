package example.classpath

private object ClassLoaderMain extends App {
//  import java.lang.ClassLoader
//  import java.net.URL
  val cl = ClassLoader.getSystemClassLoader
  cl.asInstanceOf[java.net.URLClassLoader]
    .getURLs
    .map(_.toString)
    .sorted
    .foreach(println)

}
