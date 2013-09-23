package models

import play.api.Play.current

import com.novus.salat.global._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoURI


import se.radley.plugin.salat._
import mongoContext._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import play.api.Play


/**
 * Created by skunnumkal on 9/4/13.
 */
case class MongoUser(@Key("_id") id: ObjectId = new ObjectId, username: String,title:String,linkedInId:String,
                     imageUrl:String,addresses:List[MongoUserAddress] = Nil,commutes:List[MongoUserCommute] = Nil)

object MongoUser extends ModelCompanion[MongoUser, ObjectId]{

  val db:String = Play.current.configuration.getString("mongodb.default.db").getOrElse("my_db")
  val userCollection = MongoConnection(MongoURI(mongoUri))(db)("users")
  val dao = new SalatDAO[MongoUser, ObjectId](collection = userCollection) {}

  def json2Object(name:String,linkedInId:String,imageUrl:String,title:String) = new MongoUser(new ObjectId,name,title,linkedInId,imageUrl)
//
  def object2Json(user:MongoUser):JsObject = {
    println("The object is " + user.username  + " : id " + user.id.toString)
    val jsonObj = Json.obj(
      "id" -> user.id.toString , "username" -> user.username, "title" -> user.title, "linkedInId" -> user.linkedInId,
      "imageUrl" -> user.imageUrl, "addresses" -> user.addresses , "commutes" -> user.commutes
    )
    jsonObj
  }
  def findOneByLinkedinId(linkedinId:String):Option[MongoUser] = dao.findOne(MongoDBObject("linkedInId" -> linkedinId))


  def addUserAddress(id:String,label:String,address:String):Option[MongoUserAddress] = {
    /*
    MyModel.update(q = MongoDBObject("_id" -> _id),
     |     o = MongoDBObject("$set" -> MongoDBObject("x" -> "Test2")),
     |     upsert = false, multi = false, wc = MyModel.dao.collection.writeConcern)
     */
    val maybeUser:Option[MongoUser] = findOneById(new ObjectId(id))
    maybeUser match {
      case Some(user) => {
                         val anAddress = new MongoUserAddress(address,label)
                         val newUser = user.copy(addresses = user.addresses :+ anAddress)
                         println("Addresses #size" + newUser.addresses.size)
                         //val result = MongoUser.update(MongoDBObject("_id" -> user.id),newUser,false,false)
                         val cr = MongoUser.save(newUser,WriteConcern.Safe)
                         if(cr.getLastError().ok())
                         {
                             Some(anAddress)
                         }
                         else{
                           None
                         }
                        }
      case None => { println("Dindt find user")
        throw new RuntimeException("Not found")}
    }
  }

  def addUserCommute(id:String,commute:MongoUserCommute):Option[MongoUserCommute] = {
    val maybeUser:Option[MongoUser] = findOneById(new ObjectId(id))
    maybeUser match {
      case Some(user) => {
        val aCommute:MongoUserCommute = commute.copy(userId = user.id)
        val newUser = user.copy(commutes = user.commutes :+ aCommute)
        println("Commutes #size" + newUser.commutes.size)
        //val result = MongoUser.update(MongoDBObject("_id" -> user.id),newUser,false,false)
        val cr = MongoUser.save(newUser,WriteConcern.Safe)
        if(cr.getLastError().ok())
        {
          Some(aCommute)
        }
        else{
          None
        }
      }
      case None => { println("Didnt find user to add commute")
        throw new RuntimeException("Not found")}
    }


  }


  implicit val userReads = (
      (__ \ "username").read[String] and
      (__ \ "linkedInId").read[String] and
      (__ \ "imageUrl").read[String] and
      (__ \ "title").read[String]
    )(MongoUser.json2Object _)


  def create(aUser:MongoUser):MongoUser = {
    val someId:Option[ObjectId]  = MongoUser.insert(aUser)
    val aid:ObjectId = someId.getOrElse(new ObjectId)
    val dbUser = aUser.copy(id = aid)
    dbUser
  }

}

