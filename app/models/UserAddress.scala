package models

import anorm._
import play.api.libs.json._
import play.api.db.DB
import play.api.libs.functional.syntax._
import scala.Some
import play.api.Play.current
import anorm.SqlParser._
import anorm.~
import anorm.Id
import models.User.{PkWriter, PkReader}

/**
 * Created by skunnumkal on 7/1/13.
 */
case class UserAddress(id: Pk[Long],label:String,line1:String,line2:String,city:String,state:String,zip:Int,user:Long)

object UserAddress{

  implicit val addressReads = (
    (__ \ "id").read(PkReader)   and
      (__ \ "label").read[String] and
      (__ \ "line1").read[String] and
      (__ \ "line2").read[String] and
      (__ \ "city").read[String] and
      (__ \ "state").read[String] and
      (__ \ "zip").read[Int] and
      (__ \ "user").read[Long]
    )(UserAddress.apply _)

  implicit val addressWrites = (
    (__ \ "id").write(PkWriter)   and
      (__ \ "label").write[String] and
      (__ \ "line1").write[String] and
      (__ \ "line2").write[String] and
      (__ \ "city").write[String] and
      (__ \ "state").write[String] and
      (__ \ "zip").write[Int] and
      (__ \ "user").write[Long]
    )(unlift(UserAddress.unapply))

  def create(address:UserAddress):Option[UserAddress] = {
    DB.withConnection {
      implicit connection =>
        val maybeId:Option[Long] = SQL("insert into user_address(line1, line2,city, state, zip,user_id) " +
          "values ({line1}, {line2}, {city}, {state}, {zip}, {userId});").on(
          'line1 -> address.line1,
          'line2 -> address.line2,
          'city -> address.city,
          'state -> address.state,
          'zip -> address.zip,
          'userId -> address.user

        ).executeInsert()
      val maybeAddress:Option[UserAddress] = maybeId match {
        case Some(id) =>  Some(address.copy(id = Id(maybeId.get)))
        case None => None
      }

       maybeAddress
    }
  }
  def findById(id:Long):Option[UserAddress] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user_address where id = {id}").on(
        'id -> id
      ).as(UserAddress.userAddressDBRecordParser.singleOpt)
    }
  }

  def findAddressesByUser(id:Long):List[UserAddress] = {
    DB.withConnection {   implicit  connection =>
      SQL(
        "select * from user_address where user_id = {user_id}").on(
        'user_id -> id
      ).as(UserAddress.userAddressDBRecordParser *)
    }
  }
  val userAddressDBRecordParser = {
    get[Pk[Long]]("user_address.id") ~
    get[String]("user_address.label") ~
      get[String]("user_address.line1") ~
      get[String]("user_address.line2") ~
      get[String]("user_address.city") ~
      get[String]("user_address.state")~
      get[Int]("user_address.zip")~
      get[Long]("user_address.user_id")map {
      case id~label~line1~line2~city~state~zip~userId =>
        UserAddress(id, label,line1, line2, city,state, zip,userId)
    }
  }
}
