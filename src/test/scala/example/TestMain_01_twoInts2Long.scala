package example

object TestMain_01_twoInts2Long extends App {
  val MAX_UNSIGNED_INT32: Long             = -1L >>> 32 // = 0xffffffffL = 4294967295L
  def int2uint(i: Int): Long               = i & MAX_UNSIGNED_INT32
  def twoInts2Long(i1: Int, i2: Int): Long = (int2uint(i1) << 32) + int2uint(i2)
  println(
    Int.MaxValue + " = " + int2uint(Int.MaxValue) + " | " +
      MAX_UNSIGNED_INT32 + " = " + int2uint(-1)
  )
  println((Int.MaxValue.toLong << 32) + Int.MaxValue.toLong * 2 + 1)
  println(Long.MaxValue + " | " + (Long.MaxValue * 2 + 1))
  println((MAX_UNSIGNED_INT32 << 32) + MAX_UNSIGNED_INT32)
  println("\ntwoInts2Long:")
  Seq(
    (0, 0),
    (0, Int.MaxValue),
    (0, Int.MaxValue + 1),
    (0, -1),
    (Int.MaxValue, 0),
    (Int.MaxValue, Int.MaxValue),
    (Int.MaxValue, Int.MaxValue + 1),
    (Int.MaxValue, -1),
    (Int.MaxValue + 1, 0),
    (Int.MaxValue + 1, Int.MaxValue),
    (Int.MaxValue + 1, Int.MaxValue + 1),
    (Int.MaxValue + 1, -1),
    (-1, 0),
    (-1, Int.MaxValue),
    (-1, Int.MaxValue + 1),
    (-1, -1)
  ).map { case (i1, i2) => f"${(i1, i2)}%25s -> " + twoInts2Long(i1, i2) }
    .foreach(println)
  println("\n================================\n")
}
