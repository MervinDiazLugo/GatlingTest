package videoGames

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GetVideoGamesLoopForeverTest extends Simulation {

  // 1 Http Conf
  val httpConf = http.baseUrl("http://localhost:5000/app/")
    .header("Accept", "application/json")


  //request definitions
  def getAllVideoGames() = {
    exec(
      http("Get all video games")
        .get("videogames")
        .check(status.is(200))
    )
  }

  def getSpecificGame() = {
    exec(
      http("Get Specific Game")
        .get("videogames/2")
        .check(status.is(200))
    )
  }

  // 2 Scenario Definition
  // ** SCENARIO THAT LOOPS FOREVER **
  val scn = scenario("Fixed Duration Load Simulation")
    .forever() {
      exec(getAllVideoGames())
        .pause(5)
        .exec(getSpecificGame())
        .pause(5)
        .exec(getAllVideoGames())
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