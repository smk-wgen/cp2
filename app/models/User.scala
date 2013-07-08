package models

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.Long

/**
 * Created by skunnumkal on 7/1/13.
 */
case class User(id: Pk[Long],email: String, name: String, linkedInId: String,cell:String,imageUrl:String,employer:String,
                 city:String,gender:String){
    def this(email: String, name: String, linkedInId: String,cell:String,imageUrl:String,employer:String,
             city:String,gender:String) = this(NotAssigned,email,name,linkedInId,cell,imageUrl,employer,city,gender)
}
object User{

  implicit val userReads = (
    (__ \ "id").read(PkReader) and
      (__ \ "email").read[String] and
      (__ \ "name").read[String] and
      (__ \ "linkedInId").read[String] and
      (__ \ "cell").read[String] and
      (__ \ "imageUrl").read[String] and
      (__ \ "employer").read[String] and
      (__ \ "city").read[String] and
      (__ \ "gender").read[String]
    )(User.apply _)

  implicit object PkReader extends Reads[Pk[Long]]{
    def reads(js:JsValue):JsResult[Pk[Long]] = js match{
      case JsNumber(js) => JsSuccess(Id(JsNumber(js).as[Long]))
      case _ => JsSuccess(NotAssigned)
    }
  }

  implicit val userWrites = (
    (__ \ "id").write(PkWriter) and
      (__ \ "email").write[String] and
      (__  \ "name").write[String] and
      (__ \ "linkedInId").write[String] and
      (__ \ "cell").write[String] and
      (__ \ "imageUrl").write[String] and
      (__ \ "employer").write[String] and
      (__ \ "city").write[String] and
      (__ \ "gender").write[String]
    )(unlift(User.unapply))


  implicit object PkWriter extends Writes[Pk[Long]]{
    def writes(id:Pk[Long]):JsValue = {
      val l:Long = id.getOrElse(0)
      JsNumber(l)
    }
  }
  /**
   * Parse a User from a ResultSet
   */
  val userDBRecordParser = {
    get[Pk[Long]]("user.id") ~
      get[String]("user.name") ~
      get[String]("user.email") ~
      get[String]("user.city") ~
      get[String]("user.gender") ~
      get[String]("user.cell") ~
      get[String]("user.employer") ~
      get[String]("user.imageUrl") ~
      get[String]("user.linkedInId") map {
      case id~name~email~city~gender~cell~employer~imageUrl~linkedInId =>
        User(id, email, name, linkedInId,cell, imageUrl, employer,city,gender)
    }
  }

  def create(user:User):User = {
    DB.withConnection {
      implicit connection =>
        val maybeId:Option[Long] = SQL("insert into user(name, email,linkedInId, imageUrl, employer,city,gender,cell) " +
          "values ({name}, {email}, {linkedInId}, {imageUrl}, {employer}, {city}, {gender},{cell});").on(
          'name -> user.name,
          'email -> user.email,
          'linkedInId -> user.linkedInId,
          'imageUrl -> user.imageUrl,
          'employer -> user.employer,
          'city -> user.city,
          'gender -> user.gender,
            'cell -> user.cell
        ).executeInsert()
        maybeId match{
          case Some(num) => System.out.println("Long val " + num)
          case None => System.out.println("Error ")
        }
        val dbUser:User = user.copy(id = Id(maybeId.get))

        dbUser
    }
  }

  def findById(id:Long):Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where id = {id}").on(
        'id -> id
      ).as(User.userDBRecordParser.singleOpt)
    }
  }


}
