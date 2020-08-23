ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.whitechno.subutai"
ThisBuild / scalaVersion := library.versions.scala213

//
// *** projects
//

// *** root project
lazy val subutai = (project in file("."))
// Set aggregate so that the command sent to hello is broadcast to helloCore too
  .aggregate(helloCore) //, scalaVersions
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging) // sbt-native-packager
  .settings(
    helloTask := { println("Hello from root project!") },
    baseDirectoryTask := {
      import java.io.File
      val baseDir    = baseDirectory.value.toString
      val baseLength = baseDir.length + 1
      val projectDirs = baseDirectory
        .all(ScopeFilter(inAnyProject -- inProjects(trie)))
        .value
        .map(_.getAbsolutePath)
        .collect { case s if s.length >= baseLength => File.separator + s.substring(baseLength) }
        .mkString("\n\t\t")
      println("baseDirectoryTask:\n\t" + baseDir + "\n\t\t" + projectDirs)
    },
//    commonSettings,
    scalacOptions := { // silly experiments with SBT Tasks
      val out = streams.value // streams task happens-before scalacOptions
      val log = out.log
      log.info("123")
      val ur = update.value // update task happens-before scalacOptions
      log.info("456")
      ur.allConfigurations.take(3).map(_.toString)
    },
    libraryDependencies ++= Seq(
      library.scalatest % Test
    )
  )

// *** helloCore project
lazy val helloCore = (project in file("core"))
  .settings(
    name := "Hello Core",
    commonSettings,
    libraryDependencies ++= Seq(
      library.playJson,
      library.gigahorse,
      library.scalatest % Test
    )
  )

// *** hocon project
lazy val hocon = project
  .settings(
    scalaVersion := library.versions.scala212,
    commonSettings,
    libraryDependencies ++= Seq(
      library.typesafeConfig % "provided"
    ),
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
  )

// *** joda-time based project
lazy val `jebe-time` = project
  .settings(
    scalaVersion := library.versions.scala212,
    commonSettings,
    libraryDependencies ++= Seq(
      library.jodatime  % "provided",
      library.scalatest % Test
    ),
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
  )

// json4s-jackson project
lazy val json4s = project
  .settings(
    scalaVersion := library.versions.scala212,
    commonSettings,
    libraryDependencies ++= Seq(
      library.json4sJackson % "provided"
    ),
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
  )

// *** scalaVersions project
lazy val `scala-versions` = project
  .settings(
    baseDirectoryTask := {
      val baseDir = baseDirectory.value.toString
      println("baseDirectoryTask:\n\t" + baseDir)
    },
    scalaVersion       := library.versions.scala212,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings
  )

// *** trie project
lazy val trie = project
  .settings(
    scalaVersion := library.versions.scala212,
    commonSettings
  )

//
// versions and settings
//

lazy val library = new {

  val versions = new {
    val scala210       = "2.10.7"
    val scala211       = "2.11.12"
    val scala212       = "2.12.12"
    val scala213       = "2.13.3"
    val scalatest      = "3.2.1"
    val typesafeConfig = "1.4.0"
    val jodatime       = "2.10.6"
    val json4s         = "3.6.9"
    val gigahorse      = "0.5.0" // as seen on Mar 27, 2020
    val play           = "2.8.1" // as seen on Mar 27, 2020
  }

  val supportedScalaVersions = List(versions.scala211, versions.scala212, versions.scala213)

  val scalatest      = "org.scalatest"     %% "scalatest"        % versions.scalatest
  val typesafeConfig = "com.typesafe"       % "config"           % versions.typesafeConfig
  val jodatime       = "joda-time"          % "joda-time"        % versions.jodatime
  val json4sCore     = "org.json4s"        %% "json4s-core"      % versions.json4s
  val json4sJackson  = "org.json4s"        %% "json4s-jackson"   % versions.json4s
  val json4sNative   = "org.json4s"        %% "json4s-native"    % versions.json4s
  val gigahorse      = "com.eed3si9n"      %% "gigahorse-okhttp" % versions.gigahorse
  val playJson       = "com.typesafe.play" %% "play-json"        % versions.play

}

lazy val commonSettings = List(
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature" // [warn] there were 21 feature warnings; re-run with -feature for details
  ),
  test in assembly := {}
)

// Defining tasks and settings
lazy val helloTask         = taskKey[Unit]("An example task")
lazy val baseDirectoryTask = taskKey[Unit]("Task to print base directory")
