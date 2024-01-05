package example

object TestMain_01_twoInts2Long extends App {
  val MAX_UNSIGNED_INT32: Long = -1L >>> 32 // = 0xffffffffL = 4294967295L
  def int2uint(i: Int): Long   = i & MAX_UNSIGNED_INT32

  println("\nint2uint:")
  println(
    s"|1> Int.MaxValue (${Int.MaxValue}) ==" +
      s" int2uint(Int.MaxValue) (${int2uint(Int.MaxValue)})"
  ) // 2147483647
  println(
    s"|2> MAX_UNSIGNED_INT32 = ${MAX_UNSIGNED_INT32} = int2uint(-1) = ${int2uint(-1)}"
  ) // 4294967295L
  println(
    "|3> (Int.MaxValue.toLong << 32) + Int.MaxValue.toLong * 2 + 1 = " +
      s"${(Int.MaxValue.toLong << 32) + Int.MaxValue.toLong * 2 + 1}"
  ) // 9223372036854775807L
  println(s"|4> Long.MaxValue = ${Long.MaxValue}") // 9223372036854775807L
  println(s"|5> Long.MaxValue * 2 + 1 = ${Long.MaxValue * 2 + 1}") // -1L
  println(
    "|6> (MAX_UNSIGNED_INT32 << 32) + MAX_UNSIGNED_INT32 = " +
      s"${(MAX_UNSIGNED_INT32 << 32) + MAX_UNSIGNED_INT32}"
  ) // -1L

  def twoInts2Long(i1: Int, i2: Int): Long = (int2uint(i1) << 32) + int2uint(i2)
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
