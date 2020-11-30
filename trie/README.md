Immutable Trie in Scala
=======================

A [Trie](https://en.wikipedia.org/wiki/Trie), 
(also called radix tree or prefix tree) is a kind of search tree to store 
a dynamic set or associative array where the keys are usually String. 
Each Trie starts with an empty node as the root. Every node consists of multiple 
branches. Each branch represents a possible character of keys.

See [algs4.cs.princeton.edu/52trie](
https://algs4.cs.princeton.edu/52trie/
)

Also see [implementing-immutable-trie-in-scala](
https://medium.com/@AlirezaMeskin/implementing-immutable-trie-in-scala-c0ab58fd401
)


Totally unrelated random Scala fact:
------------------------------------

```
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
```

X <: Y means type parameter X must be a subtype of type Y.  
X >: Y means the opposite, X must be a super type of Y (in both cases, X = Y is ok).  
This notation can be contrary intuition, one may think a dog is more than an animal 
(more precise of in programming terms, more services), but for the very reason it is 
more precise, there are less dogs than there are animals, the type Animal contains 
more values than the type Dog, it contains all dogs, and all ostriches too.  
So Animal >: Dog.

As for the reason why push has this signature, I'm not sure I can explain it better 
than the [page](https://www.scala-lang.org/old/node/129) the example comes from, 
but let me try.

It starts with variance. The + in class Stack[+A] means that Stack is covariant in A.
If X is a subtype of Y, Stack[X] will be a subtype of Stack[Y]. 
A stack of dogs is also a stack of animals. For the mathematically inclined, 
if one sees Stack as a function from type to type 
(X is a type, if you pass it to Stack, you get Stack[X], which is another type), 
being covariant means that it is an increasing function 
(with <:, the subtyping relation being the orders on types).

This seems right, but this is not such an easy question. 
It would not be so, with a push routine that modifies it, adding a new element, 
that is
```
def push(a: A): Unit
```
(the example is different, push returns a new stack, leaving this unchanged). 
Of course, a Stack[Dog] should only accept dogs to be pushed into it. 
Otherwise, it would no longer be a stack of dogs. But if we accept it to be treated 
as a stack of animals, we could do
```
val dogs : Stack[Dog] = new Stack[Dog]
val animals : Stack[Animal] = dogs // if we say stack is covariant
animals.push(ostrich) // allowed, we can push anything in a stack of any. 
val topDog: Dog = dogs.top  // ostrich!
```
Clearly, treating this stack as covariant is unsound. When the stack is seen as 
a Stack[Animal], an operation is allowed that would not be on Stack[Dog]. 
What was done here with push can be done with any routine that takes A as its 
argument. If a generic class is marked as covariant, with C[+A], then A cannot be 
the type of any argument of any (public) routine of C, and the compiler will enforce 
that.

But the stack in the exemple is different. We would have a 
`def push(a: A): Stack[A]`. 
If one calls push, one gets a new stack, and the original stack is left unchanged, 
it is still a proper Stack[Dog], whatever may have been pushed. 
If we do
```
val newStack = dogs.push(ostrich)
```
dogs is still the same and still a Stack[Dog]. Obviously newStack is not. Nor is it 
a Stack[Ostrich], because it also contains the dogs that were (and still are) in the 
original stack. But it would be a proper Stack[Animal]. 
If one pushes a cat, it would be more precise to say it is a Stack[Mammal] 
(while being a stack of animals too). 
If one pushes 12, it will be only a Stack[Any], the only common supertype of Dog and 
Integer. The problem is that the compiler has no way to know that this call is safe, 
and will not allow the `a: A` argument in `def push(a: A): Stack[A]` 
if Stack is marked covariant. If it stopped there, a covariant stack would be useless 
because there would be no way to put values in it.

The signature solves the problem:
```
def push[B >: A](elem: B): Stack[B]
```
If B is an ancestor of A, when adding a B, one gets a Stack[B]. 
So adding a Mammal to a Stack[Dog] gives a Stack[Mammal], adding an animal gives a 
Stack[Animal], which is fine. Adding a Dog is ok too, A >: A is true.

This is good, but seems too restrictive. 
What if the added item's type is not an ancestor of A? 
For instance, what if it is a descendant e.g dogs.push(goldenRetriever). 
One cannnot take B = GoldenRetriever, one has not GoldenRetriever >: Dog, 
but the opposite. Yet, one can take B = Dog all right. 
If the parameter elem is expected to be of type Dog, we can pass of course 
a GoldenRetriever. One gets a stack of B, still a stack of dogs. And it is right 
that B = GoldenRetriever was not allowed. 
The result would have been typed as Stack[GoldenRetriever], which would be wrong 
because the stack may have contained irish setters too.

What about ostrishes? Well Ostrich is neither a supertype, nor a subtype of Dog.
But just as one can add a goldenRetriever because it is a dog, and it is possible 
to add a dog, an ostrich is an animal, and it is possible to add an animal. 
So taking B = Animal >: Dog works, and so when pushing an ostrich, 
one gets a Stack[Animal].

Making the stack covariant force this signature, more complex than the 
naïve push(a: A) : Stack[A]. But we gain a routine that is completely flexible, 
anything can be added, not just an A, and yet, types the result as precisely 
as can be. And the actual implementation, except for the types declarations, 
is the same it would have been with push(a: A).
