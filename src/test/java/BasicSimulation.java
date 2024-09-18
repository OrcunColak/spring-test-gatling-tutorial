import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080") // Base URL of the Spring Boot application
            .acceptHeader("application/json");

    ScenarioBuilder scn = CoreDsl.scenario("Basic Simulation")
            .exec(HttpDsl.http("request_hello")
                    .get("/api/hello")
                    .check(HttpDsl.status().is(200)));

    {
        setUp(
                scn.injectOpen(CoreDsl.atOnceUsers(1000)) // Simulate 1000 users at once
        ).protocols(httpProtocol);
    }
}
