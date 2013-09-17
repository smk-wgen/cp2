package models

import com.mongodb.casbah.Imports._
import play.api.libs.json._
import com.novus.salat.annotations._
import play.api.libs.functional.syntax._

/**
 * Created by skunnumkal on 9/16/13.
 */

case class MongoUserCommute(@Key("_id") id: ObjectId = new ObjectId, label:String,startTime: Int,endTime:Int,startAddress:String,
                     endAddress:String,userId:ObjectId)

object MongoUserCommute {
  def json2Object(label:String,startTime:Int,endTime:Int,startAddress:String,endAddress:String,userIdStr:String) =
    new MongoUserCommute(new ObjectId,label,startTime,endTime,startAddress,endAddress,new ObjectId(userIdStr))

  def commute2json(aCommute:MongoUserCommute) = {
    val json = Json.obj("id" -> aCommute.id.toString, "label" -> aCommute.label, "startTime" -> aCommute.startTime,
    "endTime" -> aCommute.endTime, "startAddress" -> aCommute.startAddress, "endAddress" -> aCommute.endAddress,
    "userId" -> aCommute.userId.toString)
    json
  }
  implicit val mucReads = (
    (__ \ "label").read[String] and
      (__ \ "startTime").read(UserAddress.StringToIntReader) and
      (__ \ "endTime").read(UserAddress.StringToIntReader) and
      (__ \ "startAddress").read[String] and
      (__ \ "endAddress").read[String] and
      (__ \ "userId").read[String]
    )(MongoUserCommute.json2Object _)

//  implicit val mucWrites = (
//    (__ \ "id").write(ObjectIdWriter) and
//      (__ \ "label").write[String] and
//      (__ \ "startTime").write[Int] and
//      (__ \ "endTime").write[Int] and
//      (__ \ "startAddress").write[String] and
//      (__ \ "endAddress").write[String] and
//      (__ \ "userId").write(ObjectIdWriter)
//    )(MongoUserCommute.object2json _)
  implicit object CommuteWriter extends Writes[MongoUserCommute]{
    def writes(aCommute:MongoUserCommute):JsValue  = {
      commute2json(aCommute)
    }
  }
  implicit object ObjectIdWriter extends Writes[ObjectId]{
    def writes(id:ObjectId):JsValue = {
      JsString(id.toString)
    }
  }
}

