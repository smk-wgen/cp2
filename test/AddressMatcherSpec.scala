/**
 * Created by skunnumkal on 9/17/13.
 */

import models.MongoUserCommute
import org.specs2.mutable._
import play.api.libs.json._
import play.api.test.FakeApplication
import play.api.test.Helpers._
import strategy._

class AddressMatcherSpec extends Specification{

  //val accurateJsStr:String = "{"results":[{"formatted_address":,"geometry":{"location":{"lat":40.793532,"lng":-74.00306599999999},"location_type":"ROOFTOP"},"types":["street_address"]}]}"
  val accurateJsonObj = Json.obj("results" -> Json.arr(Json.obj("formatted_address" -> "108 71st Street, Guttenberg, NJ 07093, USA" ,
    "geometry" -> Json.obj("location" -> Json.obj("lat" -> 40.789995, "lng" -> -77.578788),"location_type" -> "ROOFTOP"))
         ))
  val inAccurateJsonObj = Json.obj("results" -> Json.arr(Json.obj("formatted_address" -> "108 71st Street, Guttenberg, NJ 07093, USA" ,
    "geometry" -> Json.obj("location" -> Json.obj("lat" -> 40.789995, "lng" -> -77.578788),"location_type" -> "APPROX")
    )))
  "AddressMatcher " should {

    "return first address with rooftop location_type" in {
      running(FakeApplication()) {


        val maybeAddress = AddressMatcher.getBestMatch(accurateJsonObj.\("results").as[JsArray])
        maybeAddress match {
          case None => assert(false)
          case Some(result) => assert(true)
        }


      }
    }

    "return empty for inaccurate results " in {
      running(FakeApplication()) {


        val maybeAddress = AddressMatcher.getBestMatch(inAccurateJsonObj.\("results").as[JsArray])
        maybeAddress match {
          case None => assert(true)
          case Some(result) => assert(false)
        }


      }
    }

    "having an inaccurate address throws Runtime exception for that address" in {
      running(FakeApplication()) {

        val accurateAddress:String = "108 71st street guttenberg nj";
        val inAccurateAddress:String = "Palo Alto, CA"
        try{
          val maybeAddress = AddressMatcher.isAddressMatch(accurateAddress,inAccurateAddress,MongoMatchingStrategy.threshold)
          assert(false)
        } catch {
          case re: RuntimeException => {println("exception caught: " + re.getMessage)
           assert(re.getMessage.equals(inAccurateAddress))
          };
        }





      }
    }


  }
}
