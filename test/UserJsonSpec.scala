import models.{MongoUserCommute, MongoUserAddress, MongoUser}
import org.specs2.mutable.Specification
import play.api.test.{FakeRequest, FakeApplication}
import play.api.test.Helpers._
import play.api.libs.json._
import com.mongodb.casbah.Imports._

/**
 * Created by skunnumkal on 9/15/13.
 */
class UserJsonSpec extends Specification{
  "MongoUser " should {

    "convert json to MongoUser" in {
      running(FakeApplication()) {
        val validJson = Json.obj("username" -> "test", "title" -> "Senior Dev", "linkedInId" -> "ALinkedInId",
        "imageUrl" -> "imageSrc")
        val maybeUser:JsResult[MongoUser]  = MongoUser.userReads.reads(validJson)
        val aUser = maybeUser.get
        aUser.username must equalTo("test")

      }
    }

    "convert user to json" in {
      running(FakeApplication()) {

        val addresses:List[MongoUserAddress] = List(new MongoUserAddress("myhome", " 108 71st street guttenberg nj 07093"))
        val commutes:List[MongoUserCommute] = List(new MongoUserCommute(new ObjectId,"label1",56,76,"a","b",new ObjectId))
        val user:MongoUser = MongoUser(new ObjectId,"adam"," In the know","adx9ff","./views/images/favicon.png",addresses,commutes)
        val json = MongoUser.object2Json(user)
        //val json = Json.fromJson(jsonStr)
        println(json)
        json.\("username").as[String] must equalTo(user.username)

      }
    }
  }


}
