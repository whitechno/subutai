package subutai.json4s.jackson

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{ read, write }

object JsonTestMain extends App {
  println(">>> JsonTestMain")

//  implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
  // Brings in default date formats etc.
  implicit val formats: DefaultFormats.type = DefaultFormats

  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address, children: List[Child])

  val json = parse("""
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

  val person = json.extract[Person]
  println(person)

  val ser = write(Child("Mary", 5, None))
  println(ser)
  val deser = read[Child](ser)
  println(deser)

}
