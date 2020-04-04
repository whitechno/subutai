/*
"Use compile server for Scala" tick box is in
Preferences > Build, Execution, Deployment > Compiler > Scala Compiler > Scala Compile Server
you can also get to this view from the speedometer-like icon in the bottom right of Intellij

"Run worksheet in the compiler process" tick box is in
Preferences > Languages & Frameworks > Scala > Worksheet (tab)

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
 */
import example.scalaVersions.VersionNumber
VersionNumber.runPrint1
