package com.colak.springtutorial.controller;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.core.FeederBuilder;

public class ParameterizedSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    FeederBuilder.FileBased<String> csvFeeder = CoreDsl.csv("userData.csv").circular();

    ScenarioBuilder scn = CoreDsl.scenario("Parameterized Simulation")
            .feed(csvFeeder)
            .exec(HttpDsl.http("request_login")
                    .post("/api/login")
                    .formParam("username", "#{username}")
                    .formParam("password", "#{password}")
                    .check(HttpDsl.status().is(200)));

    {
        setUp(
                scn.injectOpen(CoreDsl.atOnceUsers(1000))
        ).protocols(httpProtocol);
    }
}
