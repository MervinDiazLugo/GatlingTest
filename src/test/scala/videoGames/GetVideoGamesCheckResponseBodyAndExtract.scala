package videoGames

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetVideoGamesCheckResponseBodyAndExtract extends Simulation {

  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("api.videoGames.baseUrl")

  val httpConf = http.baseUrl(baseUrl)
    .header("Accept", "application/json")

  val scn = scenario("Check JSON Path")

    .exec(http("Get specific game")
      .get("videogames/1")
      .check(jsonPath("$.name").is("Resident Evil 4")))

    .exec(http("Get all video games")
      .get("videogames")
      .check(jsonPath("$[1].id").saveAs("gameId")))
    .exec { session => println(session); session}

    .exec(http("Get specific game")
      .get("videogames/${gameId}")
      .check(jsonPath("$.name").is("Resident Evil 4"))
      .check(bodyString.saveAs("responseBody")))
    .exec { session => println(session("responseBody").as[String]); session}


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}