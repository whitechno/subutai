package zzz.iterator

// import zzz.iterator.IteratorImplicits._
object IteratorImplicits {

  implicit final class IteratorOps[A](val iter: Iterator[A]) extends AnyVal {

    /** Equivalent to: {{{if (iter.hasNext) Some(iter.next()) else None}}} This
      * always consumes 1 element of [[iter]] unless it's empty.
      */
    def nextOption(): Option[A] = if (iter.hasNext) Some(iter.next()) else None

  }

}
