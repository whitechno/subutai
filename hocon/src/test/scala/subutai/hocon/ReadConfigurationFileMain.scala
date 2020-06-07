package subutai.hocon

// > hocon / test:run
// > hocon / test:runMain subutai.hocon.ReadConfigurationFileMain
private object ReadConfigurationFileMain extends App {

  import ReadConfigurationFile.config

  val driver   = config.getString("jdbc.driver")
  val url      = config.getString("jdbc.url")
  val username = config.getString("jdbc.username")
  val password = config.getString("jdbc.password")

  println(s"driver   = $driver")
  println(s"url      = $url")
  println(s"username = $username")
  println(s"password = $password")

}
