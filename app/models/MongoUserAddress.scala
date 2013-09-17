package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by skunnumkal on 9/12/13.
 */
case class MongoUserAddress(address:String,label:String)

object MongoUserAddress{

  implicit val muaReads = (
    (__ \ "address").read[String] and
      (__ \ "label").read[String]
    )(MongoUserAddress.apply _)

  implicit val muaWrites = (
    (__ \ "address").write[String] and
      (__  \ "label").write[String]
    )(unlift(MongoUserAddress.unapply))



}
