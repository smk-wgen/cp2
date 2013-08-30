package strategy

import models._


/**
 * Created by skunnumkal on 7/9/13.
 */
class DefaultMatchingStrategy extends MatchingStrategy{

  def isMatch(aCommute:UserCommute,bCommute:UserCommute):Boolean ={
    val aUser = User.findById(aCommute.user).getOrElse(null)
    val bUser = User.findById(bCommute.user).getOrElse(null)
    assert(aUser!= null,"Cannot compare a null user")
    assert(bUser!=null, "Cannot compare a null user")

    val timeOverlaps:Boolean = if(aCommute.startTime<=bCommute.startTime){
            if(aCommute.endTime<bCommute.startTime)
                 false
            else
              true
    }else{
         if(bCommute.endTime<aCommute.startTime)
           false
         else
           true
    }
    val aStartAddress = UserAddress.findById(aCommute.startAddress).getOrElse(null)
    val bStartAddress = UserAddress.findById(bCommute.startAddress).getOrElse(null)
    val aEndAddress = UserAddress.findById(aCommute.endAddress).getOrElse(null)
    val bEndAddress = UserAddress.findById(bCommute.endAddress).getOrElse(null)

    assert(aStartAddress != null, "Start Address cannot be null for " + aCommute)
    assert(bStartAddress != null, "Start Address cannot be null for " + bCommute)
    assert(aEndAddress != null, "End Address cannot be null for " + aCommute)
    assert(bEndAddress != null, "End Address cannot be null for " + bCommute)
    val threshold:Double = 0.3;
    System.out.println("Comparing commutes" + aCommute.id + " and" + bCommute.id)
    System.out.println("Time overlaps?" + timeOverlaps)

    val startAddressMatch = AddressMatcher.isAddressMatch(aStartAddress,bStartAddress,threshold)
    System.out.println("Start Addresses Match? " + startAddressMatch)
    val endAddressMatch = AddressMatcher.isAddressMatch(aEndAddress,bEndAddress,threshold)
    System.out.println("End Addresses Match? " + endAddressMatch)
    timeOverlaps /*&& aUser.gender.equals(bUser.gender)*/ && startAddressMatch && endAddressMatch


  }


}
