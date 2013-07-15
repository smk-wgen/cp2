package controllers

import play.api._
import play.api.mvc._
import models.{UserCommute, UserAddress, User}
import play.api.libs.json._
import services.MatchingService


object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def dashboard = Action{
    Ok(views.html.dashboard())
  }

  def javascriptRoutes = Action{
    implicit request =>
      Ok(
        Routes.javascriptRouter("jsRoutes")()
      ).as("text/javascript")
  }

  /** Controller action for POSTing chat messages */
    def postMessage = Action(parse.json) {
    request =>
    val json = request.body
    System.out.println(json)
    json.validate[User].fold(
      valid = (user => {
            System.out.println(user)
            val dbUser = User.create(user)
            Ok(Json.toJson(dbUser))
      }),
      invalid = (e => {

            BadRequest("Detected error:"+ JsError.toFlatJson(e))
      })
    )
  }

  def findUser(id:Long) = Action{
    val maybeUser:Option[User] = User.findById(id)
    maybeUser match{
      case Some(user) => Ok(Json.toJson(user))
      case None => BadRequest("Domain Record Not Found")
    }
  }

    def postAddress = Action(parse.json) {req =>
      val json = req.body
      System.out.println("Json is " + json)
      json.validate[UserAddress].fold(
       valid  = (address => {
              val dbAddress = UserAddress.create(address)
              dbAddress match {
                case Some(storedAddress) => Ok(Json.toJson(storedAddress))
                case None => BadRequest("Internal server error")
              }
       }),
      invalid = (e => {      println(e)
        BadRequest("Detected error " + JsError.toFlatJson(e))})
      )


    }

    def postCommute = Action(parse.json){ req =>
        val json = req.body
        json.validate[UserCommute].fold(
        valid = (validCommute => {
          val dbCommute = UserCommute.create(validCommute)
          dbCommute match{
            case Some(storedCommute) => Ok(Json.toJson(storedCommute))
            case None => ServiceUnavailable("Internal Server error")
          }
        }),
        invalid = (e => {BadRequest("Detected error " + JsError.toFlatJson(e))})
        )
    }

    def getCommuteMatches(commuteId:Long) = Action {

     val commute:Option[UserCommute] = UserCommute.findById(commuteId)
     commute match{
       case Some(dbCommute) => {
         val allCommutes:List[UserCommute] = UserCommute.findAllCommutes
         val matchingCommutes:List[UserCommute] = MatchingService.getMatches(dbCommute,allCommutes)
         Ok(Json.toJson(matchingCommutes))
       }
       case _ => BadRequest("Didnt find the record")
     }

    }

  def getProfile(id:Long) = Action {

       val maybeUser = User.findById(id)
       maybeUser match{
         case Some(user) => {
          val commutes = UserCommute.findCommuteByUserId(user.id.get)

           Ok(views.html.profile(user))
         }
         case _ => BadRequest("Couldnt find user")
        }
  }

}