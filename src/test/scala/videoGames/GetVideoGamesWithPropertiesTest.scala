package videoGames

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetVideoGamesWithPropertiesTest extends Simulation {
  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("api.videoGames.baseUrl")

  // 1 Http Conf
  val httpConf = http.baseUrl(baseUrl)
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