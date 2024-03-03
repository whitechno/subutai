package zzz.stack

/** Simple and elegant implementation of stack */
class Stack[+A] { self => // alias for Stack.this
  import scala.sys.error
  def push[B >: A](elem: B): Stack[B] = new Stack[B] {
    override def top: B        = elem
    override def pop: Stack[B] = self // Stack.this
    override def toString: String =
      elem.toString + " | " + self.toString() // Stack.this.toString()
  }
  def top: A                    = error("no element on stack")
  def pop: Stack[A]             = error("no element on stack")
  override def toString: String = "*"
}

// trie/test:runMain zzz.stack.VariancesTest
// 7 | java.lang.Object@34610473 | hello | *
private object VariancesTest extends App {
  var s: Stack[Any] = new Stack().push("hello");
  s = s.push(new Object())
  s = s.push(7)
  println(s)
}
