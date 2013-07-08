package datamappers

import models.{User, UserAddress}
import play.api.libs.json._
import anorm.NotAssigned

/**
 * Created by skunnumkal on 7/4/13.
 */
object UserAddressMapper {

  def mapJsonToUserAddress(js:JsValue):Option[UserAddress] = {

    val label:String = js.\("label").as[String]
    val line1:String = js.\("line1").as[String]
    val line2:String = js.\("line2").as[String]
    val city:String = js.\("city").as[String]
    val state:String = js.\("state").as[String]
    val zip:String = js.\("zip").as[String]
    val userId:String = js.\("userId").as[String]
    System.out.println("User Id " + userId)
    val user:User = User.findById(userId.toLong).getOrElse(null)
    val zipInt:Int = zip.toInt
    System.out.println("Zip as Int" + zipInt)
    if(user == null)
       None
    else{
      Some(UserAddress(NotAssigned,label,line1,line2,city,state,zipInt,user))
    }


  }

  def mapUserAddressToJson(address:UserAddress):JsValue = {
     val json = Json.obj(
        "id" -> address.id.get,
        "line1" -> address.line1,
        "line2" -> address.line2,
        "city" -> address.city,
        "state" -> address.state,
        "zip" -> address.zip,
        "userId" -> address.user.id.get ,
        "label" -> address.label
     )
    json
  }



}
