package zzz.trie

// trie/test:runMain zzz.trie.TestMain_01_SimpleTrie
object TestMain_01_SimpleTrie extends App {

  // example: counting words

//  import SimpleTrie.defaultUpdate
  implicit def updateCount(count1: Option[Int], count2: Option[Int]): Option[Int] =
    Some(Seq(count1, count2).flatten.sum)

  val trie: SimpleTrie[Int] = SimpleTrie[Int]
  println(s"My empty SimpleTrie: ${trie}")
  println(s"My empty SimpleTrieMap: ${trie.toSimpleTrieMap}")

  // insert
  val trie1: SimpleTrie[Int] = trie.insert("hello", Some(1))
  println(s"trie1:\n${trie1.toSimpleTrieMap}")

  val trie2: SimpleTrie[Int] = trie1
    .insert("hell")
    .insert("hell*", Some(2))
    .insert("hell?", Some(2))
    .insert("two words", Some(10))
    .insert("", Some(-1))
  println(s"trie2:\n${trie2.toSimpleTrieMap}")

  // search
  println("search 'he': " + trie2.search("he"))
  println("search 'hell': " + trie2.search("hell"))
  println("search 'hello': " + trie2.search("hello"))
  println("search 'hell^': " + trie2.search("hell^"))
  println("search '': " + trie2.search(""))
  println("search 'two words': " + trie2.search("two words"))

  // delete
  val trie3: SimpleTrie[Int] = trie2
    .delete("hell")
    .delete("two words")
    .delete("")
    .delete("not existing")
  println(s"trie3:\n${trie3.toSimpleTrieMap}")
  println("search 'hell': " + trie3.search("hell"))
  println("search 'hello': " + trie3.search("hello"))
  println("search 'hell^': " + trie3.search("hell^"))
  println("search '': " + trie3.search(""))
  println("search 'two words': " + trie3.search("two words"))
}
