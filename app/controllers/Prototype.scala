package controllers

import play.api.mvc._
import models.UserCommuteView
import play.api.libs.json._
import play.api.Logger

import mappers.CommuteMapper

/**
 * Created by skunnumkal on 9/7/13.
 */
object Prototype  extends Controller{

  def postAddress = Action(parse.json) {req =>
    val json = req.body
    System.out.println("Json is " + json)
    Logger.info("Address for user" + json)
    Ok("added")

  }

  def postCommute = Action(parse.json){ req =>
    val json = req.body
    Logger.info("Commute for user" + json)
    Ok("added")

  }

  def getCommuteMatches(commuteId:Long) = Action {
    val sample1:UserCommuteView = new UserCommuteView("600","660",1,"Pep Guardioala","123 Main Street","456 Jackson Ave",1,1,1,"","Coach Bayern Munich")
    val sample2:UserCommuteView = new UserCommuteView("600","660",2,"Donald Knuth","123 Main Street","456 Jackson Ave",1,1,1,"","Genius")
    val sample3:UserCommuteView = new UserCommuteView("600","660",3,"Imogene Thomas","123 Main Street","456 Jackson Ave",1,1,1,"","Giantic Software")

    val matchingCommutes:List[UserCommuteView] = List(sample1,sample2,sample3)
    Ok(views.html.commutelist(matchingCommutes,0))


  }

}
