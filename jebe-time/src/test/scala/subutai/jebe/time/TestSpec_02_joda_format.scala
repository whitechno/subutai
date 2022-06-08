package subutai.jebe.time

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.joda.time.{ DateTime, DateTimeZone, Period }
import org.joda.time.format.{
  DateTimeFormat,
  DateTimeFormatter,
  ISODateTimeFormat,
  ISOPeriodFormat
}

class TestSpec_02_joda_format extends AnyFlatSpec with Matchers {

  // to print time zones in various formats
  private val tzFmtJt: DateTimeFormatter =
    DateTimeFormat.forPattern("yyyy-MM-dd'T'HH Z ZZ ZZZ z zzzz")

  "ISODateTimeFormat.dateTimeParser" should
    "parse ISO datetime with or without offset" in {
      // with offset, datetime is exactly known
      val dtWithOffset = "2021-01-03T+0000"
      // without offset, parser applies either default or specified time-zone
      val dtWithoutOffset = "2021-01-03"

      // constructor uses ISODateTimeFormat.dateTimeParser()
      // with the default (local) time-zone
      tzFmtJt.print(new DateTime(dtWithOffset)) shouldBe
        "2021-01-02T16 -0800 -08:00 America/Los_Angeles PST Pacific Standard Time"
      tzFmtJt.print(new DateTime(dtWithoutOffset)) shouldBe
        "2021-01-03T00 -0800 -08:00 America/Los_Angeles PST Pacific Standard Time"

      // parse uses ISODateTimeFormat.dateTimeParser().withOffsetParsed()
      // it sets zone to parsed offset if date is with offset,
      // and it sets zone to default (local) if date is without offset.
      tzFmtJt.print(DateTime.parse(dtWithOffset)) shouldBe
        "2021-01-03T00 +0000 +00:00 UTC UTC Coordinated Universal Time"
      tzFmtJt.print(DateTime.parse(dtWithoutOffset)) shouldBe
        "2021-01-03T00 -0800 -08:00 America/Los_Angeles PST Pacific Standard Time"

      // parse using formatter with explicitly specified time-zone
      val parseZone: DateTimeFormatter = ISODateTimeFormat
        .dateTimeParser()
        .withZone(DateTimeZone.forID("America/New_York"))
      tzFmtJt.print(DateTime.parse(dtWithOffset, parseZone)) shouldBe
        "2021-01-02T19 -0500 -05:00 America/New_York EST Eastern Standard Time"
      tzFmtJt.print(DateTime.parse(dtWithoutOffset, parseZone)) shouldBe
        "2021-01-03T00 -0500 -05:00 America/New_York EST Eastern Standard Time"

      // with UTC formatter both parsed datetimes are the same
      val parseUTC: DateTimeFormatter =
        ISODateTimeFormat.dateTimeParser().withZoneUTC()
      tzFmtJt.print(DateTime.parse(dtWithOffset, parseUTC)) shouldBe
        "2021-01-03T00 +0000 +00:00 UTC UTC Coordinated Universal Time"
      tzFmtJt.print(DateTime.parse(dtWithoutOffset, parseUTC)) shouldBe
        "2021-01-03T00 +0000 +00:00 UTC UTC Coordinated Universal Time"
    }

  it should """
  parse datetime without offset in one time-zone and print it in another""" in {
    val dtWithoutOffset = "2021-01-03T00" // assumed to be UTC
    val parseUTC: DateTimeFormatter =
      ISODateTimeFormat.dateTimeParser().withZoneUTC()
    val jt = parseUTC
      .parseDateTime(dtWithoutOffset)
      .withZone(DateTimeZone.forID("America/Los_Angeles"))
    tzFmtJt.print(jt) shouldBe
      "2021-01-02T16 -0800 -08:00 America/Los_Angeles PST Pacific Standard Time"
  }

  "DateTimeFormat.forPattern" should "parse datetime with time zone id" in {
    val jt = DateTimeFormat
      .forPattern("yyyy-MM-dd'T'HH ZZZ")
      .parseDateTime("2021-06-03T00 America/New_York")
    tzFmtJt.print(jt) shouldBe
      "2021-06-03T00 -0400 -04:00 America/New_York EDT Eastern Daylight Time"
  }

  // parse and print datetime in "yyyy-MM-dd'T'HH:mm:ss ZZZ" format
  // and set time zone to time zone id in ZZZ
  val formatZZZ = "yyyy-MM-dd'T'HH:mm:ss ZZZ"
  def parseZZZ(text: String): DateTime =
    DateTimeFormat.forPattern(formatZZZ).parseDateTime(text)
  def printZZZ(jt: DateTime): String = DateTimeFormat.forPattern(formatZZZ).print(jt)

