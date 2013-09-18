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
        val differentCommute:Boolean = !commute.id.equals(usersCommute.id)   && !commute.userId.equals(usersCommute.userId)
        if(differentCommute){
          try{
            strategy.isMatch(commute,usersCommute)
          } catch {
            case re:RuntimeException => {
              //TODO flag such commutes
              println("Had an issue with address" + re.getMessage)
              false
            }
          }

        }
        else false


      })
      matches
  }



}
