import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class RampUpSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scn = CoreDsl.scenario("Ramp-Up Simulation")
            .exec(HttpDsl.http("request_hello")
                    .get("/api/hello")
                    .check(HttpDsl.status().is(200)));

    {
        setUp(
                scn.injectOpen(CoreDsl.rampUsers(1000).during(600)) // Ramp up to 1000 users over 10 minutes
        ).protocols(httpProtocol);
    }
}
