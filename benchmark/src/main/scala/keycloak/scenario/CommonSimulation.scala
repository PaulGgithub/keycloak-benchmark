package keycloak.scenario

import io.gatling.commons.validation.Validation
import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import org.keycloak.benchmark.gatling.log.LogProcessor
import org.keycloak.benchmark.Config

import scala.Console.println


/**
 * @author tomas Kyjovsky &lt;tkyjovsk@redhat.com&gt;
 */
abstract class CommonSimulation extends Simulation {

  def toStringSimulationProperties : String = {
    " Name: " + getClass.getName
  }

  def rampDownNotStarted(): Validation[Boolean] = {
    System.currentTimeMillis < Config.measurementEndTime
  }

  def defaultHttpProtocol() = {
    var default = http
      .acceptHeader("application/json")
      .disableFollowRedirect

    if (Config.inferHtmlResources) {
      default.inferHtmlResources()
    }

    default
  }

  def setUp(name: String, builder : KeycloakScenarioBuilder): Unit = {
    val scn = scenario(name).exec(builder.chainBuilder)
    setUp(
      scn.inject(
//        rampUsersPerSec(0.001).to(Config.usersPerSec).during(Config.rampUpPeriod),
        rampConcurrentUsers(1).to(Config.usersPerSec).during(Config.rampUpPeriod),
        constantConcurrentUsers(Config.usersPerSec).during(Config.warmUpPeriod + Config.measurementPeriod))
        .protocols(defaultHttpProtocol()))
      .assertions(
        global.failedRequests.count.lt(Config.maxFailedRequests + 1),
        global.responseTime.mean.lt(Config.maxMeanReponseTime))
  }

  before {
    println("==========================================================")
    Console.println("Target servers: " + Config.serverUrisList)
    Console.println("Scenario:\n" + toStringSimulationProperties);
    Console.println("Population:\n" + Config.toStringPopulationConfig);
    Console.println("Runtime:\n" + Config.toStringRuntimeParameters);
    println("Service Level Agreements:\n" + Config.toStringSLA)
    println("Timestamps: \n" + Config.toStringTimestamps)
    Config.validateConfiguration
    println("==========================================================")
  }

  after {
    if (Config.filterResults) {
      new LogProcessor(getClass).filterLog(
        Config.measurementStartTime,
        Config.measurementEndTime,
        false, false)
    }
  }

}
