import Dependencies.{ Library => L, Versions => V }
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.whitechno.subutai"
ThisBuild / scalaVersion := V.scala213

// You can use -Dsbt.launcher.coursier=false to opt out of using Coursier
// and used Apache Ivy instead.
//ThisBuild / useCoursier := false
// rm ~/.sbt/.credentials; rm ~/.sbt/repositories
//ThisBuild / resolvers += Resolver.mavenCentral
//ThisBuild / resolvers += Resolver.sbtPluginRepo("releases")

//
// *** projects
//

// *** root project
lazy val subutai = (project in file("."))
// Set aggregate so that the command sent to hello is broadcast to helloCore too
  .aggregate(
    helloCore,
    algs4j,
    breeze,
    graphz,
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
      L.scalatest % Test
    )
  )

// *** helloCore project
lazy val helloCore = (project in file("core"))
  .settings(
    name := "Hello Core",
    commonSettings,
    libraryDependencies ++= Seq(
      L.typesafePlayJson,
      L.gigahorse,
      L.scalatest % Test
    )
  )

lazy val algs4j = project

// *** scalanlp breeze project
lazy val breeze = project
  .settings(
    scalaVersion := V.scala213,
    // breeze version 2.0 changed many APIs and is no longer compatible
    // with version 1.0 - the last version still available for Scala 2.11
    crossScalaVersions := List(V.scala212, V.scala213),
    commonSettings,
    libraryDependencies ++= Seq(
      L.breeze(scalaBinaryVersion.value),
      L.scalatest                            % Test,
      L.scalacheck(scalaBinaryVersion.value) % Test
    )
  )

lazy val graphz = project
  .settings(
    scalaVersion := V.scala213,
    commonSettings,
    libraryDependencies ++= Seq(
      L.scalatest % Test
    )
  )

// *** hocon project
// sbt> +hocon/assembly
// $ jar tvf hocon/target/scala-2.12/hocon-assembly_2.12-0.1.0-SNAPSHOT.jar
// 5,434,345 B with Scala, and 5,331 B without Scala
lazy val hocon = project
  .settings(
    scalaVersion       := V.scala212,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      L.typesafeConfig % "provided"
    ),
    assembly / assemblyOption ~= { _.withIncludeScala(includeScala = false) },
    assemblyJarName :=
      s"${name.value}-assembly_${scalaBinaryVersion.value}-${version.value}.jar"
  )

// *** joda-time based project
lazy val `jebe-time` = project
  .settings(
    scalaVersion       := V.scala212,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      L.jodatime  % Test,
      L.scalatest % Test
    )
  )

// json4s-jackson project
lazy val json4s = project
  .settings(
    scalaVersion       := V.scala212,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      L.json4sJackson % Test,
      L.scalatest     % Test
    )
  )

// *** scala-check project
lazy val `scala-check` = project
  .settings(
    scalaVersion       := V.scala213,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings,
    libraryDependencies ++= Seq(
      L.scalacheck(scalaBinaryVersion.value) % Test
    )
  )

// *** scala-versions project
lazy val `scala-versions` = project
  .settings(
    baseDirectoryTask := {
      val baseDir = baseDirectory.value.toString
      println("baseDirectoryTask:\n\t" + baseDir)
    },
    scalaVersion       := V.scala212,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings
  )

// *** trie project
lazy val trie = project
  .settings(
    scalaVersion       := V.scala213,
    crossScalaVersions := V.supportedScalaVersions,
    commonSettings
  )

//
// Settings and Tasks
//

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
    Resolver.sonatypeRepo("public"), // (or “snapshots”, “staging”, “releases”)
    Resolver.sonatypeRepo("snapshots"), // (or “snapshots”, “staging”, “releases”)
    Resolver.typesafeRepo("releases"), // (or “snapshots”)
    Resolver.typesafeIvyRepo("releases"), // (or “snapshots”)
    Resolver.sbtPluginRepo("releases"), // (or “snapshots”)
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
