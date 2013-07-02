package models

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * Created by skunnumkal on 7/1/13.
 */
case class User(id: Pk[Long],email: String, name: String, linkedInId: String,cell:String,imageUrl:String,employer:String,
                 city:String,gender:String){
    def this(email: String, name: String, linkedInId: String,cell:String,imageUrl:String,employer:String,
             city:String,gender:String) = this(Id(0),email,name,linkedInId,cell,imageUrl,employer,city,gender)
}
object User{
  /**
   * Create a User.
   */
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
    def writes(id:Pk[Long]):JsValue = new JsNumber(id.get)
  }
  def create(user: User): User = {
    DB.withConnection { implicit connection =>

    // Get the user id
      val id: Long = user.id.getOrElse {
        SQL("select next value for task_seq").as(scalar[Long].single)
      }

      SQL(
        """
          insert into user values (
            {id}, {email}, {name}, {linkedInId}, {cell}, {imageUrl}, {employer},{city},{gender}
          )
        """
      ).on(
        'id -> id,
        'email -> user.email,
        'name -> user.name,
        'linkedInId -> user.linkedInId,
        'cell -> user.cell,
        'imageUrl -> user.imageUrl,
        'employer -> user.employer,
        'city -> user.city,
        'gender -> user.gender
      ).executeUpdate()

      user.copy(id = Id(id))

    }
  }
}
