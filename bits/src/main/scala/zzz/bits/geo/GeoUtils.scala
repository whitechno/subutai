package zzz.bits.geo

case class GeoInfo(
    errFlags: Int   = GeoInfo.errSetAll,
    continent: Byte = -1,
    country: Short  = -1,
    state: Short    = -1,
    city: Int       = -1
)
object GeoInfo {
  val errFlagCity      = 1 // 0001
  val errFlagState     = 2 // 0010
  val errFlagCountry   = 4 // 0100
  val errFlagContinent = 8 // 1000

  val errSetNone: Int = 0
  // 0xF 15 1111
  val errSetAll: Int = errFlagCity | errFlagState | errFlagCountry | errFlagContinent
}

case class EncodedGeoInfo(
    continent: Long,
    country: Long,
    state: Long,
    city: Long
) {
  def toPortalGeoInfo: PortalGeoInfo = PortalGeoInfo(
    continent = GeoCoder.encodedToPortalGeoStr(continent),
    country   = GeoCoder.encodedToPortalGeoStr(country),
    state     = GeoCoder.encodedToPortalGeoStr(state),
    city      = GeoCoder.encodedToPortalGeoStr(city)
  )
}
object EncodedGeoInfo {
  def fromGeoInfo(geoInfo: GeoInfo): EncodedGeoInfo = EncodedGeoInfo(
    continent = GeoUtils.encodedContinent(geoInfo),
    country   = GeoUtils.encodedCountry(geoInfo),
    state     = GeoUtils.encodedState(geoInfo),
    city      = GeoUtils.encodedCity(geoInfo)
  )
}

case class PortalGeoInfo(
    continent: String,
    country: String,
    state: String,
    city: String
) {
  def toEncodedGeoInfo: EncodedGeoInfo = EncodedGeoInfo(
    continent = GeoCoder.portalGeoStrToEncoded(continent),
    country   = GeoCoder.portalGeoStrToEncoded(country),
    state     = GeoCoder.portalGeoStrToEncoded(state),
    city      = GeoCoder.portalGeoStrToEncoded(city)
  )
}

//
// GeoUtils
//

object GeoUtils {
  import GeoInfo._

  val ERR_MASK_CONTINENT: Int = errFlagContinent // 0x8 1000
  val ERR_MASK_COUNTRY: Int   = ERR_MASK_CONTINENT | errFlagCountry // 0xC 12 1100
  val ERR_MASK_STATE: Int     = ERR_MASK_COUNTRY | errFlagState // 0xE 14 1110
  val ERR_MASK_CITY: Int      = ERR_MASK_STATE | errFlagCity // 0xF 15 1111

  // 9187344889128419329L
  val UNRESOLVED_ENCODED_GEO: Long = GeoCoder.encodeGeoCity(127, -1, -1, -1)
  // "127.-1.-1.-1"
  val UNRESOLVED_PORTAL_GEO: String =
    GeoCoder.encodedToPortalGeoStr(UNRESOLVED_ENCODED_GEO)

  private def encodedGeo(
      geoInfo: GeoInfo,
      mask: Int,
      cont: Int    = 0,
      country: Int = 0,
      state: Int   = 0,
      city: Int    = 0
  ): Long =
    if ((geoInfo.errFlags & mask) == 0)
      GeoCoder.encodeGeoCity(cont, country, state, city)
    else UNRESOLVED_ENCODED_GEO

  def encodedContinent(geoInfo: GeoInfo): Long =
    encodedGeo(geoInfo, ERR_MASK_CONTINENT, cont = geoInfo.continent)
  def encodedCountry(geoInfo: GeoInfo): Long = encodedGeo(
    geoInfo,
    ERR_MASK_COUNTRY,
    cont    = geoInfo.continent,
    country = geoInfo.country
  )
  def encodedState(geoInfo: GeoInfo): Long = encodedGeo(
    geoInfo,
    ERR_MASK_STATE,
    cont    = geoInfo.continent,
    country = geoInfo.country,
    state   = geoInfo.state
  )
  def encodedCity(geoInfo: GeoInfo): Long = encodedGeo(
    geoInfo,
    ERR_MASK_CITY,
    cont    = geoInfo.continent,
    country = geoInfo.country,
    state   = geoInfo.state,
    city    = geoInfo.city
  )

}

