package strategy

import models._

/**
 * Created by skunnumkal on 7/9/13.
 */
trait MatchingStrategy {

  def isMatch(aCommute:MongoUserCommute,bCommute:MongoUserCommute):Boolean

  def timeOverLaps(aStart:Int,bStart:Int,aEnd:Int,bEnd:Int):Boolean = {
    if(aStart<=bStart){
      if(aEnd<bStart)
        false
      else
        true
    }else{
      if(bEnd<aStart)
        false
      else
        true
    }
  }
}
