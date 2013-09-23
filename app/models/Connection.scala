package models

import play.api.Play
import com.novus.salat.global._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.novus.salat.dao._


import se.radley.plugin.salat._
import mongoContext._

/**
 * Created by skunnumkal on 9/22/13.
 */

case class Connection(@Key("_id") id: ObjectId = new ObjectId, fromId: ObjectId,toId: ObjectId,commuteId:ObjectId)

object Connection extends ModelCompanion[Connection, ObjectId]{
  val db:String = Play.current.configuration.getString("mongodb.default.db").getOrElse("my_db")
  val connectionsCollection = MongoConnection()(db)("connections")
  val dao = new SalatDAO[Connection, ObjectId](collection = connectionsCollection) {}
}
