subutai
=======
Experiments with SBT.

Simplest SBT project setup (with new github repo)
-------------------------------------------------
1. Create repository (like `subutai`)
2. In it `touch build.sbt`
3. Set scala version(s) in build.sbt
   Add this line to build.sbt:  
   `ThisBuild / scalaVersion := "2.13.2"`
3. Create `project/build.properties` with the following content:  
   `sbt.version = 1.3.12`.  
   Use `sbt> reboot` if changed.
4. If needed, add `.gitignore` and `LICENSE`

Now you can clone this new repository on your desktop with  
`git clone https://github.com/whitechno/subutai.git`,  
and open and edit it with your favorite IDE.

Now you have a minimal setup to start using Scala with SBT.

You can also switch scalaVersion temporarily:  
`sbt:Hello>  ++2.12.11! -v`  
`!` is to force version switch (without it, 
the switch happens only in projects where this particular version is included 
in `crossScalaVersions` of project `settings`)  
`-v` is for verbose


Versions of key dependencies
----------------------------

- Scala  
  https://github.com/scala/scala/releases
  - 2.13.3  - Jun 25, 2020
  - 2.13.2  - Apr 22, 2020
  - 2.13.1  - Sep 18, 2019
  - 2.12.11 - Mar 16, 2020
  - 2.12.10 - Sep 10, 2019
  - 2.11.12 - Nov  9, 2017
  - 2.10.7  - Nov  9, 2017
- SBT  
  https://github.com/sbt/sbt/releases
  - 1.3.13 - Jun 27, 2020
  - 1.3.12 - May 30, 2020
  - 1.3.11 - May 29, 2020  
    There was a regression in sbt 1.3.11. Please use sbt 1.3.12 instead.
  - 1.3.10 - Apr 14, 2020
  - 1.3.9  - Mar 31, 2020
  - 1.3.8  - Feb  3, 2020
- ScalaTest  
  https://www.scalatest.org/  
  https://github.com/scalatest/scalatest/releases
  - 3.2.0 - Jun 20, 2020
  - 3.1.2 - May 13, 2020
  - 3.1.1 - Feb 20, 2020
  - 3.1.0 - Nov 28, 2019
- typesafe config
  https://github.com/lightbend/config/releases
  - 1.4.0 - Oct 14, 2019
  - 1.3.4 - Apr 18, 2019
  - 1.3.0 - May  8, 2015
  - 1.2.1 - May  2, 2014
  - 1.2.0 - Jan 15, 2014
- Scalafmt  
  https://github.com/scalameta/scalafmt  
  https://github.com/scalameta/scalafmt/releases
  - 2.6.2 - Jul  5, 2020
  - 2.6.1 - Jun 19, 2020
  - 2.6.0 - Jun 16, 2020
  - 2.5.3 - May 25, 2020
  - 2.5.2 - May  9, 2020
  - 2.5.1 - May  3, 2020
  - 2.5.0 - Apr 30, 2020
  - 2.5.0-RC3 - Apr 28, 2020
  - 2.5.0-RC2 - Apr 25, 2020
  - 2.5.0-RC1 - Apr 15, 2020
  - 2.4.2 - Feb 22, 2020
- sbt-scalafmt (Scalafmt sbt plugin)  
  https://github.com/scalameta/sbt-scalafmt  
  https://github.com/scalameta/sbt-scalafmt/releases  
  - 2.4.0 - May 14, 2020
  - 2.3.4 - Apr 10, 2020
  - 2.3.3 - Apr 10, 2020
  - 2.3.2 - Mar  5, 2020
  - 2.3.1 - Jan 27, 2020
  - 2.3.0 - Dec  9, 2019
- sbt-assembly  
  https://github.com/sbt/sbt-assembly  
  https://github.com/sbt/sbt-assembly/releases
  - 0.15.0  - Jun 22, 2020
  - 0.14.10 - Jul 11, 2019
  - 0.14.9  - Oct 28, 2018


Important techniques
--------------------

### Checking SBT, Scala, Java version

