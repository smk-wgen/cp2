package mappers

import models._
import play.api.libs.json._

/**
 * Created by skunnumkal on 9/21/13.
 */
object CommuteMapper {

  def mapCommutes(commutes:List[MongoUserCommute]):List[JsValue] = {
          val result:List[JsValue] = for{
            commute <- commutes
               user <- MongoUser.findOneById(commute.userId)

          }yield (Json.obj("username" -> user.username,"title"-> user.title,"imageUrl" -> user.imageUrl))

    result
  }

  def mapConnections(commutes:List[MongoUserCommute]):List[(MongoUser,MongoUserCommute)] = {
    val result:List[(MongoUser,MongoUserCommute)] = for{
      commute <- commutes
      user <- MongoUser.findOneById(commute.userId)
    } yield(user,commute)
    result
  }

}
