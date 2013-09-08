package models

import play.api.Play.current

import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import mongoContext._

/**
 * Created by skunnumkal on 9/4/13.
 */
case class MongoUser(id: ObjectId = new ObjectId, username: String,title:String,linkedinId:String,
                     imageUrl:String,addresses:List[String] = Nil)

object MongoUser extends ModelCompanion[MongoUser, ObjectId] {
  val dao = new SalatDAO[MongoUser, ObjectId](collection = mongoCollection("users")) {}

  def findOneByUsername(username: String): Option[MongoUser] = dao.findOne(MongoDBObject("username" -> username))

  def findByLinkedinId(linkedinId:String):Option[MongoUser] = dao.findOne(MongoDBObject("linkedinId" -> linkedinId))

  def addUserAddress(id:ObjectId,address:String) = {
    val maybeUser:Option[MongoUser] = dao.findOne(MongoDBObject("_id" -> id))
    maybeUser match {
      case Some(user) => {

                         val newUser = user.copy(addresses = user.addresses :+ address)
                          MongoUser.update(MongoDBObject("_id" -> id),newUser,true,true,WriteConcern.Safe)
                        }
      case None => { throw new RuntimeException("Not found")}
    }
  }
}
