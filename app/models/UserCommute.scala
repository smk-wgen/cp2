package models

import anorm._
import play.api.db.DB
import play.api.Play.current

/**
 * Created by skunnumkal on 7/8/13.
 */
case class UserCommute(id: Pk[Long],startTime:Int,endTime:Int,startAddress:UserAddress,endAddress:UserAddress,user:User)

object UserCommute{

  def create(userCommute:UserCommute):UserCommute = {
    DB.withConnection {
      implicit connection =>
        val maybeId:Option[Long] = SQL("insert into user_commute(window_start, window_end,from_address, to_address, user_id) " +
          "values ({window_start}, {window_end}, {from_address}, {to_address},  {userId});").on(
          'window_start -> userCommute.startTime,
          'window_end -> userCommute.endTime,
          'from_address -> userCommute.startAddress.id,
          'to_address -> userCommute.endAddress.id,
          'userId -> userCommute.user.id


        ).executeInsert()

        val dbCommute:UserCommute = userCommute.copy(id = Id(maybeId.get))
        dbCommute
    }
  }
}
