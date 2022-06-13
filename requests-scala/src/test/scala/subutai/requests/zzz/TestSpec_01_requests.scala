package subutai.requests.zzz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import requests.Response

import scala.collection.mutable

class TestSpec_01_requests extends AnyFlatSpec with Matchers {

  "requests" should "do example 1" in {
    val r: Response = requests.get("https://api.github.com/users/lihaoyi")
    r.statusCode shouldBe 200

    // println(r.headers)
    r.headers("content-type") shouldBe
      mutable.Buffer("application/json; charset=utf-8")

    // println(r.text)
    r.text()
      .startsWith(
        """{"login":"lihaoyi","id":934140,"node_id":"MDQ6VXNlcjkzNDE0MA==","""
      ) shouldBe true
  }

  it should "do example 2" in {
    requests
      .post("http://httpbin.org/post", data = Map("key" -> "value"))
      .statusCode shouldBe 200

    requests
      .put("http://httpbin.org/put", data = Map("key" -> "value"))
      .statusCode shouldBe 200

    requests.delete("http://httpbin.org/delete").statusCode shouldBe 200

    // requests.head("http://httpbin.org/head").statusCode shouldBe 200

    requests.options("http://httpbin.org/get").statusCode shouldBe 200

    // dynamically choose what HTTP method to use
    requests
      .send("put")("http://httpbin.org/put", data = Map("key" -> "value"))
      .statusCode shouldBe 200
  }

}
