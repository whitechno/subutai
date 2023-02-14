import sbt._
object Dependencies {
  val supportedScalaVersions: Seq[String] =
    List(Versions.scala211, Versions.scala212, Versions.scala213)
  object Versions {
    val scala210       = "2.10.7"
    val scala211       = "2.11.12"
    val scala212       = "2.12.17"
    val scala213       = "2.13.10"
    val scalatest      = "3.2.15"
    val typesafeConfig = "1.4.2"
    val jodatime       = "2.12.2"
    val json4s         = "4.0.6"
    val requests       = "0.8.0"
    val gigahorse      = "0.6.0"
    val typesafePlay   = "2.9.2"
    def scalacheck(scalaBinaryVer: String): String =
      if (scalaBinaryVer == "2.11") "1.15.2" // Last version available for Scala 2.11
      else "1.17.0"
    def breeze(scalaBinaryVer: String): String =
      if (scalaBinaryVer == "2.11") "1.0" // Last version available for Scala 2.11
      else "2.0.1-RC2"
  }
  object Library {
    import Dependencies.{ Versions => V }
    val scalatest      = "org.scalatest" %% "scalatest"        % V.scalatest
    val typesafeConfig = "com.typesafe"   % "config"           % V.typesafeConfig
    val jodatime       = "joda-time"      % "joda-time"        % V.jodatime
    val json4sCore     = "org.json4s"    %% "json4s-core"      % V.json4s
    val json4sJackson  = "org.json4s"    %% "json4s-jackson"   % V.json4s
    val json4sNative   = "org.json4s"    %% "json4s-native"    % V.json4s
    val requests       = "com.lihaoyi"   %% "requests"         % V.requests
    val gigahorse      = "com.eed3si9n"  %% "gigahorse-okhttp" % V.gigahorse
    val typesafePlayJson = "com.typesafe.play" %% "play-json" % V.typesafePlay
    def scalacheck(scalaBinaryVer: String): ModuleID =
      "org.scalacheck" %% "scalacheck" % V.scalacheck(scalaBinaryVer)
    def breeze(scalaBinaryVer: String): ModuleID =
      "org.scalanlp" %% "breeze" % V.breeze(scalaBinaryVer)
  }
}
