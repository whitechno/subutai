ThisBuild / version := "0.1.0"
ThisBuild / organization := "whitechno.subutai"
ThisBuild / scalaVersion := library.versions.scala213

lazy val hello = (project in file("."))
  .aggregate(helloCore) // Set aggregate so that the command sent to hello is broadcast to helloCore too
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging) // sbt-native-packager
  .settings(
    name := "Hello",
    helloTask := { println("Hello!") },
    commonSettings,
    libraryDependencies ++= Seq(
      library.scalaTest % Test
    )
  )

lazy val helloCore = (project in file("core"))
  .settings(
    name := "Hello Core",
    commonSettings,
    libraryDependencies ++= Seq(
      library.playJson,
      library.gigahorse,
      library.scalaTest % Test
    )
  )

lazy val library = new {

  val versions = new {
    val scala212  = "2.12.10"
    val scala213  = "2.13.1"
    val play      = "2.8.1"
    val gigahorse = "0.5.0"
    val scalaTest = "3.1.0"
  }

  val playJson  = "com.typesafe.play" %% "play-json"        % versions.play
  val gigahorse = "com.eed3si9n"      %% "gigahorse-okhttp" % versions.gigahorse
  val scalaTest = "org.scalatest"     %% "scalatest"        % versions.scalaTest

}

lazy val commonSettings = List(
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked"
  )
)

// Defining tasks and settings
lazy val helloTask = taskKey[Unit]("An example task")
