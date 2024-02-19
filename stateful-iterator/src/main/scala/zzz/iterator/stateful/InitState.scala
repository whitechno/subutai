package zzz.iterator.stateful

/** A type class for a state that keeps track of iteration history or that undergoes
  * some kind of aggregation or merging operation and have an initialization value -
  * a "base case" or "zero" or "empty". Since the merging operation is not defined
  * here, no rigorous laws can be stated for [[InitState.empty]].
  *
  * In category theory terminology, this is a monad, though a really trivial one.
  */
trait InitState[+A] {

  /** The initial state of [[A]]
    *
    * For efficiency, it's recommended that implementations return a singleton value,
    * rather than a new instance per call.
    */
  def empty: A

}

object InitState {

  /** simple apply when implicit InitState[A] is in scope */
  def apply[A: InitState]: InitState[A] = implicitly

  /** apply with empty value */
  def apply[A](emptyValue: A): InitState[A] = instance(emptyValue)

  /** Explicit instantiation of the `InitState` trait. Instead of
    * {{{
    *   new InitState[A] { val empty: A = emptyValue }
    * }}}
    */
  final private case class InitStateInstance[+A](empty: A) extends InitState[A]
  private def instance[A](emptyValue: A): InitState[A] =
    InitStateInstance(emptyValue)

  /** Provides implicit InitState instances for several common usages. */
  object Implicits {

    /** [[NumericInit]] should be used to ensure that [[InitState]]'s zero is the
      * same as [[Numeric]]'s zero - which is set to `0`.
      *
      * NOTE: This is not necessarily the only choice of `zero` for numerics, and it
      * depends on merge operation and specific context. For sum operation, `0` is
      * often the right choice. For multiplication operation, `1` would be a common
      * choice. And, e.g., for max operation, `zero` could be a `NegativeInfinity`.
      *
      * Use:
      * {{{
      * implicit val intInitState: InitState[Int] = InitState.Implicits.NumericInit[Int]
      * }}}
      */
    def NumericInit[N: Numeric]: InitState[N] = instance(implicitly[Numeric[N]].zero)

    /** [[OptionInit]] can be used to provide implicit [[InitState]] value for
      * evidence parameter of any [[Option]] type.
      *
      * Use:
      * {{{
      *   import InitState.Implicits.OptionInit
      * }}}
      */
    implicit def OptionInit[A]: InitState[Option[A]]      = anyOptionInit
    private val anyOptionInit: InitState[Option[Nothing]] = instance(None)

    /** Implicit [[InitState]] instance for any [[scala.collection.Iterable]] type.
      *
      * Use:
      * {{{
      *   import InitState.Implicits.IterableInit
      * }}}
      */
    implicit def IterableInit[A]: InitState[Iterable[A]]      = anyIterableInit
    private val anyIterableInit: InitState[Iterable[Nothing]] = instance(Nil)

    /** Implicit [[InitState]] instance for any [[Map]] type.
      *
      * Use:
      * {{{
      *   import InitState.Implicits.MapInit
      * }}}
      */
    implicit def MapInit[K, V]: InitState[Map[K, V]] =
      anyMapInit.asInstanceOf[InitState[Map[K, V]]]
    private val anyMapInit: InitState[Map[Any, Nothing]] = instance(Map.empty)
  }

}
