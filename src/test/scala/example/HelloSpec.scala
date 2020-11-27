package example

import org.scalatest.{ diagrams, funsuite }

// testOnly example.HelloSpec
class HelloSpec extends funsuite.AnyFunSuite with diagrams.Diagrams {
  test("Hello should start with H") {
    assert("Hello".startsWith("H"))
  }
}
