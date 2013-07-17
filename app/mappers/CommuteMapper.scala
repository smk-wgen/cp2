package mappers

import views.UserCommuteView
import models._

/**
 * Created by skunnumkal on 7/17/13.
 */
object CommuteMapper {

  def buildCommutes(user:User,addresses:List[UserAddress],commutes:List[UserCommute]):List[UserCommuteView] = {


    val viewList:List[UserCommuteView] = for {
      commute <- commutes
      startAddress = getAddress(addresses,commute.startAddress)
      endAddress = getAddress(addresses,commute.endAddress)
      startTime = computeTime(commute.startTime)
      endTime = computeTime(commute.endTime)
    } yield UserCommuteView(startTime,endTime,user.id.get,user.name,startAddress,endAddress,commute.startAddress,commute.endAddress)

    viewList
  }


  private def getAddress(list:List[UserAddress],addressId:Long):String = {
    val maybeAddress:Option[UserAddress] = list.find(currAddress => currAddress.id.get.equals(addressId))
    maybeAddress match {
      case Some(address) => address.line1+", "+address.line2+ ", "+address.city+ ", "+address.state+ ", "+address.zip
      case None => ""
    }
  }
  private def computeTime(time:Int):String = {
    if(time>=720){   //PM
      val hrs:Int = (time - 720)/60;
      val mins:Int = (time - 720)%60;
         hrs.toString+ " : "+ mins.toString+" PM"
    }else{
      (time/60).toString+ " : "+(time%60).toString+" AM"
    }
  }

}
