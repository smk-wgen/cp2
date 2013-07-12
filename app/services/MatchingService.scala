package services

import models.UserCommute
import strategy.{MatchingStrategy, DefaultMatchingStrategy}

/**
 * Created by skunnumkal on 7/11/13.
 */
object MatchingService {

  val strategy:MatchingStrategy = new DefaultMatchingStrategy()
  def getMatches(usersCommute:UserCommute,otherCommutes:List[UserCommute]):List[UserCommute] = {
      val matches:List[UserCommute] = otherCommutes.filter(commute => {
        commute.id != usersCommute.id   && strategy.isMatch(commute,usersCommute)

      })
      matches
  }

}
