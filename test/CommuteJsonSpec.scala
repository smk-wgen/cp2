import models._
import org.specs2.mutable.Specification
import play.api.libs.json.{JsResult, Json}
import play.api.test.FakeApplication
import play.api.test.Helpers._
import com.mongodb.casbah.Imports._

/**
 * Created by skunnumkal on 9/16/13.
 */


class CommuteJsonSpec extends Specification{
  "MongoCommute " should {

    "convert json to MongoCommute" in {
      running(FakeApplication()) {
        val startT = 340
        val endT:Int = 450
        val validJson = Json.obj("label" -> "test", "startTime" -> "340", "endTime" -> "450",
          "startAddress" -> "108 71st street Guttenberg" , "endAddress" -> "Staten Island NY","userId" -> "52365c930364bb6c3c447382")
        val maybeCommute:JsResult[MongoUserCommute]  = MongoUserCommute.mucReads.reads(validJson)
        val aCommute = maybeCommute.get
        aCommute.startTime must equalTo(startT)
        aCommute.endTime must equalTo(endT)

      }
    }

    "convert commute to json" in {
      running(FakeApplication()) {
        val starTime:Int = 485
        val aCommute = new MongoUserCommute(new ObjectId,"aLabel",starTime,599,"an Address","another Address", new ObjectId)
        aCommute.startTime must equalTo(starTime)
        println("So far so good")
        val json = MongoUserCommute.commute2json(aCommute)
        json.\("startTime").as[Int] must equalTo(starTime)

      }
    }
  }


}

