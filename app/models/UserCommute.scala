package models

import anorm._
import play.api.db.DB
import play.api.Play.current
import anorm.SqlParser._
import anorm.~
import anorm.Id

/**
 * Created by skunnumkal on 7/8/13.
 * In this case im not going to give references because zentasks example just uses longs and not objects
 */
case class UserCommute(id: Pk[Long],startTime:Int,endTime:Int,startAddress:Long,endAddress:Long,user:Long)

object UserCommute{

  def create(userCommute:UserCommute):UserCommute = {
    DB.withConnection {
      implicit connection =>
        val maybeId:Option[Long] = SQL("insert into user_commute(window_start, window_end,from_address, to_address, user_id) " +
          "values ({window_start}, {window_end}, {from_address}, {to_address},  {userId});").on(
          'window_start -> userCommute.startTime,
          'window_end -> userCommute.endTime,
          'from_address -> userCommute.startAddress,
          'to_address -> userCommute.endAddress,
          'userId -> userCommute.user


        ).executeInsert()

        val dbCommute:UserCommute = userCommute.copy(id = Id(maybeId.get))
        dbCommute
    }
  }

  def findCommuteByUserId(userId:Long):List[UserCommute] = {
    DB.withConnection {   implicit  connection =>
      SQL(
        "select * from user_commute where user_id = {user_id}").on(
        'user_id -> userId
      ).as(UserCommute.userCommuteDBRecordParser *)
    }
  }


  val userCommuteDBRecordParser = {
    get[Pk[Long]]("user_commute.id") ~
      get[Int]("user_commute.window_start") ~
      get[Int]("user_commute.window_end") ~
      get[Long]("user_commute.from_address") ~
      get[Long]("user_commute.to_address") ~
      get[Long]("user_commute.user_id")map {
      case id~window_start~window_end~fromAddress~toAddress~userId =>
        UserCommute(id, window_start,window_end, fromAddress, toAddress,userId)
    }
  }

}
