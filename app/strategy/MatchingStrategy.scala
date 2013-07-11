package strategy

import models._

/**
 * Created by skunnumkal on 7/9/13.
 */
trait MatchingStrategy {

  def isMatch(aCommute:UserCommute,bCommute:UserCommute):Boolean

}
