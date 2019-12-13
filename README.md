# subutai
Experiments with SBT

### Simplest SBT project setup
1. Create repository (like `subutai`)
2. In it `touch build.sbt`
3. Create `project/build.properties` with the following content: `sbt.version = 1.3.4` (as of November 23, 2019)
4. If needed, add `.gitignore`

Now you can clone this new repository on your desktop with `git clone https://github.com/whitechno/subutai.git`,
and open and edit it with your favorite IDE.

### Set scala version(s) in build.sbt
Add this line: `ThisBuild / scalaVersion := "2.12.10"`  

As of Dec 11, 2019:  
2.13.1 - Sep 18, 2019  
2.12.10 - Sep 10, 2019  
2.11.12 - Nov 9, 2017  
2.10.7 - Nov 9, 2017

