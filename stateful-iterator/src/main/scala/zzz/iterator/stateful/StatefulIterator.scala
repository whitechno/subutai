package zzz.iterator.stateful

import scala.collection.{ mutable, GenTraversableOnce }
import scala.collection.generic.{ CanBuildFrom, FilterMonadic }

trait StatefulIterator[+State, +Elem]
    extends Iterator[Elem]
    with FilterMonadic[Elem, StatefulIterator[State, Elem]] { self =>

  override def map[NewElem, That](f: Elem => NewElem)(implicit
      bf: CanBuildFrom[StatefulIterator[State, Elem], NewElem, That]
  ): That = ???

  override def flatMap[NewElem, That](f: Elem => GenTraversableOnce[NewElem])(
      implicit bf: CanBuildFrom[StatefulIterator[State, Elem], NewElem, That]
  ): That = ???

  override def filter(f: Elem => Boolean): StatefulIterator[State, Elem] =
    withFilter(f)
  override def withFilter(f: Elem => Boolean): StatefulIterator[State, Elem] = ???

}

object StatefulIterator {

  abstract class AbstractStatefulIterator[State, Elem]
      extends StatefulIterator[State, Elem]

  abstract class WrappedStatefulIterator[State, Elem, OrigElem](
      val child: StatefulIterator[State, OrigElem]
  ) extends StatefulIterator[State, Elem] {
    override def hasNext: Boolean = child.hasNext
  }

  /** Implementation does nothing. map/flatMap/etc in [[StatefulIterator]] take care
    * of the operations.
    *
    * (No wonder then that [[CanBuildFrom]] is deprecated in Scala 2.13)
    */
  private val genericCanBuildFrom: CanBuildFrom[
    StatefulIterator[Nothing, Nothing],
    Nothing,
    StatefulIterator[Any, Any]
  ] = new CanBuildFrom[
    StatefulIterator[Nothing, Nothing],
    Nothing,
    StatefulIterator[Any, Any]
  ] {
    override def apply(): mutable.Builder[Nothing, StatefulIterator[Any, Any]] = ???
    override def apply(
        from: StatefulIterator[Nothing, Nothing]
    ): mutable.Builder[Nothing, StatefulIterator[Any, Any]] = ???
  }
  implicit def canBuildStatefulIteratorFromStatefulIterator[State, Elem, NewElem]
      : CanBuildFrom[
        StatefulIterator[State, Elem],
        NewElem,
        StatefulIterator[State, NewElem]
      ] = genericCanBuildFrom.asInstanceOf[CanBuildFrom[
    StatefulIterator[State, Elem],
    NewElem,
    StatefulIterator[State, NewElem]
  ]]

  /** Stateful iteration ops for any traversable. */
  implicit class StatefulIterationOps[Elem](
      val inputsTraversable: TraversableOnce[Elem]
  ) extends AnyVal {

    def asStateful[State](finalState: => State): StatefulIterator[State, Elem] =
      new AbstractStatefulIterator[State, Elem] {
        private val iter: Iterator[Elem] = inputsTraversable.toIterator
        override def hasNext: Boolean    = iter.hasNext
        override def next(): Elem        = iter.next()
      }

  }

}
