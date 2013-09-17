package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import models._
import com.mongodb.casbah.Imports._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    


//     "be able to store and retrieve MongoUser" in {
//       running(FakeApplication(additionalConfiguration = Map("mongodb.default.user" -> "theadmin",
//         "mongodb.default.password" -> "12345" ,
//         "mongodb.default.db" -> "unit-test"))){
//         val aUser = new MongoUser(new ObjectId,"a Name","Birchborx","linked","image",Nil)
//         val dbUser:MongoUser = MongoUser.create(aUser)
//         val maybe = MongoUser.findOneById(dbUser.id)
//         maybe match {
//           case Some(user) => {println("Found a user iwth id" + user.id)}
//           case None => {assert(false)}
//         }
//       }
//
//     }

  }
}