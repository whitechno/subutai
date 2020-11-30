package zzz.stack

class Stack[+A] {
  import scala.sys.error
  def push[B >: A](elem: B): Stack[B] = new Stack[B] {
    override def top: B           = elem
    override def pop: Stack[B]    = Stack.this
    override def toString: String = elem.toString + " | " + Stack.this.toString()
  }
  def top: A                    = error("no element on stack")
  def pop: Stack[A]             = error("no element on stack")
  override def toString: String = ""
}

private object VariancesTest extends App {
  var s: Stack[Any] = new Stack().push("hello");
  s = s.push(new Object())
  s = s.push(7)
  println(s)
}