  // parse any ISO 8601 datetime and set time zone to UTC
  def parseISOUTC(text: String): DateTime =
    ISODateTimeFormat.dateTimeParser().withZoneUTC().parseDateTime(text)
  def printISOUTC(jt: DateTime): String = DateTimeFormat
    .forPattern("yyyy-MM-dd'T'HH:mm:ss")
    .print(jt.withZone(DateTimeZone.UTC))
  // print using ISODateTimeFormat "yyyy-MM-dd'T'HH:mm:ss.SSSZZ" format.
  // The time zone offset is 'Z' for zero, and of the form '±HH:mm' for non-zero.
  def printISO(jt: DateTime): String = ISODateTimeFormat.dateTime().print(jt)

  "parseISOUTC" should "parse ISO 8601 datetime and set time zone to UTC" in {
    val jt = parseISOUTC("2021-01-03T00:05:06-0400")

    printISOUTC(jt) shouldBe "2021-01-03T04:05:06"
    printISO(jt) shouldBe "2021-01-03T04:05:06.000Z"
    printZZZ(jt) shouldBe "2021-01-03T04:05:06 UTC"

    for (
      (fmt, out) <- Seq(
        "EEEE"                  -> "Sunday",
        "z"                     -> "UTC",
        "zzzz"                  -> "Coordinated Universal Time",
        "Z"                     -> "+0000",
        "ZZ"                    -> "+00:00",
        "ZZZ"                   -> "UTC",
        "yyyy-MM-dd'T'HH:mm:ss" -> "2021-01-03T04:05:06",
        "xxxx-'W'ww-e"          -> "2020-W53-7",
        "yyyy-DDD"              -> "2021-003"
      )
    ) {
      val dt = DateTimeFormat.forPattern(fmt).print(jt)
      // val qfmt = "\"" + s"${fmt}" + "\""
      // println(f"""${qfmt}%25s -> "${dt}",""")
      dt shouldBe out
    }
  }

  "parseZZZ" should """
   parse "yyyy-MM-dd'T'HH:mm:ss ZZZ" datetime and set time zone to ZZZ""" in {
    val jt = parseZZZ("2021-01-03T00:05:06 America/Los_Angeles")

    printISOUTC(jt) shouldBe "2021-01-03T08:05:06"
    printISO(jt) shouldBe "2021-01-03T00:05:06.000-08:00"
    printZZZ(jt) shouldBe "2021-01-03T00:05:06 America/Los_Angeles"

    for (
      (fmt, out) <- Seq(
        "G"                     -> "AD",
        "GGGG"                  -> "AD",
        "C"                     -> "20",
        "YYYY"                  -> "2021",
        "xxxx"                  -> "2020",
        "ww"                    -> "53",
        "e"                     -> "7",
        "E"                     -> "Sun",
        "EEEE"                  -> "Sunday",
        "y"                     -> "2021",
        "yy"                    -> "21",
        "yyy"                   -> "2021",
        "yyyy"                  -> "2021",
        "DDD"                   -> "003",
        "M"                     -> "1",
        "MM"                    -> "01",
        "MMM"                   -> "Jan",
        "MMMM"                  -> "January",
        "dd"                    -> "03",
        "a"                     -> "AM",
        "aaaa"                  -> "AM",
        "K"                     -> "0",
        "KK"                    -> "00",
        "h"                     -> "12",
        "hh"                    -> "12",
        "HH"                    -> "00",
        "kk"                    -> "24",
        "mm"                    -> "05",
        "ss"                    -> "06",
        "z"                     -> "PST",
        "zzzz"                  -> "Pacific Standard Time",
        "Z"                     -> "-0800",
        "ZZ"                    -> "-08:00",
        "ZZZ"                   -> "America/Los_Angeles",
        "''"                    -> "'",
        "yyyy-MM-dd'T'HH:mm:ss" -> "2021-01-03T00:05:06",
        "xxxx-'W'ww-e"          -> "2020-W53-7",
        "yyyy-DDD"              -> "2021-003"
      )
    ) {
      val dt = DateTimeFormat.forPattern(fmt).print(jt)
      // val qfmt = "\"" + s"${fmt}" + "\""
      // println(f"""${qfmt}%25s -> "${dt}",""")
      dt shouldBe out
    }
  }
}
