package subutai.jebe.time

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.joda.time.{ DateTime, Period }
import org.joda.time.format.{ DateTimeFormat, DateTimeFormatter, ISOPeriodFormat }

class TestSpec_TimeStepper extends AnyFlatSpec with Matchers {

  it should "correctly implement TimeStepper" in {
    val unixStart = DateTime.parse("1970-01-01T00:00:00Z")
    //println(s"|1> unixStart = ${unixStart}")
    //println(s"    unixStart.getMillis = ${unixStart.getMillis}")
    unixStart.getMillis shouldBe 0L

    //val now = DateTime.now
    val now          = DateTime.parse("2021-06-03T18:29:51.601-07:00")
    val nowMillisFmt = f"${now.getMillis.toDouble / 1000d}%,f"
    //println(s"|2> ${now} = ${nowMillisFmt}")
    nowMillisFmt shouldBe "1,622,770,191.601000"

    val billionSec = 1e9.toInt
    //println(f"|3> now %% billionSec = ${now.getMillis / 1000 % billionSec}%,d")

    val period   = s"PT${billionSec}S"
    val periodJt = ISOPeriodFormat.standard.parsePeriod(period)
    //print(s"|4> billionSec = ${periodJt} = ${periodJt.normalizedStandard}")
    periodJt.toString shouldBe "PT1000000000S"
    periodJt.normalizedStandard.toString shouldBe "P1653W3DT1H46M40S"
    //println(s" ~= ${periodJt.toDurationFrom(now).getStandardDays / 365} years")
    periodJt.toDurationFrom(now).getStandardDays / 365 shouldBe 31

    val start = "2020_07_01_00"
    val end   = "2020_07_01_03"
    val ts    = TimeStepper(start, end)
    //println("isStepping: " + ts.isStepping)
    ts.isStepping shouldBe true
    val acc = new scala.collection.mutable.ArrayBuffer[String]
    while (ts.isStepping) {
      //println(ts.next)
      acc.append(ts.next)
    }
    //println(acc.toList)
    acc.toList shouldBe List("2020_07_01_00", "2020_07_01_01", "2020_07_01_02")
  }

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
