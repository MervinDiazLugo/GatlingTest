package petStore

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.typesafe.config._

class GetPetStoreWithPropertiesTest extends Simulation {

  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("api.petStore.baseUrl")

  // 1 Http Conf
  val httpConf = http.baseUrl(baseUrl)
    .header("Accept", "application/json")
    .header("api_key", "special-key")

  // 2 Scenario Definition
  val scn = scenario("Get Pets with status Available Test")
    .exec(http("Get Pets with status Available")
      .get("/pet/findByStatus?status=available"))

  // 3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}