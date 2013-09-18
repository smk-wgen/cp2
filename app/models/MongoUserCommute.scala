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

  def findAll():List[MongoUserCommute] = {
         val allUsers:List[MongoUser] = MongoUser.findAll().toList
         val commutes = accumulate(allUsers,List[MongoUserCommute]())
         commutes

  }
  private def accumulate(users:List[MongoUser],acc:List[MongoUserCommute]):List[MongoUserCommute] = {
    if(users.isEmpty){
      acc
    }
    else{
      val user = users.head
      accumulate(users.tail,acc ++ user.commutes)
    }

  }

  def findById(id:String):Option[MongoUserCommute] = {

    val allUsers:List[MongoUser] = MongoUser.findAll().toList
    val commuteId = new ObjectId(id)
    println("Searching for commute iwth id=" + id)
    val matchingCommutes:List[MongoUserCommute] = for( user <- allUsers;
         commute <- user.commutes;
         if(commute.id.equals(commuteId)))yield(commute)

    matchingCommutes.size match{
      case 0 => None
      case 1 => Some(matchingCommutes.head)
      case _ => throw new RuntimeException("Found duplicate commutes in db")
    }



  }
}