SBT:  
`> sbt about`  
`> sbt -v`  
`> sbt sbtVersion`  

Scala:  
`> sbt scalaVersion`

To see which version of Java does SBT use:  
`> sbt "eval System.getProperty(\"java.version\")" "eval System.getProperty(\"java.home\")"`  
or in sbt shell:  
`sbt:subutai> eval System.getProperty("java.version")`   
`sbt:subutai> eval System.getProperty("java.home")`  

### scalafmt Task keys

 - `myproject/scalafmt`: Format main sources of myproject project
 - `myproject/test`:scalafmt: Format test sources of myproject project
 - `scalafmtCheck`: Check if the scala sources under the project has been formatted.
 - `scalafmtSbt`: Format *.sbt and project/*.scala files.
 - `scalafmtSbtCheck`: Check if the files has been formatted by scalafmtSbt.
 - `scalafmtOnly`: Format a single given file.
 - `scalafmtAll`: Execute the scalafmt task for all configurations in which it is enabled.  
   (By default this means the Compile and Test configurations.) (available as of v2.0.0-RC5)
 - `scalafmtCheckAll`: Execute the scalafmtCheck task for all configurations in which it is enabled.  
   (By default this means the Compile and Test configurations.) (available as of v2.0.0-RC5)

### clean;compile;test;run

Run one particular main class:  
`sbt:subutai> runMain example.Hello`

For code in `test` folder:  
`sbt:subutai> test:compile`

Run one particular main class in `test` folder of `scalaVersions` project:  
`sbt:subutai> scalaVersions / test:runMain example.scalaVersions.TestScalaVersionsMain`

Run one particular test class (of "scalatest" kind):  
`sbt:subutai> testOnly example.HelloSpec`

### Package

`sbt:hocon> package` to generate JAR in 
`target/scala-2.12/hocon_2.12-0.1.0.jar`  
You can list its contents with the usual jar tvf command:
```text
$ jar tvf target/scala-2.12/hocon_2.12-0.1.0.jar
   271 Wed Apr 29 19:15:58 PDT 2020 META-INF/MANIFEST.MF
     0 Wed Apr 29 19:15:58 PDT 2020 example/
     0 Wed Apr 29 19:15:58 PDT 2020 example/hocon/
   854 Wed Apr 29 19:14:24 PDT 2020 example/hocon/ReadConfigurationFile.class
  1020 Wed Apr 29 19:14:24 PDT 2020 example/hocon/ReadConfigurationFile$.class
   130 Wed Apr 29 19:15:58 PDT 2020 lightbend.conf
```

To build against all versions listed in `crossScalaVersions`, 
prefix the action to run with `+`. For example:  
`> + package`

Assembly
--------

- https://github.com/sbt/sbt-assembly 

Add  
`addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")`  
to `project/plugins.sbt`

`sbt:hocon> assembly` to generate assembly JAR in  
`target/scala-2.12/hocon-assembly-0.1.0.jar`  
You can list its contents with the usual jar tvf command:
```text
$ jar tvf target/scala-2.12/hocon-assembly-0.1.0.jar
```

Add `test in assembly := {}` to project settings to skip the test during assembly.

To exclude Scala library 
(JARs that start with `scala-` and are included in the binary 
Scala distribution) 
to run with `scala` command:
```text
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
```

To make a JAR file containing only the external dependencies, type  
`> assemblyPackageDependency`  
This is intended to be used with a JAR that only contains your project:
```text
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false)

```

### Spark & Assembly

[SBT Assembly, Spark](
https://coderwall.com/p/6gr84q/sbt-assembly-spark-and-you
)


Publishing
----------

The `publishLocal` task will publish to the “local” Ivy repository. 
By default, this is at `$HOME/.ivy2/local/`. 
Other builds on the same machine can then list the project as a dependency. 

The `publish` action is used to publish your project to a remote repository. 
To use publishing, you need to specify the repository to publish to and the credentials to use.

- scala-sbt's [SBT Publishing](https://www.scala-sbt.org/1.x/docs/Publishing.html)

### Sonatype OSSRH (OSS Repository Hosting)

- sonatype's [Producers Guide to Publishing to Central Maven Repository](
https://central.sonatype.org/pages/producers.html
)

- sonatype's [OSSRH Guide](https://central.sonatype.org/pages/ossrh-guide.html)

- github's [Nexus Repository Publisher for GitHub Actions](
https://github.com/marketplace/actions/nexus-repository-publisher-for-github-actions
)

- jenkov's [Publish JAR To Central Maven Repository with maven](
http://tutorials.jenkov.com/maven/publish-to-central-maven-repository.html
) Jan 25, 2020

- Leonard Ehrenfried's [An in depth guide to deploying to Maven Central with sbt](
https://leonard.io/blog/2017/01/an-in-depth-guide-to-deploying-to-maven-central/
) Jan 15, 2017

- dzone's [How to Publish Your Artifacts to Maven Central](
https://dzone.com/articles/publish-your-artifacts-to-maven-central
) Sep 20, 2016

- lightbend/config [example of publishing to sonatype with sbt](
https://github.com/lightbend/config/blob/master/build.sbt
)

- circe/circe-config [example of publishing to sonatype with sbt](
https://github.com/circe/circe-config/blob/master/build.sbt
)

Step-by-step
- Created Sonatype JIRA account and filed JIRA issue to claim my namespace  
  https://issues.sonatype.org/browse/OSSRH-58349
- Created a public repo called  
  https://github.com/whitechno/OSSRH-58349  
  to verify github account ownership.
- com.github.whitechno has been prepared, now user(s) whitechno can:
  - Deploy snapshot artifacts into repository  
    https://oss.sonatype.org/content/repositories/snapshots
  - Deploy release artifacts into the staging repository  
    https://oss.sonatype.org/service/local/staging/deploy/maven2
  - Release staged artifacts into repository 'Releases'  
  Please comment on this ticket when you promoted your first release, thanks


Cross-building
--------------

https://www.scala-sbt.org/release/docs/Cross-Build.html

The underlying mechanism used to indicate which version of Scala a library 
was compiled against is to append `_<scala-binary-version>` to the library’s name. 
For example, the artifact name `dispatch-core_2.12` is used 
when compiled against Scala 2.12.0, 2.12.1 or any 2.12.x version. 
This fairly simple approach allows interoperability with users of 
Maven, Ant and other build tools.

Define the versions of Scala to build against in the `crossScalaVersions` setting. 
Versions of Scala 2.10.2 or later are allowed.

*Note:* `crossScalaVersions` must be set to Nil on the root project 
(or all aggregating projects) to avoid double publishing.

To build against all versions listed in `crossScalaVersions`, 
prefix the action to run with `+`. For example:  
`> + test`

You can use `++ <version> [command]` to temporarily switch the Scala version 
currently being used to build the subprojects given that `<version>` is listed 
in their `crossScalaVersions`.

When a `[command]` is passed in to `++`, it will execute the command on the 
subprojects that supports the given `<version>`.

Sometimes you might want to force the Scala version switch regardless of the 
`crossScalaVersions` values. 
You can use `++ <version>!` with exclamation mark for that. For example:  
`> ++ 2.13.0-M5! -v`


<hr/>

Other Special topics
====================

Reading resources
-----------------

See stackoverflow's [How to read files from resources folder in Scala?](
https://stackoverflow.com/questions/27360977/how-to-read-files-from-resources-folder-in-scala
)

The idea is to use `getClass.getResourceAsStream` because it works fine 
when the resources are part of a jar.
```scala
val stream: java.io.InputStream = getClass.getResourceAsStream("/readme.txt")
val lines: Iterator[String] = scala.io.Source.fromInputStream( stream ).getLines

```


Scala Worksheets in IntelliJ
----------------------------

"Use compile server for Scala" tick box is in  
`Preferences > Build, Execution, Deployment > Compiler > Scala Compiler > Scala Compile Server`  
you can also get to this view from the speedometer-like icon 
in the bottom right of IntelliJ, 
which can be used to Stop/Run Scala Compile Server.

"Run worksheet in the compiler process" tick box is in  
`Preferences > Languages & Frameworks > Scala > Worksheet (tab)`  
A typical way to use this feature is to do development on a single Scala version 
(no `+` prefix) and then cross-build (using `+`) occasionally and when releasing.

#### Experiments
All experiments below are with
"Make project before run": On

Experiment 1  
Run type: REPL  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": On  
Result: doesn't pick the change even with restart

Experiment 2  
Run type: REPL  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": Off  
Result: doesn't pick the change even with restart

Experiment 3  
Run type: REPL  
"Use compile server for Scala": Off  
Result: REPL doesn't work in this mode at all

Experiment 4  
Run type: Plain  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": On  
Result: picks the change after restart

Experiment 5  
Run type: Plain  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": Off  
Result: always picks the change, no need to restart

Experiment 6  
Run type: Plain  
"Use compile server for Scala": Off  
Result: always picks the change (feels a tiny bit slower)

#### Final recommendations.
For dev/test/debug cycle:  
"Make project before run": On  
Run type: Plain  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": Off

For exploring stable libraries:  
"Make project before run": On (?)  
Run type: REPL  
"Use compile server for Scala": On  
"Run worksheet in the compiler process": On


Slow start because of "Getting the hostname ... was slow"
---------------------------------------------------------

Sometimes the startup time for test and run might be pretty long and you might see 
the following command:  
`[warn] Getting the hostname Olegs-MacBook-Pro was slow (18020.887197 ms). 
This is likely because the computer's hostname is not set. 
You can set the hostname with the command: scutil --set HostName $(scutil --get LocalHostName).`

I found the following solution.

I used to have:
```
$ scutil --get HostName
Olegs-MacBook-Pro
$ scutil --get LocalHostName
Olegs-MacBook-Pro
$ time python -c 'import socket; socket.getfqdn()'
  real	0m18.134s
  user	0m0.037s
  sys	0m0.025s
```

Then I changed:
```text
$ sudo scutil --set HostName Olegs-MacBook-Pro.local
    real    0m0.047s
    user    0m0.030s
    sys     0m0.012s
```
and then added into `/etc/hosts`
```text
127.0.0.1   localhost Olegs-MacBook-Pro.local
::1         localhost Olegs-MacBook-Pro.local
```


SBT by example
--------------

See and try https://www.scala-sbt.org/release/docs/sbt-by-example.html

In the `subutai` directory create nested directories and `.scala` file:  
`src/main/scala/example/Hello.scala`  
In SBT shell:  
`sbt:Hello> clean;compile;test;run`

### Use Scala REPL
```
sbt:Hello> console
[info] Starting scala interpreter...
Welcome to Scala 2.12.7 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_171).
Type in expressions for evaluation. Or try :help.

scala> :paste
// Entering paste mode (ctrl-D to finish)

import scala.concurrent._, duration._
import gigahorse._, support.okhttp.Gigahorse
import play.api.libs.json._

Gigahorse.withHttp(Gigahorse.config) { http =>
  val baseUrl = "https://www.metaweather.com/api/location"
  val rLoc = Gigahorse.url(baseUrl + "/search/").get.
    addQueryString("query" -> "New York")
  val fLoc = http.run(rLoc, Gigahorse.asString)
  val loc = Await.result(fLoc, 10.seconds)
  val woeid = (Json.parse(loc) \ 0 \ "woeid").get
  val rWeather = Gigahorse.url(baseUrl + s"/$woeid/").get
  val fWeather = http.run(rWeather, Gigahorse.asString)
  val weather = Await.result(fWeather, 10.seconds)
  ({Json.parse(_: String)} andThen Json.prettyPrint)(weather)
}

// press Ctrl+D

// Exiting paste mode, now interpreting.

import scala.concurrent._
import duration._
import gigahorse._
import support.okhttp.Gigahorse
import play.api.libs.json._
res0: String =
{
  "consolidated_weather" : [ {
    "id" : 6446939314847744,
    "weather_state_name" : "Light Rain",
    "weather_state_abbr" : "lr",
    "wind_direction_compass" : "WNW",
    "created" : "2019-02-21T04:39:47.747805Z",
    "applicable_date" : "2019-02-21",
    "min_temp" : 0.48000000000000004,
    "max_temp" : 7.84,
    "the_temp" : 2.1700000000000004,
    "wind_speed" : 5.996333145703094,
    "wind_direction" : 293.12257757287307,
    "air_pressure" : 1033.115,
    "humidity" : 77,
    "visibility" : 14.890539250775472,
    "predictability" : 75
  }, {
    "id" : 5806299509948416,
    "weather_state_name" : "Heavy Cloud",
...

scala> :q // to quit
```

### Use sbt-native-packager to create .zip distribution
In `project/plugins.sbt` add:  
`addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.4")`  
In `build.sbt` add:  
`.enablePlugins(JavaAppPackaging)`
In SBT shell:
```
sbt:Hello> reload
sbt:Hello> dist
```
In terminal shell:
```
> unzip -o -d /tmp/someother /tmp/foo-build/target/universal/hello-0.1.0-SNAPSHOT.zip
> /tmp/someother/hello-0.1.0-SNAPSHOT/bin/hello
```

### run 'evicted'
```
[warn] There may be incompatibilities among your library dependencies; 
run 'evicted' to see detailed eviction warnings.
```
With some plugins (like `sbt-native-packager`), sbt might show eviction warning on sbt shell start,
however `> evicted` command shows nothing.

Remember that [SBT is recursive](https://www.scala-sbt.org/1.x/docs/Organizing-Build.html#sbt+is+recursive) - 
that means, SBT uses SBT to build the SBT used to build your project.

The eviction warning you are seeing is not for your main project, but for the meta-project!
You can check that by running the evicted command in the meta project using the `reload plugins` command.
To return to the main project execute the `reload return` command
```
$ sbt
[warn] There may be incompatibilities among your library dependencies; 
run 'evicted' to see detailed eviction warnings.
sbt:Hello> evicted
[success] Total time: 0 s, completed Jan 8, 2020 11:21:26 PM


sbt:Hello> reload plugins
sbt:project> evicted
[warn] Found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
[warn]  * org.scala-lang.modules:scala-xml_2.12:1.2.0 is selected over 1.1.1
[warn]      +- com.typesafe.sbt:sbt-native-packager:1.5.2 (sbtVersion=1.0, scalaVersion=2.12) (depends on 1.1.1)
[info] Here are other dependency conflicts that were resolved:
[info]  * org.scala-sbt:io_2.12:1.3.1 is selected over 1.2.2
[info]      +- com.typesafe.sbt:sbt-native-packager:1.5.2 (sbtVersion=1.0, scalaVersion=2.12) (depends on 1.2.2)
[info]  * org.scala-lang.modules:scala-parser-combinators_2.12:1.1.2 is selected over 1.1.1
[info]      +- com.typesafe.sbt:sbt-native-packager:1.5.2 (sbtVersion=1.0, scalaVersion=2.12) (depends on 1.1.1)
[success] Total time: 0 s, completed Jan 8, 2020 11:22:56 PM

sbt:project> reload return
sbt:Hello> evicted
[success] Total time: 0 s, completed Jan 8, 2020 11:25:40 PM
```

### Is it wise to fix eviction warnings of library dependencies?
Is it a good idea to fix SBT eviction warning messages using `dependencyOverrides` in build.sbt 
as per [SBT Documentation Eviction warning](
https://www.scala-sbt.org/1.x/docs/Library-Management.html#Eviction+warning
)?

If these warnings are for dependencies you use directly in your code, 
you should definitely add the upgraded version to your libraryDependencies.

For evicted transitive dependencies (those dependencies only used directly by your own dependencies), 
it's likely best to simply leave the warnings in place. This provides documentation to you about 
possible incompatibilities in your dependencies, and could help you debug runtime problems that 
arise due to such incompatibilities.

Remember, setting dependencyOverrides merely hides the warning, 
it doesn't guarantee compatibility between your libraries and the version you set.
