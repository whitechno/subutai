package example.hocon

import com.typesafe.config.{ Config, ConfigFactory }

// > hocon / runMain example.hocon.ReadConfigurationFile
object ReadConfigurationFile extends App {

  val config: Config = ConfigFactory.load("lightbend.conf")

  val driver   = config.getString("jdbc.driver")
  val url      = config.getString("jdbc.url")
  val username = config.getString("jdbc.username")
  val password = config.getString("jdbc.password")

  println(s"driver   = $driver")
  println(s"url      = $url")
  println(s"username = $username")
  println(s"password = $password")

}
