package zzz.bits.geo

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_1
object TestMain_GeoUtils_1 extends App {
  import GeoUtils._

  println(ERR_MASK_COUNTRY) // 12
  println(ERR_MASK_STATE) // 14
  println(ERR_MASK_CITY) // 15

  println(UNRESOLVED_ENCODED_GEO) // 9187344889128419329
  println(GeoCoder.encodedToPortalGeoStr(UNRESOLVED_ENCODED_GEO)) // 127.-1.-1.-1
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_2
object TestMain_GeoUtils_2 extends App {
  // Unknown
  val geoInfo = GeoInfo()
  println(geoInfo) // GeoInfo(15,-1,-1,-1,-1)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(9187344889128419329,9187344889128419329,9187344889128419329,9187344889128419329)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(127.-1.-1.-1,127.-1.-1.-1,127.-1.-1.-1,127.-1.-1.-1)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_3
object TestMain_GeoUtils_3 extends App {
  import GeoInfo._
  // Africa, Unknown
  val geoInfo = GeoInfo(errFlagCity | errFlagState | errFlagCountry, 0, -1, -1, -1)
  println(geoInfo) // GeoInfo(7,0,-1,-1,-1)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(0,9187344889128419329,9187344889128419329,9187344889128419329)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(0.0.0.0,127.-1.-1.-1,127.-1.-1.-1,127.-1.-1.-1)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_4
object TestMain_GeoUtils_4 extends App {
  import GeoInfo._
  // Europe, Andorra AD, Unknown
  val geoInfo = GeoInfo(errFlagCity | errFlagState, 3, 0, -1, -1)
  println(geoInfo) // GeoInfo(3,3,0,-1,-1)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(216172782113783808,216172782113783808,9187344889128419329,9187344889128419329)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(3.0.0.0,3.0.0.0,127.-1.-1.-1,127.-1.-1.-1)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_5
object TestMain_GeoUtils_5 extends App {
  import GeoInfo._
  // 3|Europe, 102|Isle of Man|IM, N/A, 26151|Douglas with errFlagState
  val geoInfo = GeoInfo(errFlagState, 3, 102, -3, 26151)
  println(geoInfo) // GeoInfo(2,3,102,-3,26151)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(216172782113783808,216284932299816960,9187344889128419329,9187344889128419329)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(3.0.0.0,3.102.0.0,127.-1.-1.-1,127.-1.-1.-1)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_6
object TestMain_GeoUtils_6 extends App {
  import GeoInfo._
  // 3|Europe, 102|Isle of Man|IM, N/A, 26151|Douglas with errSetNone
  val geoInfo = GeoInfo(errSetNone, 3, 102, -3, 26151)
  println(geoInfo) // GeoInfo(0,3,102,-3,26151)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(216172782113783808,216284932299816960,216285482105962496,216285482105988647)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(3.0.0.0,3.102.0.0,3.102.-3.0,3.102.-3.26151)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoUtils_7
object TestMain_GeoUtils_7 extends App {
  import GeoInfo._
  // 3|Europe, 102|Isle of Man|IM, N/A, Unknown with errSetNone
  val geoInfo = GeoInfo(errSetNone, 3, 102, -3, -2)
  println(geoInfo) // GeoInfo(0,3,102,-3,-2)
  val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
  // EncodedGeoInfo(216172782113783808,216284932299816960,216285482105962496,216285482114351106)
  println(encGeoInfo)
  val portalGeoInfo = encGeoInfo.toPortalGeoInfo
  // PortalGeoInfo(3.0.0.0,3.102.0.0,3.102.-3.0,3.102.-3.-2)
  println(portalGeoInfo)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoCoder_1
object TestMain_GeoCoder_1 extends App {
  val enc = EncodedGeoInfo(
    continent = 432345564227567616L, // 6.0.0.0
    country   = 432534680227545088L, // 6.172.0.0
    state     = 432534725777686528L, // 6.172.2715.0
    city      = 432534725777741066L //  6.172.2715.54538
  )

  val prt = enc.toPortalGeoInfo
  // PortalGeoInfo(6.0.0.0,6.172.0.0,6.172.2715.0,6.172.2715.54538)
  println(prt)

  val prt2enc = prt.toEncodedGeoInfo
  // EncodedGeoInfo(432345564227567616,432534680227545088,432534725777686528,432534725777741066)
  println(prt2enc)
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoCoder_2
object TestMain_GeoCoder_2 extends App {
  import GeoCoder._
  val enc: Long = encodeGeoCity(127, -1, -2, -3)
  println(enc) // 9187344889145196547
  val dec: String = encodedToPortalGeoStr(enc)
  println(dec) // 127.-1.-2.-3
}

// bits/test:runMain zzz.bits.geo.TestMain_GeoCoder_3
object TestMain_GeoCoder_3 extends App {
  import GeoCoder._

  // limit on number of cities 8,388,607
  val maxCity = decodeGeoCity(CITY_MASK & (~CITY_SIGNBIT))
  println(maxCity) // 8388607
  println((1 << 23) - 1) // 8388607
  println(encodeGeoCity(0, 0, 0, maxCity)) // 8388607
  // next number cannot be used
  val encCity = encodeGeoCity(0, 0, 0, maxCity + 1)
  println(encCity) // 8388608
  println(decodeGeoCity(encCity)) // 0
  println(decodeGeoCity(encodeGeoCity(0, 0, 0, maxCity + 2))) // -1

  // limit on number of states 32,767
  val maxState = decodeGeoState(STATE_MASK & (~STATE_SIGNBIT))
  println(maxState) // 32767
  println((1 << 15) - 1) // 32767
  println(decodeGeoState(encodeGeoCity(0, 0, maxState, 0))) // 32767
  println(decodeGeoState(encodeGeoCity(0, 0, maxState + 1, 0))) // 0
  println(decodeGeoState(encodeGeoCity(0, 0, maxState + 2, 0))) // -1

  // limit on number of countries 32,767
  val maxCountry = decodeGeoCountry(COUNTRY_MASK & (~COUNTRY_SIGNBIT))
  println(maxCountry) // 32767
  println((1 << 15) - 1) // 32767
  println(decodeGeoCountry(encodeGeoCity(0, maxCountry, 0, 0))) // 32767
  println(decodeGeoCountry(encodeGeoCity(0, maxCountry + 1, 0, 0))) // 0
  println(decodeGeoCountry(encodeGeoCity(0, maxCountry + 2, 0, 0))) // -1

}
