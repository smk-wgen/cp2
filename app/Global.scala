import anorm.Id
import anorm.{NotAssigned, Id}
import models._
import play.api.{Application, GlobalSettings}

/**
 * Created by skunnumkal on 7/15/13.
 */

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    //InitialData.insert()
  }

}
object InitialData {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

//  def insert() = {
//
//    if(User.findAll.isEmpty) {
//
//      Seq(
//        User(Id(1),"guillaume@sample.com", "Guillaume Bort", "linkedin1","090-494-1091","imgsrc","TypeSafe","New York","male"),
//        User(Id(2),"sadek@sample.com", "Sadek MAdache ", "linkedin2","590-494-1091","imgsrc","TypeSafe","New Jersey","male"),
//        User(Id(3),"phausel@sample.com", "Patty Hausel", "linkedin3","090-494-2091","imgsrc","Google","New York","female"),
//        User(Id(4),"geena@sample.com", "Geena Bort", "linkedin4","090-494-1091","imgsrc","GrailSafe","New York","female"),
//        User(Id(5),"abc@example.com","Sajit Kunnumkal","gu09aWAXSz","123-555-5555","http://m.c.lnkd.licdn.com/mpr/mprx/0_iWKW8pDZCYQBXJtqSalV8xacGgKnQ01qTmk98YYXjRkQMUXN7785aOoo2KrWL4-4GEtB7JhpubdK", "Senior Developer Amplify","Philadelphia","male")
//      ).foreach(User.create)
//
//
//
//    }
//
//  }

}
