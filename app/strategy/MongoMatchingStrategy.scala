package strategy

import models._

/**
 * Created by skunnumkal on 9/17/13.
 */
class MongoMatchingStrategy extends MatchingStrategy{

  def isMatch(aCommute:MongoUserCommute,bCommute:MongoUserCommute):Boolean ={
      val timeOverlap = this.timeOverLaps(aCommute.startTime,bCommute.startTime,aCommute.endTime,
        bCommute.endTime)
      if(timeOverlap){
        val startAddressMatch = AddressMatcher.isAddressMatch(aCommute.startAddress,bCommute.startAddress,
          MongoMatchingStrategy.threshold)
        if(startAddressMatch){
          val endAddressMatch = AddressMatcher.isAddressMatch(aCommute.endAddress,bCommute.endAddress,
            MongoMatchingStrategy.threshold)
          endAddressMatch
        }
        else
          false
      }
      else
        false


  }
}
object MongoMatchingStrategy{
  val threshold:Double = 0.3;
}
