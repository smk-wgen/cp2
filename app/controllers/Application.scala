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
    def postMessage = Action(parse.json) { request =>
    request.body.validate[User].map{
      //case (email,name, linkedInId,cell,imageUrl,employer,city,gender) => {
      //val user: JsResult[User] =
      case (user:User) => {
          //User user = new User(NotAssigned,email,name,linkedInId,cell,imageUrl,employer,city,gender)
          User.create(user)

        Ok(Json.toJson(user))
      }
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
    }
  }

    def postAddress = Action(parse.json) {req => Ok }
  
}