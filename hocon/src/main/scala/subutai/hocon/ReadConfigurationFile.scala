package subutai.hocon

import com.typesafe.config.{ Config, ConfigFactory }

object ReadConfigurationFile {
  val config: Config = ConfigFactory.load("lightbend.conf")
  val url: String    = config.getString("jdbc.url")
}
