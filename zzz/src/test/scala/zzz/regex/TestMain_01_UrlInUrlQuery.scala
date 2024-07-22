package zzz.regex

import scala.util.matching.Regex

// zzz/test:runMain zzz.regex.TestMain_01_UrlInUrlQuery
object TestMain_01_UrlInUrlQuery extends App {

  // Ex 1

  println(
    """\w+""".r.findAllIn("A simple example.").toList
  ) // List(A, simple, example)

  // Ex 2

  val hathaway = "hathatthattthatttt"

  // Return all non-overlapping matches
  // Where potential matches overlap, the first possible match is returned,
  // followed by the next match that follows the input consumed by the first match.
  val hat = "hat[^a]+".r
  println(hat.findAllIn(hathaway).toList) // List(hath, hattth)
  val hatAllMatchIn = hat.findAllMatchIn(hathaway).toList
  println(hatAllMatchIn) // List(hath, hattth)
  println(hatAllMatchIn.map(_.start)) // List(0, 7)
  println(hatAllMatchIn.map(_.groupCount)) // List(0, 0)
  println(hatAllMatchIn.map(_.group(0))) // List(hath, hattth)

  // To return overlapping matches, it is possible to formulate a regular expression
  // with lookahead (?=) that does not consume the overlapping region.
  val madhatter           = "(h)(?=(at[^a]+))".r
  val madhatterAllMatchIn = madhatter.findAllMatchIn(hathaway).toList
  println(madhatterAllMatchIn) // List(h, h, h, h)
  val madhats = madhatterAllMatchIn.map { case madhatter(x, y) => s"$x$y" }
  println(madhats) // List(hath, hatth, hattth, hatttt)
  println(madhatterAllMatchIn.map(_.start)) // List(0, 3, 7, 12)
  println(madhatterAllMatchIn.map(_.groupCount)) // List(2, 2, 2, 2)
  println(
    madhatterAllMatchIn.map(_.subgroups)
  ) // List(List(h, ath), List(h, atth), List(h, attth), List(h, atttt))
  println(madhatterAllMatchIn.map(_.group(0))) // List(h, h, h, h)
  println(madhatterAllMatchIn.map(_.group(1))) // List(h, h, h, h)
  println(madhatterAllMatchIn.map(_.group(2))) // List(ath, atth, attth, atttt)

  // Ex 3

  val url = "https://abc.com?a=http://aaa.edu&b=ftp://xyz.com&c=3&d=http://ddd.org/"

  // bad regex
  val UrlInUrlQueryRx1: Regex = "(\\w+://[^/]+/?)".r

  val urlAllMatchIn1 = UrlInUrlQueryRx1.findAllMatchIn(url).toList
  println(urlAllMatchIn1.map(_.groupCount)) // List(1, 1)
  println(
    urlAllMatchIn1.map(_.subgroups)
  ) // List(List(https://abc.com?a=http:/), List(ftp://xyz.com&c=3&d=http:/))
  println(
    urlAllMatchIn1.map(_.group(0))
  ) // List(https://abc.com?a=http:/, ftp://xyz.com&c=3&d=http:/)
  println(
    urlAllMatchIn1.map(_.group(1))
  ) // List(https://abc.com?a=http:/, ftp://xyz.com&c=3&d=http:/)

  // good regex
  val UrlInUrlQueryRx2: Regex = "\\w+://[^/?&]+/?".r

  val urlAllMatchIn2 = UrlInUrlQueryRx2.findAllMatchIn(url).toList
  println(urlAllMatchIn2.map(_.groupCount)) // List(0, 0, 0)
  println(
    urlAllMatchIn2.map(_.subgroups)
  ) // List(List(), List(), List())
  println(
    urlAllMatchIn2.map(_.group(0))
  ) // List(https://abc.com, http://aaa.edu, ftp://xyz.com, http://ddd.org/)
  println(
    urlAllMatchIn2.map(_.matched)
  ) // List(https://abc.com, http://aaa.edu, ftp://xyz.com, http://ddd.org/)

}
