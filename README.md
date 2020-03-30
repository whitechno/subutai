# subutai
Experiments with SBT.

### Simplest SBT project setup (with new github repo)
1. Create repository (like `subutai`)
2. In it `touch build.sbt`
3. Create `project/build.properties` with the following content: `sbt.version = 1.3.6`
4. If needed, add `.gitignore` and `LICENSE`

Now you can clone this new repository on your desktop with  
`git clone https://github.com/whitechno/subutai.git`,  
and open and edit it with your favorite IDE.

### Set scala version(s) in build.sbt
Add this line to build.sbt:  
`ThisBuild / scalaVersion := "2.13.1"`  

As of Mar 27, 2020:  
2.13.1  - Sep 18, 2019  
2.12.11 - Mar 16, 2020
2.12.10 - Sep 10, 2019  
2.11.12 - Nov 9, 2017  
2.10.7  - Nov 9, 2017

Now you have a minimal setup to start using Scala with SBT.

You can also switch scalaVersion temporarily:  
`sbt:Hello>  ++2.12.10!`

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

### SBT by example
See and try https://www.scala-sbt.org/1.x/docs/sbt-by-example.html

In the `subutai` directory create nested directories and `.scala` file:  
`src/main/scala/example/Hello.scala`  
In SBT shell:  
`sbt:Hello> clean; compile; test; run`

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

### [warn] There may be incompatibilities among your library dependencies; run 'evicted' to see detailed eviction warnings.
With some plugins (like `sbt-native-packager`), sbt might show eviction warning on sbt shell start,
however `> evicted` command shows nothing.

Remember that [SBT is recursive](https://www.scala-sbt.org/1.x/docs/Organizing-Build.html#sbt+is+recursive) - 
that means, SBT uses SBT to build the SBT used to build your project.

The eviction warning you are seeing is not for your main project, but for the meta-project!
You can check that by running the evicted command in the meta project using the `reload plugins` command.
To return to the main project execute the `reload return` command
```
$ sbt
[warn] There may be incompatibilities among your library dependencies; run 'evicted' to see detailed eviction warnings.
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
as per [SBT Documentation Eviction warning](https://www.scala-sbt.org/1.x/docs/Library-Management.html#Eviction+warning)?

If these warnings are for dependencies you use directly in your code, 
you should definitely add the upgraded version to your libraryDependencies.

For evicted transitive dependencies (those dependencies only used directly by your own dependencies), 
it's likely best to simply leave the warnings in place. This provides documentation to you about 
possible incompatibilities in your dependencies, and could help you debug runtime problems that 
arise due to such incompatibilities.

Remember, setting dependencyOverrides merely hides the warning, 
it doesn't guarantee compatibility between your libraries and the version you set.
