package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.libs.json._
import services.MatchingService
import mappers.CommuteMapper
import play.api.Play
import play.api.db.DB
import anorm._
import play.api.Play.current
import com.mongodb.casbah.Imports._

import scala.Some


object Application extends Controller {
  
  def index = Action {
    val apiKey = Play.current.configuration.getString("linkedin.api.key").getOrElse("91zpl9j92hr9")
    Ok(views.html.main("LandingPage",apiKey))
    //Ok(views.html.error())
  }


  def javascriptRoutes = Action{
    implicit request =>
      Ok(
        Routes.javascriptRouter("jsRoutes")()
      ).as("text/javascript")
  }


    def createOrGetUser = Action(parse.json) {
    request =>
    val json = request.body
    System.out.println("In create or retrieve user.Json=" + json)
    json.validate[MongoUser].fold(
      valid = (user => {

            val aUser:MongoUser = MongoUser.findOneByLinkedinId(user.linkedInId).getOrElse(

             MongoUser.create(user)

//             maybeUser match {
//               case None => BadRequest(" Semothing went wrong")
//               case Some(dbUser) => dbUser
//             }
            )

        val userJson = MongoUser.object2Json(aUser)
        System.out.println("Json User" + userJson)
        Ok(userJson)


      }),
      invalid = (e => {

            BadRequest("Detected error:"+ JsError.toFlatJson(e))
      })
    )
    }

  def getUserAddresses(id:String) = Action {

    val maybeUser:Option[MongoUser] = MongoUser.findOneById(new ObjectId(id))
    maybeUser match {
      case Some(user) => {
             val userAddresses = user.addresses
             Ok(Json.toJson(userAddresses))
      }
      case None => BadRequest("Couldnt find user")
    }
  }




    def postAddress(id:String) = Action(parse.json) {req =>
      val json = req.body
      System.out.println("Json is " + json)
      val label = json.\("label").as[String]
      val addressStr = json.\("address").as[String]

      val maybeUserAddr:Option[MongoUserAddress] = MongoUser.addUserAddress(id,label,addressStr)
      maybeUserAddr match {
        case Some(address) => {

          Ok(Json.toJson(address))
        }
        case None => {
          BadRequest("Detected error..didnt find user or address add went wrong")
        }
        }
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