package strategy

import models._

/**
 * Created by skunnumkal on 9/17/13.
 */
class MongoMatchingStrategy extends MatchingStrategy{

  def isMatch(aCommute:MongoUserCommute,bCommute:MongoUserCommute):Boolean ={
      val timeOverlap = this.timeOverLaps(aCommute.startTime,bCommute.startTime,aCommute.endTime,
        bCommute.endTime)
      timeOverlap
  }
}
object MongoMatchingStrategy{
  val threshold:Double = 0.3;
}
