package subutai.jebe.time

object TimeStepperTestMain extends App {

//  var i = 0
//  while (i < 10) {
//    println(i)
//    i += 1
//  }
  val start = "2020_07_01_00"
  val end   = "2020_07_01_03"
  val ts    = TimeStepper(start, end)
  println(ts.isStepping)

  while (ts.isStepping) println(ts.next)
}

// ISO8601: P[n]Y[n]M[n]DT[n]H[n]M[n]S or P1W, P1D, PT1H, PT1M
// EXCLUSIVE: until
case class TimeStepper(
    start: String,
    end: String,
    step: String = "PT1H",
    fmt: String  = "yyyy_MM_dd_HH"
) {
  import org.joda.time.{ DateTime, Period }
  import org.joda.time.format.{ DateTimeFormat, DateTimeFormatter, ISOPeriodFormat }

  private val fmtJt: DateTimeFormatter = DateTimeFormat.forPattern(fmt).withZoneUTC()
  private val startJt: DateTime        = DateTime.parse(start, fmtJt)
  private val endJt: DateTime          = DateTime.parse(end, fmtJt)
  private val stepJt: Period           = ISOPeriodFormat.standard.parsePeriod(step)

  private var jt          = startJt
  def isStepping: Boolean = jt.isBefore(endJt)
  def next: String = {
    val dt = fmtJt.print(jt)
    jt = jt.plus(stepJt)
    dt
  }

}
