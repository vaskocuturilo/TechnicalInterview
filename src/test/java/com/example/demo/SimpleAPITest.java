package com.example.demo;

import com.example.demo.model.HealthCheck;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class SimpleAPITest {
    String url = "http://localhost:8080/actuator/health";
    @Test
    public void makeSureThatHealthCheckEndpointUp() {
        Response response = given().expect().when().
                get(url).then().statusCode(200)
                .extract().response();

        HealthCheck healthCheck = response.getBody().as(HealthCheck.class);

        assertThat(healthCheck.getStatus()).isEqualTo("UP");
    }
}
