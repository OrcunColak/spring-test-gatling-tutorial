import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class PeakTrafficSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scn = CoreDsl.scenario("Peak Traffic Simulation")
            .exec(HttpDsl.http("request_hello")
                    .get("/api/hello")
                    .check(HttpDsl.status().is(200)));

    {
        setUp(
                scn.injectOpen(
                        CoreDsl.constantUsersPerSec(100).during(300), // Constant load of 100 users per second
                        CoreDsl.rampUsersPerSec(10).to(100).during(600) // Gradually increase from 10 to 100 users per second
                )
        ).protocols(httpProtocol);
    }
}
