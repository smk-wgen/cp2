package strategy

import models._

/**
 * Created by skunnumkal on 7/9/13.
 */
class DefaultMatchingStrategy extends MatchingStrategy{

  def isMatch(aCommute:UserCommute,bCommute:UserCommute):Boolean ={
    val aUser = User.findById(aCommute.user).getOrElse(null)
    val bUser = User.findById(bCommute.user).getOrElse(null)
    if(aUser == null || bUser == null)
      false
    if(!aUser.gender.equals(bUser.gender))
      false
    if(aCommute.startTime<=bCommute.startTime){
         if(aCommute.endTime<bCommute.startTime)
           false
    }else{
         if(bCommute.endTime<aCommute.startTime)
           false
    }
    val aStartAddress = UserAddress.findById(aCommute.startAddress).get
    val bStartAddress = UserAddress.findById(bCommute.startAddress).get
    assert(aStartAddress != null, "Start Address cannot be null for " + aCommute)
    assert(bStartAddress != null, "Start Address cannot be null for " + bCommute)

    false
  }


}
