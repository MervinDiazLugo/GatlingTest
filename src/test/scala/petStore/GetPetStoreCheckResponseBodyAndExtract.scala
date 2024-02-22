package petStore

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetPetStoreCheckResponseBodyAndExtract extends Simulation {

  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("api.petStore.baseUrl")

  // 1 Http Conf
  val httpConf = http.baseUrl(baseUrl)
    .header("Accept", "application/json")

  // 2 Scenario Definition
  val scn = scenario("Check JSON Path")
    .exec(http("Get Pets with id 8228")
      .get("/pet/8228")
      .check(jsonPath("$.name").is("Feia")))

    .exec(http("Get all Pets with status Available")
      .get("/pet/findByStatus?status=available")
      .check(jsonPath("$[1].id").saveAs("firstPetId")))
    .exec { session => println(session); session}

    .exec(http("Get Pets with id 8228")
      .get("/pet/${firstPetId}")
      .check(jsonPath("$.name").is("doggie"))
      .check(bodyString.saveAs("responseBody")))
    .exec { session => println(session("responseBody").as[String]); session}


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}