package models

import anorm._
import play.api.libs.json._
import play.api.db.DB
import play.api.libs.functional.syntax._
import scala.Some
import play.api.Play.current

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
}
