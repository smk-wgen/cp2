import anorm.{NotAssigned, Id}
import models._
import play.api.{Application, GlobalSettings}

/**
 * Created by skunnumkal on 7/15/13.
 */

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}
object InitialData {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def insert() = {

    if(User.findAll.isEmpty) {

      Seq(
        User(Id(1),"guillaume@sample.com", "Guillaume Bort", "linkedin1","090-494-1091","imgsrc","TypeSafe","New York","male"),
        User(Id(2),"sadek@sample.com", "Sadek MAdache ", "linkedin2","590-494-1091","imgsrc","TypeSafe","New Jersey","male"),
        User(Id(3),"phausel@sample.com", "Patty Hausel", "linkedin3","090-494-2091","imgsrc","Google","New York","female"),
        User(Id(4),"geena@sample.com", "Geena Bort", "linkedin4","090-494-1091","imgsrc","GrailSafe","New York","female")
      ).foreach(User.create)

      Seq(
        UserAddress(Id(1),"home","108 71s street","","Guttenberg","NJ",17093,1),
        UserAddress(Id(2),"work","55 Washington Street ","Brooklyn","New York","Ny",11201,1),
        UserAddress(Id(3),"home","108 71s street","","Guttenberg","NJ",97093,2),
        UserAddress(Id(4),"work","585 Swedesford Road East","","Wayne","PA",19087,2),
        UserAddress(Id(5),"home","53 Hamilton Ave","","Staten Island New York","NY",10301,3),
        UserAddress(Id(6),"work","55 Washington Street ","Brooklyn","New York","Ny",11201,3),
        UserAddress(Id(7),"home","55 Washington Street","","Brooklyn New York","NY",11201,4),
        UserAddress(Id(8),"work","110 71st Street ","","Guttenberg","NJ",97093,4)

      ).foreach(UserAddress.create)



    }

  }

}
