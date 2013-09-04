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
case class MongoUser(id: ObjectId = new ObjectId, username: String,title:String,linkedinId:String,imageUrl:String)
