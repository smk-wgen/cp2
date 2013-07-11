package datamappers

import models._
import play.api.libs.json._
import anorm.NotAssigned

/**
 * Created by skunnumkal on 7/8/13.
 */
object UserCommuteMapper {

  def mapJsonToUserCommute(js:JsValue):Option[UserCommute] = {
        val startTime:Int = js.\("startTime").as[String].toInt
        val endTime:Int = js.\("endTime").as[String].toInt
        val userId:Long = js.\("userId").as[String].toLong
        val startAddressId:Long = js.\("startAddress").as[String].toLong
        val endAddressId:Long = js.\("endAddress").as[String].toLong
        val user:User = User.findById(userId).getOrElse(null)
        val startAddress:UserAddress = UserAddress.findById(startAddressId).getOrElse(null)
        val endAddress:UserAddress = UserAddress.findById(endAddressId).getOrElse(null)
        if(user == null || startAddress == null || endAddress == null){
          None
        } else if(startAddress.id.get.equals(endAddress.id.get)){
          None
        }
          else{
          Some(UserCommute(NotAssigned,startTime,endTime,startAddressId,endAddressId,userId))
        }

  }

  def mapUserCommuteToJson(userCommute:UserCommute):JsValue = {
    val json = Json.obj(
      "id" -> userCommute.id.get,
      "startTime" -> userCommute.startTime,
      "endTime" -> userCommute.endTime,
      "startAddress" -> userCommute.startAddress,
      "endAddress" -> userCommute.endAddress,
      "userId" -> userCommute.user
    )
    json
  }

}
