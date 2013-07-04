package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json

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
    
//    "render the index page" in {
//      running(FakeApplication()) {
//        val home = route(FakeRequest(GET, "/")).get
//
//        status(home) must equalTo(OK)
//        contentType(home) must beSome.which(_ == "text/html")
//        contentAsString(home) must contain ("Your new application is ready.")
//      }
//    }

    "respond to the create User Action" in {
      running(FakeApplication()){
        val mockJsValue = Json.obj(
          "id" -> "",
          //"name" -> "Testing my mistakes",
          "email" -> "abc@example.com",
          "cell" -> "999-000-9494",
          "imageUrl" -> "someping.jpg",
          "linkedInId" -> "www.linkedIn.com",
          "gender" -> "male",
          "employer" -> "JPHM",
          "city" ->  "2004-12-"
        )
        val result = controllers.Application.postMessage()(FakeRequest().withJsonBody(mockJsValue)).map(
        res => {
          status(res) must equalTo(OK)
          //contentType(res) must beSome("text/plain")
         }
        )



        //charset(result) must beSome("utf-8")
      }


    }
  }
}