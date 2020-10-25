package subutai.jebe.time

import org.joda.time.{ DateTime, Period }
import org.joda.time.format.{ DateTimeFormat, DateTimeFormatter, ISOPeriodFormat }

object TestMain_01_TimeStepper extends App {
  val unixStart = DateTime.parse("1970-01-01T00:00:00Z")
  println("unixStart = " + unixStart + " | " + unixStart.getMillis)

  val now = DateTime.now
  println(f"now = ${now} | ${now.getMillis.toDouble / 1000d}%,f")
  val billionSec = 1e9.toInt
  println(f"now %% billionSec = ${now.getMillis / 1000 % billionSec}%,d")

  val period = s"PT${billionSec}S"
  print("billionSec = " + period + " = ")
  val periodJt = ISOPeriodFormat.standard.parsePeriod(period)
  print(periodJt.normalizedStandard + " = ")
  println(periodJt.toDurationFrom(now).getStandardDays / 365 + " years")
  println("\n================================\n")

//  var i = 0
//  while (i < 10) {
//    println(i)
//    i += 1
//  }
  val start = "2020_07_01_00"
  val end   = "2020_07_01_03"
  val ts    = TimeStepper(start, end)
  println("isStepping: " + ts.isStepping)
  while (ts.isStepping) println(ts.next)
}

// ISO8601: P[n]Y[n]M[n]DT[n]H[n]M[n]S or P1W, P1D, PT1H, PT1M
case class TimeStepper(
    start: String,
    end: String,
    step: String = "PT1H",
    fmt: String  = "yyyy_MM_dd_HH"
) {
  private val fmtJt: DateTimeFormatter = DateTimeFormat.forPattern(fmt).withZoneUTC()
  private val startJt: DateTime        = DateTime.parse(start, fmtJt)
  private val endJt: DateTime          = DateTime.parse(end, fmtJt)
  private val stepJt: Period           = ISOPeriodFormat.standard.parsePeriod(step)

  private var jt          = startJt
  def startUnix: Double   = jt.getMillis.toDouble / 1000d
  def isStepping: Boolean = jt.isBefore(endJt) // strictly before by millisecond
  def next: String = {
    val dt = fmtJt.print(jt)
    jt = jt.plus(stepJt)
    dt
  }
}
