ThisBuild / version := "0.1.0"
ThisBuild / organization := "whitechno.subutai"
ThisBuild / scalaVersion := library.versions.scala213

//
// *** projects
//

// *** root project
lazy val hello = (project in file("."))
// Set aggregate so that the command sent to hello is broadcast to helloCore too
  .aggregate(helloCore) //, scalaVersions
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging) // sbt-native-packager
  .settings(
    name := "Hello",
    helloTask := { println("Hello from root project!") },
    baseDirectoryTask := {
      import java.io.File
      val baseDir = baseDirectory.value.toString
      val baseLength = baseDir.size + 1
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

// *** scalaVersions project
lazy val scalaVersions = (project in file("scalaVersions"))
  .settings(
    baseDirectoryTask := {
      val baseDir = baseDirectory.value.toString
      println("baseDirectoryTask:\n\t" + baseDir)
    },
    scalaVersion := library.versions.scala212,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings
  )

// *** trie project
lazy val trie = (project in file("trie"))
  .settings(
    scalaVersion := library.versions.scala212,
    commonSettings
  )

//
// versions and settings
//

lazy val library = new {

  val versions = new {
    val scala210 = "2.10.7"  // Nov 9, 2017 https://github.com/scala/scala/releases/tag/v2.10.7
    val scala211 = "2.11.12" // Nov 9, 2017 https://github.com/scala/scala/releases/tag/v2.11.12
    // val scala212  = "2.12.10" // Sep 10, 2019 https://github.com/scala/scala/releases/tag/v2.12.10
    val scala212  = "2.12.11" // Mar 16, 2020 https://github.com/scala/scala/releases/tag/v2.12.11
    val scala213  = "2.13.1"  // Sep 18, 2019 https://github.com/scala/scala/releases/tag/v2.13.1
    val play      = "2.8.1"   // as seen on Mar 27, 2020
    val gigahorse = "0.5.0"   // as seen on Mar 27, 2020
    val scalatest = "3.1.1"   // as seen on Mar 27, 2020
  }

  val supportedScalaVersions = List(versions.scala211, versions.scala212, versions.scala213)

  val playJson  = "com.typesafe.play" %% "play-json"        % versions.play
  val gigahorse = "com.eed3si9n"      %% "gigahorse-okhttp" % versions.gigahorse
  val scalatest = "org.scalatest"     %% "scalatest"        % versions.scalatest

}

lazy val commonSettings = List(
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature" // [warn] there were 21 feature warnings; re-run with -feature for details
  )
)

// Defining tasks and settings
lazy val helloTask         = taskKey[Unit]("An example task")
lazy val baseDirectoryTask = taskKey[Unit]("Task to print base directory")
