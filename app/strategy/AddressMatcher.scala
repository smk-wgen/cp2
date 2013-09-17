package strategy

import models.UserAddress
import play.api.libs.json._

/**
 * Created by skunnumkal on 7/11/13.
 */
object AddressMatcher {
  val ROOFTOP:String = "ROOFTOP";
  def getBestMatch(arr:JsArray): Option[JsValue] = {
    //println("Input arr" + Json.stringify(arr))
    val resultList:List[JsValue] = arr.as[List[JsValue]]

    val accurateResults:List[JsValue] = resultList.filter(jsVal =>
    { println("Result Location type =" + jsVal.\("geometry").\("location_type"))
      val resultType:String  =  jsVal.\("geometry").\("location_type").as[String]
      //println(resultType)
      resultType.equals(ROOFTOP)
    })
    if(accurateResults.isEmpty)
      None
    else
      Some(accurateResults.head)  //return the first accurate result
  }

  def isAddressMatch(a:String,b:String,threshold:Double):Boolean = {

        val json1:JsValue = Json.parse(GMapHelper.getAddressJson(a))
        val json2:JsValue = Json.parse(GMapHelper.getAddressJson(b))
        val maybeAddress:Option[JsValue] = getBestMatch(json1.\("results").as[JsArray])
        maybeAddress match {
          case Some(jsVal) => {
              val mayBddress = getBestMatch(json2.\("results").as[JsArray])
              mayBddress match {
                case Some(jsVbl) => {
                  val (lat1,lon1):(Double,Double) =  extractLatLong(jsVal)
                  println("Latitude " + lat1+" , Longitude" + lon1)
                  val (lat2,lon2):(Double,Double) = extractLatLong(jsVbl)
                  println("Latitude " + lat2+" , Longitude" + lon2)
                  val distance:Double = Math.sqrt((lat2-lat1)*(lat2-lat1) + (lon2-lon1)*(lon2-lon1))
                  return distance <= threshold
                }
                case None => throw new RuntimeException(b)
              }
          }
          case None => throw new RuntimeException(a)
        }


  }

  def extractLatLong(js:JsValue):(Double,Double) = {

    val firstAddress:JsValue = js
    println("Formatted Address" + firstAddress.\("formatted_address"))
    val location1:JsValue  = firstAddress.\("geometry").\("location")
    (location1.\("lat").as[Double], location1.\("lng").as[Double])
  }

}
