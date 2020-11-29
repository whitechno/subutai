package zzz.scalacheck

object TestMain_00_Run_All extends App {
  Seq(
    TestMain_01_QuickExamples_Intro,
    TestMain_02_QuickExamples_Labeling,
    TestMain_03_QuickExamples_Generators,
    TestMain_04_QuickExamples_TreeGenerator,
    TestMain_05_QuickExamples_Arbitrary,
    TestMain_06_QuickExamples_ArbitraryTree,
    TestMain_07_QuickExamples_CollectingGeneratedData
  ).foreach { app =>
    println(s"\n\t>>> ${app.getClass.getSimpleName}\n")
    app.run()
  }
}
