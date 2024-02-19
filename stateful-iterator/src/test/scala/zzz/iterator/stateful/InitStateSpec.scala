package zzz.iterator.stateful

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InitStateSpec extends AnyFlatSpec with Matchers {

  "InitState apply with empty value" should
    "make valid instance of InitState[String]" in {
      implicit val stringInitState: InitState[String] = InitState("")
      stringInitState.empty shouldBe ""
      implicitly[InitState[String]].empty shouldBe ""
      InitState[String].empty shouldBe ""
    }

  "Numeric implicits" should "always be available in Scala" in {
    implicitly[Numeric[Int]].zero shouldBe 0
    implicitly[Numeric[Int]].one shouldBe 1
    implicitly[Numeric[Int]].zero shouldBe 0
  }

  "Provided NumericInit instance" should "make InitState available for numerics" in {
    implicit val intInitState: InitState[Int] = InitState.Implicits.NumericInit[Int]
    intInitState.empty shouldBe 0
    implicitly[InitState[Int]].empty shouldBe 0
    InitState[Int].empty shouldBe 0
  }

  "Importing OptionInit instance" should
    "provide implicit value for evidence parameter of any Option type" in {
      import InitState.Implicits.OptionInit
      InitState[Option[Int]].empty shouldBe None
    }

  "Importing IterableInit instance" should
    "provide implicit value for evidence parameter of any Iterable type" in {
      import InitState.Implicits.IterableInit
      InitState[Iterable[Int]].empty shouldBe Nil
    }

  "Importing MapInit instance" should
    "provide implicit value for evidence parameter of any Map type" in {
      import InitState.Implicits.MapInit
      InitState[Map[String, Double]].empty shouldBe Map.empty[String, Double]
    }

}
