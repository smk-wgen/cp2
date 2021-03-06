package models

import play.api.libs.json._

/**
 * Created by skunnumkal on 7/17/13.
 */
case class UserCommuteView(val startTime:String, val endTime:String,val userId:Long,val userName:String,val startAddress:String,
                           val endAddress:String,val startAddressId:Long,val endAddressId:Long,val commuteId:Long, val imgSrc:String, val title:String)

object UserCommuteView{
  implicit val writesUserCommuteView: Writes[UserCommuteView] = new Writes[UserCommuteView] {
    def writes(commuteView: UserCommuteView): JsValue = {
        val json = Json.obj(
          "startTime" -> commuteView.startTime,
           "endTime" -> commuteView.endTime,
           "userId" -> commuteView.userId,
           "userName" -> commuteView.userName,
           "startAddress" -> commuteView.startAddress,
           "endAddress" -> commuteView.endAddress,
           "commuteId" ->commuteView.commuteId ,
           "imgSrc" -> commuteView.imgSrc,
           "title" -> commuteView.title
        )
      json
    }
  }
}

