package mappers

import models._
import scala.Some

/**
 * Created by skunnumkal on 7/17/13.
 */
object CommuteMapper {

  def buildCommutes(commutes:List[UserCommute]):List[UserCommuteView] = {


    val viewList:List[UserCommuteView] = for {
      commute <- commutes
      commuteStartAddress = UserAddress.findById(commute.startAddress).get
      commuteEndAddress = UserAddress.findById(commute.endAddress).get
      startAddress = getAddress(commuteStartAddress)
      endAddress = getAddress(commuteEndAddress)
      user = User.findById(commute.user).get
      startTime = computeTime(commute.startTime)
      endTime = computeTime(commute.endTime)
    } yield UserCommuteView(startTime,endTime,user.id.get,user.name,startAddress,endAddress,commute.startAddress,commute.endAddress,commute.id.get)

    viewList
  }


  private def getAddress(address:UserAddress):String = {
    address.line1+", "+address.line2+ ", "+address.city+ ", "+address.state+ ", "+address.zip
  }
  private def computeTime(time:Int):String = {
    val hrs:Int =  time / 60;
    val mins:Int = time % 60;
    val hrsAsString:String = if(hrs==0){"00"} else {hrs.toString}
    val minsAsString:String = if(mins==0){"00"} else {mins.toString}
    hrsAsString + " : " + minsAsString
  }

}
