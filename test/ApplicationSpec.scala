package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import models._
import services._
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
    val address1Start = new MongoUserAddress("108 71st street Guttenberg NJ","home")
    val address1End = new MongoUserAddress("130 Madison Ave, New york , NY","work")
    val address2Start = new MongoUserAddress("300 70th Street Guttenberg NJ 07093","sd")
    val address2End = new MongoUserAddress("230 5th ave new york ny","work2")


    "be able to store and retrieve MongoUser" in {
       running(FakeApplication(additionalConfiguration = Map("mongodb.default.user" -> "theadmin",
         "mongodb.default.password" -> "12345" ,
         "mongodb.default.db" -> "unit-test"))){
         val aUser = new MongoUser(new ObjectId,"a Name","Birchborx","linked","image",Nil)
         val dbUser:MongoUser = MongoUser.create(aUser)
         val maybe = MongoUser.findOneById(dbUser.id)
         maybe match {
           case Some(user) => {println("Found a user iwth id" + user.id)}
           case None => {assert(false)}
         }
       }

     }

    "should match for addresses and overlapping times" in {
      running(FakeApplication(additionalConfiguration = Map("mongodb.default.user" -> "theadmin",
        "mongodb.default.password" -> "12345" ,
        "mongodb.default.db" -> "unit-test"))){
        val aUser = new MongoUser(new ObjectId,"a Name","Birchborx","linked","image")
        val aCommute = new MongoUserCommute(new ObjectId,"work",120,134,address1Start.address,address1End.address,aUser.id)
        val bUser = new MongoUser(new ObjectId,"ponnu","irritating","75578","sdss",Nil)
        val bCommute = new MongoUserCommute(new ObjectId,"work",125,160,address2Start.address,address2End.address,bUser.id)

        val list:List[MongoUserCommute] = List[MongoUserCommute](aCommute,bCommute)
        val result:List[MongoUserCommute] = MatchingService.getMatches(aCommute,list)

        result.size must equalTo(1)

        val matchingCommute = result.head
        matchingCommute must equalTo(bCommute)
      }
    }

    "should not match for addresses same user" in {
      running(FakeApplication(additionalConfiguration = Map("mongodb.default.user" -> "theadmin",
        "mongodb.default.password" -> "12345" ,
        "mongodb.default.db" -> "unit-test"))){
        val aUser = new MongoUser(new ObjectId,"a Name","Birchborx","linked","image")
        val aCommute = new MongoUserCommute(new ObjectId,"work",120,134,address1Start.address,address1End.address,aUser.id)
        val bUser = new MongoUser(new ObjectId,"ponnu","irritating","75578","sdss",Nil)
        val bCommute = new MongoUserCommute(new ObjectId,"work",125,160,address2Start.address,address2End.address,aUser.id)

        val list:List[MongoUserCommute] = List[MongoUserCommute](aCommute,bCommute)
        val result:List[MongoUserCommute] = MatchingService.getMatches(aCommute,list)

        result.isEmpty must beTrue
      }
    }

  }
}