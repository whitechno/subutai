package subutai.json4s.jackson

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jackson.Serialization.{ read, write }
import java.text.SimpleDateFormat
import java.util.{ Date, TimeZone }

class TestSpec_json4s_jackson extends AnyFlatSpec with Matchers {

  //  implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
  // Brings in default date formats etc.
  implicit val formats: DefaultFormats.type = DefaultFormats

  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address, children: List[Child])

  it should "correctly parse, extract, read and write" in {

    val json = parse(in = """
      { "name": "joe",
       "address": {
         "street": "Bulevard",
         "city": "Helsinki"
       },
       "children": [
         {
           "name": "Mary",
           "age": 5,
           "birthdate": "2004-09-04T18:06:22Z"
         },
         {
           "name": "Mazy",
           "age": 3,
           "birthdate": null
         },
         {
           "name": "Maxy",
           "age": 1
         }
       ]
      }
    """)

    val person: Person = json.extract[Person]
    // println(person)

    val df: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    df.setTimeZone(TimeZone.getTimeZone("UTC"))
    val dt: Date = df.parse("2004-09-04T18:06:22Z")

    person.toString shouldBe
      Person(
        name    = "joe",
        address = Address("Bulevard", "Helsinki"),
        children = List(
          Child("Mary", 5, Some(dt)),
          Child("Mazy", 3, None),
          Child("Maxy", 1, None)
        )
      ).toString

    val obj = Child("Mary", 5, Some(dt))
    // println("obj: " + obj)
    val ser = write(obj)
    // println("ser: " + ser)
    val deser = read[Child](ser)
    // println("deser: " + deser)
    obj.toString shouldBe deser.toString
    deser.birthdate shouldBe Some(dt)
  }
}
