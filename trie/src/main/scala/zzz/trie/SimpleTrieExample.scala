package zzz.trie

/** Simple Trie implementation for words built of only ascii a to z letters, and
  * using '*' for any other chars.
  *
  * TODO: add Abstract SimpleTrie
  *
  * TODO: add Ternary Search Tree
  */
case class SimpleTrie[V](
    value: Option[V]                    = None,
    refs: Vector[Option[SimpleTrie[V]]] = Vector.fill(SimpleTrie.REFS_LENGTH)(None)
) {

  def insert(s: String, v: Option[V] = None)(implicit
      update: (Option[V], Option[V]) => Option[V]
  ): SimpleTrie[V] = {
    if (s.isEmpty) this.copy(value = update(value, v))
    else {
      val (cIndex: Int, sTail: String) = (SimpleTrie.c2i(s.head), s.tail)
      this.copy(
        refs = refs.updated(
          index = cIndex,
          elem = Some(
            refs.lift(cIndex).flatten.getOrElse(SimpleTrie[V]).insert(sTail, v)
          )
        )
      )
    }
  }

  def delete(s: String): SimpleTrie[V] = {
    if (s.isEmpty) this.copy(value = None)
    else {
      val (cIndex: Int, sTail: String) = (SimpleTrie.c2i(s.head), s.tail)
      val newRef: Option[SimpleTrie[V]] =
        refs.lift(cIndex).flatten.flatMap { trie: SimpleTrie[V] =>
          val newTrie = trie.delete(sTail)
          if (newTrie == SimpleTrie[V]) None
          else Some(newTrie)
        }
      this.copy(
        refs = refs.updated(
          index = cIndex,
          elem  = newRef
        )
      )
    }
  }

  def search(s: String): Option[V] = {
    if (s.isEmpty) value
    else {
      val (cIndex: Int, sTail: String) = (SimpleTrie.c2i(s.head), s.tail)
      refs.lift(cIndex).flatten.flatMap(_.search(sTail))
    }
  }

  def toSimpleTrieMap: SimpleTrieMap[V] = SimpleTrieMap[V](
    value = value,
    refs =
      SimpleTrieMap.vector2map(refs).map { case (c, t) => c -> t.toSimpleTrieMap }
  )
}

object SimpleTrie {

  def apply[V]: SimpleTrie[V] = SimpleTrie[V]()
  implicit def defaultUpdate[T](v1: Option[T], v2: Option[T]): Option[T] =
    Seq(v1, v2).flatten.lastOption

  private val FIRST_LETTER: Int    = 'a'.toInt // =97
  private val LAST_LETTER: Int     = 'z'.toInt // =122
  private val ANY_CHAR: Char       = '*' // used for any non-a-to-z char
  private val ALPHABET_LENGTH: Int = LAST_LETTER - FIRST_LETTER + 1 // =26
  private val REFS_LENGTH: Int     = ALPHABET_LENGTH + 1 // +1 for "any" char

  /** Get index of the char */
  private[trie] def c2i(c: Char): Int = {
    val cInt = c.toInt
    if (cInt < FIRST_LETTER || cInt > LAST_LETTER) 0
    else c.toInt - FIRST_LETTER + 1
  }

  /** Get char from the index */
  private[trie] def i2c(i: Int): Char = {
    if (i == 0) ANY_CHAR
    else (i + FIRST_LETTER - 1).toChar
  }
}

/** Use [[SimpleTrieMap]] for better visualization of [[SimpleTrie]].
  *
  * E.g., empty trie `= SimpleTrie[Int]` prints out as
  * {{{
  *   SimpleTrie(None,Vector(None, None, None, None, None, None, None, None, None,
  *   None, None, None, None, None, None, None, None, None, None, None, None, None,
  *   None, None, None, None, None))
  * }}}
  * but in [[SimpleTrieMap]] representation it prints out as
  * {{{SimpleTrieMap(None,Map())}}}
  */
case class SimpleTrieMap[V](
    value: Option[V]                  = None,
    refs: Map[Char, SimpleTrieMap[V]] = Map.empty[Char, SimpleTrieMap[V]]
)

object SimpleTrieMap {

  private[trie] def vector2map[T](refs: Vector[Option[T]]): Map[Char, T] = {
    if (refs.flatten.isEmpty) Map.empty[Char, T]
    else
      (for {
        i <- refs.indices
        t <- refs(i)
      } yield SimpleTrie.i2c(i) -> t).toMap
  }

}
