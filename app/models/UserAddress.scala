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

/**
 * Created by skunnumkal on 7/1/13.
 */
case class UserAddress(id: Pk[Long],label:String,line1:String,line2:String,city:String,state:String,zip:Int,user:User)

object UserAddress{



  def create(address:UserAddress):UserAddress = {
    DB.withConnection {
      implicit connection =>
        val maybeId:Option[Long] = SQL("insert into user_address(line1, line2,city, state, zip,user_id) " +
          "values ({line1}, {line2}, {city}, {state}, {zip}, {userId});").on(
          'line1 -> address.line1,
          'line2 -> address.line2,
          'city -> address.city,
          'state -> address.state,
          'zip -> address.zip,
          'userId -> address.user.id

        ).executeInsert()

        val dbAddress:UserAddress = address.copy(id = Id(maybeId.get))
        dbAddress




    }
  }
  def findById(id:Long):Option[UserAddress] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user_address where id = {id}").on(
        'id -> id
      ).as(UserAddress.userAddressDBRecordParser.singleOpt)
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
        UserAddress(id, label,line1, line2, city,state, zip,User.findById(userId).get)
    }
  }
}
