package petStore

import com.typesafe.config._
import helpers.GatlingHelper
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PostNewPetWithCustomFeeder extends Simulation {

  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("api.petStore.baseUrl")

  before {
    println(s"Running test with url: ${baseUrl}")
  }

  // 1 Http Conf
  val httpConf = http.baseUrl(baseUrl)
    .header("Accept", "application/json")
    .header("api_key", "special-key")

  val customFeeder = Iterator.continually(Map(
    "name" -> ("Pet-" + GatlingHelper.randomString(5)),
    "categoryName" -> GatlingHelper.randomString(6),
  ))

  def postNewPet() = {
    repeat(5) {
      feed(customFeeder)
        .exec(http("Post New Pets")
          .post("pet")
          .body(ElFileBody("bodies/NewPetTemplate.json")).asJson
          .check(bodyString.saveAs("responseBody")))
        .pause(1)
      .exec { session => println(session("responseBody").as[String]); session}
    }
  }

  // 2 Scenario Definition
  val scn = scenario("Post new Pet")
    .exec(postNewPet())

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}