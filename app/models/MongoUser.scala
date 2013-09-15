package models

import play.api.Play.current

import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.mvc.Results.Todo


/**
 * Created by skunnumkal on 9/4/13.
 */
case class MongoUser(id: ObjectId = new ObjectId, username: String,title:String,linkedInId:String,
                     imageUrl:String,addresses:List[MongoUserAddress] = Nil)

object MongoUser extends ModelCompanion[MongoUser, ObjectId]{

  val dao = new SalatDAO[MongoUser, ObjectId](collection = mongoCollection("users")) {}

  def json2Object(name:String,linkedInId:String,imageUrl:String,title:String) = new MongoUser(new ObjectId,name,title,linkedInId,imageUrl)

  def object2Json(user:MongoUser):JsObject = {
    val jsonObj = Json.obj(
      "id" -> user.id.toString, "username" -> user.username, "title" -> user.title, "linkedInId" -> user.linkedInId,
      "imageUrl" -> user.imageUrl, "addresses" -> user.addresses
    )
    jsonObj
  }
  def findOneByLinkedinId(linkedinId:String):Option[MongoUser] = dao.findOne(MongoDBObject("linkedInId" -> linkedinId))

//  def addUserAddress(id:ObjectId,label:String,address:String) = {
//    val maybeUser:Option[MongoUser] = dao.findOne(MongoDBObject("_id" -> id))
//    maybeUser match {
//      case Some(user) => {
//                         val anAddress = new MongoUserAddress(address,label)
//                         val newUser = user.copy(addresses = user.addresses :+ anAddress)
//                          MongoUser.update(MongoDBObject("_id" -> id),newUser,true,true,WriteConcern.Safe)
//                        }
//      case None => { throw new RuntimeException("Not found")}
//    }
//  }
  def create(aMongoUser:MongoUser) = {
    MongoUser.save(aMongoUser)
    aMongoUser
  }

  implicit val userReads = (
      (__ \ "username").read[String] and
      (__ \ "linkedInId").read[String] and
      (__ \ "imageUrl").read[String] and
      (__ \ "title").read[String]
    )(MongoUser.json2Object _)



  implicit object ObjectIdWriter extends Writes[ObjectId]{
    def writes(id:ObjectId):JsValue = {
      JsString(id.toString)
    }
  }

}