//
// GeoCoder
//

object GeoCoder {
  def signedShift(iValue: Long, iNumShift: Int, iSignBit: Long): Long =
    if (iValue >= 0)
      iValue << iNumShift
    else
      ((iValue * -1L) << iNumShift) | iSignBit

  def signedUnshift(
      iValue: Long,
      iMask: Long,
      iNumShift: Int,
      iSignBit: Long
  ): Int = {
    val tmp = iValue & iMask
    if ((tmp & iSignBit) != 0)
      ((tmp & (~iSignBit)) >> iNumShift).asInstanceOf[Int] * -1
    else
      (tmp >> iNumShift).asInstanceOf[Int]
  }

  def encodeGeoCity(iContinent: Int, iCountry: Int, iState: Int, iCity: Int): Long =
    signedShift(iContinent, CONTINENT_SHIFT, CONTINENT_SIGNBIT) |
      signedShift(iCountry, COUNTRY_SHIFT, COUNTRY_SIGNBIT) |
      signedShift(iState, STATE_SHIFT, STATE_SIGNBIT) |
      signedShift(iCity, 0, CITY_SIGNBIT)

  def decodeGeoContinent(iEncodedCity: Long): Int =
    // continent >=0, no negative case to handle.
    ((iEncodedCity & CONTINENT_MASK) >> CONTINENT_SHIFT).asInstanceOf[Int]

  def decodeGeoCountry(iEncodedCity: Long): Int =
    signedUnshift(iEncodedCity, COUNTRY_MASK, COUNTRY_SHIFT, COUNTRY_SIGNBIT)

  def decodeGeoState(iEncodedCity: Long): Int =
    signedUnshift(iEncodedCity, STATE_MASK, STATE_SHIFT, STATE_SIGNBIT)

  def decodeGeoCity(iEncodedCity: Long): Int =
    signedUnshift(iEncodedCity, CITY_MASK, 0, CITY_SIGNBIT)

  def encodedToPortalGeoStr(encodedGeo: Long): String =
    decodeGeoContinent(encodedGeo) + "." +
      decodeGeoCountry(encodedGeo) + "." +
      decodeGeoState(encodedGeo) + "." +
      decodeGeoCity(encodedGeo)

  def portalGeoStrToEncoded(portalGeo: String): Long = {
    val geoArr = portalGeo.split("\\.").map { str =>
      try str.toInt
      catch {
        case e: NumberFormatException => 0
      }
    }
    val continent = if (geoArr.length > 0) geoArr(0) else 0
    val country   = if (geoArr.length > 1) geoArr(1) else 0
    val state     = if (geoArr.length > 2) geoArr(2) else 0
    val city      = if (geoArr.length > 3) geoArr(3) else 0

    encodeGeoCity(continent, country, state, city)
  }

  // we use 127 (the MAX_INT for one byte) to represent invalid continent
  // and use -1 to represent other invalid fields
  val CITY_MASK         = 0x0000000000ffffffL // 23 bits, 8,388,607 max (390,664)
  val CITY_SIGNBIT      = 0x0000000000800000L
  val STATE_MASK        = 0x000000ffff000000L // 15 bits, 32,767 max (10,262)
  val STATE_SIGNBIT     = 0x0000008000000000L
  val COUNTRY_MASK      = 0x00ffff0000000000L // 15 bits, 32,767 max
  val COUNTRY_SIGNBIT   = 0x0080000000000000L
  val CONTINENT_MASK    = 0xff00000000000000L // 7 bits, 127 max
  val CONTINENT_SIGNBIT = 0x8000000000000000L

  val CONTINENT_SHIFT: Int = 64 - 8
  val COUNTRY_SHIFT: Int   = 64 - 8 - 16
  val STATE_SHIFT: Int     = 64 - 8 - 16 - 16
}
