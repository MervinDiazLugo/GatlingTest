package videoGames

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetVideoGamesTest extends Simulation {

  // 1 Http Conf
  val httpConf = http.baseUrl("http://localhost:5000/app/")
    .header("Accept", "application/json")

  // 2 Scenario Definition
  val scn = scenario("Get Video Game Test")
    .exec(http("Get All Games")
      .get("videogames"))

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}