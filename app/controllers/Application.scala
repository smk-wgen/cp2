package controllers

import play.api._
import play.api.mvc._
import models.User
import play.api.libs.json._

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

    def postAddress = Action(parse.json) {req => Ok }
  
}