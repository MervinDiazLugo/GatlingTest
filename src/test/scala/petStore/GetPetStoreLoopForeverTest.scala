package petStore

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class GetPetStoreLoopForeverTest extends Simulation {

  // 1 Http Conf
  val httpConf = http.baseUrl("https://petstore.swagger.io/v2/")
    .header("Accept", "application/json")
    .header("api_key", "special-key")


  //request definitions
  def getAllAvailablePets() = {
    exec(
      http("Get Pets with status Available")
        .get("/pet/findByStatus?status=available")
        .check(status.is(200))
    )
  }

  def getSpecificPet() = {
    exec(
      http("Get Pet with Id 1")
        .get("/pet/1")
        .check(status.is(200))
    )
  }

  // 2 Scenario Definition
  // ** SCENARIO THAT LOOPS FOREVER **
  val scn = scenario("Fixed Duration Load Simulation")
    .forever() {
      exec(getAllAvailablePets())
        .pause(5)
        .exec(getSpecificPet())
        .pause(5)
        .exec(getAllAvailablePets())
    }

  // 3 Load Scenario
  // ** Fixed duration Load Simulation **
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
      rampUsers(50) during (30 second)
    ).protocols(httpConf)
  ).maxDuration(1 minute)
    .assertions(
      global.responseTime.max.lt(100),
      global.successfulRequests.percent.gt(95)
    )

}