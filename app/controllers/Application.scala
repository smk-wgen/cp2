package controllers

import play.api._
import play.api.mvc._
import models.{UserCommute, UserAddress, User}
import play.api.libs.json._
import services.MatchingService
import mappers.CommuteMapper
import play.api.Play
import play.api.db.DB
import anorm._
import play.api.Play.current

import scala.Some


object Application extends Controller {
  
  def index = Action {
    val apiKey = Play.current.configuration.getString("linkedin.api.key").getOrElse("91zpl9j92hr9")
    //Ok(views.html.main("LandingPage",apiKey))
    Ok(views.html.error())
  }

  def dashboard(id:Long) = Action{
    val maybeUser  = User.findById(id)
    maybeUser match {
      case Some(user) => Ok(views.html.dashboard(user))
      case None => BadRequest("Could not find User")
    }

  }

  def getWizard = Action{
    Ok(views.html.profile())
  }

  def javascriptRoutes = Action{
    implicit request =>
      Ok(
        Routes.javascriptRouter("jsRoutes")()
      ).as("text/javascript")
  }


    def createUser = Action(parse.json) {
    request =>
    val json = request.body
    System.out.println(json)
    json.validate[User].fold(
      valid = (user => {
            val dbUser = User.create(user)
            val userJson = Json.toJson(dbUser)
            System.out.println("Json User" + userJson)
            Ok(userJson)
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

  def findUserByLinkedInId(linkedInId:String) = Action {


      val maybeExistingUser:Option[User] = User.findByLinkedInId(linkedInId)

      maybeExistingUser match {
          case Some(existingUser) => {
            val jsRespBody = Json.obj("isNew" -> false,"user" -> existingUser)
            Ok(Json.toJson(jsRespBody))
          }
          case None => {
            val jsRespBody = Json.obj("isNew" -> true)
            Ok(Json.toJson(jsRespBody))
          }

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
                case None => ServiceUnavailable("Internal server error")
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
          Ok(views.html.commutelist(CommuteMapper.buildCommutes(matchingCommutes),dbCommute.user))
         //Ok(Json.toJson(matchingCommutes))
       }
       case _ => BadRequest("Didnt find the commute record")
     }

    }


  def getUserAddresses(id:Long) = Action {
    val addressList:List[UserAddress] = UserAddress.findAddressesByUser(id)
    Ok(Json.toJson(addressList))
  }

  def getUserCommutes(id:Long) = Action{

    val maybeUser:Option[User] = User.findById(id)
    maybeUser match{
      case Some(user) => {
        val commuteList:List[UserCommute] = UserCommute.findCommuteByUserId(id)
        //Ok(Json.toJson(CommuteMapper.buildCommutes(commuteList)))
        Ok(Json.toJson(commuteList))
      }
      case None => {
        BadRequest("Didnt find user in db")
      }
    }

  }

  def connect = Action(parse.json){  req =>
    val json = req.body
    val fromUser = json.\("fromUserId").as[Long]
    val toUser = json.\("toUserId").as[Long]
    val commuteId = json.\("commuteId").as[Long]

    DB.withConnection {
      implicit connection =>
        SQL("insert into user_connection(from_user,to_user, commute_id) " +
          "values ({from_user},{to_user}, {commute_id});").on(
          'from_user -> fromUser,
          'to_user -> toUser,
          'commute_id -> commuteId


        ).executeInsert()


    }

    Ok(Json.obj("message" -> "success"))

  }

}