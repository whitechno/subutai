ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.whitechno.subutai"
ThisBuild / scalaVersion := library.versions.scala213

// You can use -Dsbt.launcher.coursier=false to opt out of using Coursier
// and used Apache Ivy instead.
//ThisBuild / useCoursier := false
ThisBuild / resolvers += Resolver.mavenCentral
ThisBuild / resolvers += Resolver.sbtPluginRepo("releases")

//
// *** projects
//

// *** root project
lazy val subutai = (project in file("."))
// Set aggregate so that the command sent to hello is broadcast to helloCore too
  .aggregate(
    helloCore,
    hocon,
    `jebe-time`,
    json4s,
    `scala-check`,
    `scala-versions`,
    trie
  )
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
        .collect {
          case s if s.length >= baseLength =>
            File.separator + s.substring(baseLength)
        }
        .mkString("\n\t\t")
      println("baseDirectoryTask:\n\t" + baseDir + "\n\t\t" + projectDirs)
    },
    scalacOptions := { // silly experiments with SBT Tasks
      val out = streams.value // streams task happens-before scalacOptions
      val log = out.log
      log.info("123")
      val ur = update.value // update task happens-before scalacOptions
      log.info("456")
      ur.allConfigurations.take(3).map(_.toString)
    },
    commonSettings,
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
// sbt> +hocon/assembly
// $ jar tvf hocon/target/scala-2.12/hocon-assembly_2.12-0.1.0-SNAPSHOT.jar
// 5,434,345 B with Scala, and 5,331 B without Scala
lazy val hocon = project
  .settings(
    scalaVersion       := library.versions.scala212,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      library.typesafeConfig % "provided"
    ),
    assembly / assemblyOption ~= { _.withIncludeScala(includeScala = false) },
    assemblyJarName :=
      s"${name.value}-assembly_${scalaBinaryVersion.value}-${version.value}.jar"
  )

// *** joda-time based project
lazy val `jebe-time` = project
  .settings(
    scalaVersion       := library.versions.scala212,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      library.jodatime  % Test,
      library.scalatest % Test
    )
  )

// json4s-jackson project
lazy val json4s = project
  .settings(
    scalaVersion       := library.versions.scala212,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      library.json4sJackson % Test,
      library.scalatest     % Test
    )
  )

// *** scala-check project
lazy val `scala-check` = project
  .settings(
    scalaVersion       := library.versions.scala213,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      library.scalacheck(scalaBinaryVersion.value) % Test
    )
  )

// *** scala-versions project
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
    scalaVersion       := library.versions.scala213,
    crossScalaVersions := library.supportedScalaVersions,
    commonSettings
  )

//
// versions and settings
//

lazy val library = new {

  val versions = new {
    val scala210       = "2.10.7"
    val scala211       = "2.11.12"
    val scala212       = "2.12.14"
    val scala213       = "2.13.6"
    val scalatest      = "3.2.9"
    val typesafeConfig = "1.4.1"
    val jodatime       = "2.10.10"
    val json4s         = "4.0.0"
    val gigahorse      = "0.5.0" // as seen on Mar 27, 2020
    val play           = "2.8.1" // as seen on Mar 27, 2020

    def scalacheck(scalaBinaryVersionString: String): String =
      scalaBinaryVersionString match {
        case "2.11" => "1.15.2" // Last version still available for Scala 2.11
        case _      => "1.15.4"
      }
  }

  val supportedScalaVersions =
    List(versions.scala211, versions.scala212, versions.scala213)

  val scalatest      = "org.scalatest"     %% "scalatest"        % versions.scalatest
  val typesafeConfig = "com.typesafe"       % "config"           % versions.typesafeConfig
  val jodatime       = "joda-time"          % "joda-time"        % versions.jodatime
  val json4sCore     = "org.json4s"        %% "json4s-core"      % versions.json4s
  val json4sJackson  = "org.json4s"        %% "json4s-jackson"   % versions.json4s
  val json4sNative   = "org.json4s"        %% "json4s-native"    % versions.json4s
  val gigahorse      = "com.eed3si9n"      %% "gigahorse-okhttp" % versions.gigahorse
  val playJson       = "com.typesafe.play" %% "play-json"        % versions.play

  def scalacheck(scalaBinaryVersionString: String): ModuleID =
    "org.scalacheck" %% "scalacheck" % versions.scalacheck(scalaBinaryVersionString)

}

lazy val commonSettings = List(
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
)

// Defining tasks and settings
lazy val helloTask         = taskKey[Unit]("An example task")
lazy val baseDirectoryTask = taskKey[Unit]("Task to print base directory")
lazy val printTask         = taskKey[Unit]("Print SBT vars")
printTask := {
  println("baseDirectory: " + baseDirectory.value)
  println("unmanagedBase: " + unmanagedBase.value)

  Seq(
    Resolver.mavenLocal,
    DefaultMavenRepository,
    Resolver.mavenCentral,
    JavaNet2Repository,
    Resolver.sonatypeRepo("public"), //(or “snapshots”, “staging”, “releases”)
    Resolver.sonatypeRepo("snapshots"), //(or “snapshots”, “staging”, “releases”)
    Resolver.typesafeRepo("releases"), //(or “snapshots”)
    Resolver.typesafeIvyRepo("releases"), //(or “snapshots”)
    Resolver.sbtPluginRepo("releases"), //(or “snapshots”)
    Resolver.bintrayRepo("owner", "repo"),
    Resolver.jcenterRepo,
    Resolver.ivyStylePatterns,
    Resolver.mavenStylePatterns
  ).foreach { s => println("\t" + s) }

  val (art, file) = (Compile / packageBin / packagedArtifact).value
  println("Artifact definition: " + art)
  println("Packaged file: " + file.getAbsolutePath)

  println("\n externalResolvers:")
  externalResolvers.value.foreach { s => println("\t" + s) }

  println("\n resolvers:")
  resolvers.value.foreach { s => println("\t" + s) }

  println("\n combineDefaultResolvers:")
  Resolver.combineDefaultResolvers(resolvers.value.toVector).foreach { s =>
    println("\t" + s)
  }
}
