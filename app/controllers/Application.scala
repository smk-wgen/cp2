package controllers

import play.api._
import play.api.mvc._
import models.{UserCommute, UserAddress, User}
import play.api.libs.json._
import datamappers.{UserCommuteMapper, UserAddressMapper}

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
      val maybeUserAddress:Option[UserAddress] = UserAddressMapper.mapJsonToUserAddress(json)
      System.out.println(maybeUserAddress.get)
      maybeUserAddress match{
        case Some(userAddress) => {
          val dbAddress = UserAddress.create(userAddress)
          Ok(UserAddressMapper.mapUserAddressToJson(dbAddress))
        }
        case None => BadRequest("shit blew up")
      }


    }

    def postCommute = Action(parse.json){ req =>
        val json = req.body
        val maybeCommute:Option[UserCommute] = UserCommuteMapper.mapJsonToUserCommute(json)
      maybeCommute match{
        case Some(commute) => {
          val dbCommute = UserCommute.create(commute)
          Ok(UserCommuteMapper.mapUserCommuteToJson(dbCommute))
        }
        case None => BadRequest("shit blew up")
      }
    }


  
}