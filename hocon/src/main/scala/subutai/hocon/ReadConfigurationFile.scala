package subutai.hocon

import com.typesafe.config.{ Config, ConfigFactory }

object ReadConfigurationFile {
  val config: Config = ConfigFactory.load("lightbend.conf")
  val url            = config.getString("jdbc.url")
}
