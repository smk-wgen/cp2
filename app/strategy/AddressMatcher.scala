package strategy

import models.UserAddress
import play.api.libs.json._

/**
 * Created by skunnumkal on 7/11/13.
 */
object AddressMatcher {

  def isAddressMatch(a:UserAddress,b:UserAddress,threshold:Double):Boolean = {

        val json1:JsValue = Json.parse(GMapHelper.getAddressJson(a))
        val json2:JsValue = Json.parse(GMapHelper.getAddressJson(b))

        val (lat1,lon1):(Double,Double) =  extractLatLong(json1)
        val (lat2,lon2):(Double,Double) = extractLatLong(json2)
        val distance:Double = Math.sqrt((lat2-lat1)*(lat2-lat1) + (lon2-lon1)*(lon2-lon1))
        return distance <= threshold

  }

  def extractLatLong(js:JsValue):(Double,Double) = {
    val location1:JsValue  = js.\("results").\("geometry").\("location")
    (location1.\("lat").as[Double], location1.\("lon").as[Double])
  }

}