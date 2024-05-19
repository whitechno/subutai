package zzz.bits.geo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TestSpec_GeoUtils extends AnyFlatSpec with Matchers {

  "GeoInfo()" should "have all geo unknown" in {
    val geoInfo = GeoInfo()
    geoInfo shouldBe GeoInfo(15, -1, -1, -1, -1)
    val encGeoInfo = EncodedGeoInfo.fromGeoInfo(geoInfo)
    encGeoInfo shouldBe EncodedGeoInfo(
      9187344889128419329L,
      9187344889128419329L,
      9187344889128419329L,
      9187344889128419329L
    )
    val portalGeoInfo = encGeoInfo.toPortalGeoInfo
    portalGeoInfo shouldBe PortalGeoInfo(
      "127.-1.-1.-1",
      "127.-1.-1.-1",
      "127.-1.-1.-1",
      "127.-1.-1.-1"
    )
  }

  "GeoCoder" should "decode and encode Geo" in {
    val enc = EncodedGeoInfo(
      continent = 432345564227567616L, // 6.0.0.0
      country   = 432534680227545088L, // 6.172.0.0
      state     = 432534725777686528L, // 6.172.2715.0
      city      = 432534725777741066L //  6.172.2715.54538
    )

    val prt = enc.toPortalGeoInfo
    prt shouldBe PortalGeoInfo(
      "6.0.0.0",
      "6.172.0.0",
      "6.172.2715.0",
      "6.172.2715.54538"
    )

    val prt2enc = prt.toEncodedGeoInfo
    prt2enc shouldBe enc
  }

  it should "limit number of cities" in {
    import GeoCoder._
    val maxCity = decodeGeoCity(CITY_MASK & (~CITY_SIGNBIT))
    maxCity shouldBe (1 << 23) - 1
    maxCity shouldBe 8388607

    decodeGeoCity(encodeGeoCity(0, 0, 0, maxCity + 1)) shouldBe 0
    decodeGeoCity(encodeGeoCity(0, 0, 0, maxCity + 2)) shouldBe -1
  }

  it should "limit number of states" in {
    import GeoCoder._
    val maxState = decodeGeoState(STATE_MASK & (~STATE_SIGNBIT))
    maxState shouldBe (1 << 15) - 1
    maxState shouldBe 32767

    decodeGeoState(encodeGeoCity(0, 0, maxState + 1, 0)) shouldBe 0
    decodeGeoState(encodeGeoCity(0, 0, maxState + 2, 0)) shouldBe -1
  }

}
