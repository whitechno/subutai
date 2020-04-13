package example.trie

object SimpleTrieExample {
  /* Simple Trie implementation for words built of only ascii a to z letters */
  case class SimpleTrie[V](
      value: Option[V]                    = None,
      refs: Vector[Option[SimpleTrie[V]]] = Vector.fill(SimpleTrie.alphabetLength)(None)
  ) {
    //def insert()
  }

  object SimpleTrie {
    private val alphabetLength   = 26 // 'a' to 'z'
    private val firstLetterIndex = 'a'.toInt
    def apply[V]: SimpleTrie[V]  = SimpleTrie[V]()

    def other(
        a: String,
        b: String
    )(
        c: String,
        d: String
    ) = a + b + c
    other(a = "a", b = "b")(c = "c", d = "d")
  }
}

private object SimpleTrieMain extends App {
  import SimpleTrieExample._

  val trie = SimpleTrie[Int]

  println("My empty SimpleTrie: " + trie)
}
