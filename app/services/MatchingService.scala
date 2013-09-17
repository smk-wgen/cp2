package services

import models.{MongoUserCommute, UserCommute}
import strategy._

/**
 * Created by skunnumkal on 7/11/13.
 */
object MatchingService {

  val strategy:MatchingStrategy = new MongoMatchingStrategy()
  def getMatches(usersCommute:MongoUserCommute,otherCommutes:List[MongoUserCommute]):List[MongoUserCommute] = {
      val matches:List[MongoUserCommute] = otherCommutes.filter(commute => {
        !commute.id.equals(usersCommute.id)   && !commute.userId.equals(usersCommute.userId) &&
          strategy.isMatch(commute,usersCommute)

      })
      matches
  }



}
