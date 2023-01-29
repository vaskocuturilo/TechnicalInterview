package com.example.demo;

import com.example.demo.model.health.HealthCheck;
import com.example.demo.model.info.InformationRestService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SimpleAPITest {
    private static RequestSpecification request;

    @BeforeAll
    public static void setupResponseSpecification() {
        RestAssured.baseURI = "http://127.0.0.1:8080/";
        request = RestAssured.given();
    }

    @Test
    public void makeSureThatHealthCheckEndpointUp() {
        Response response = request.get("actuator/health").then().statusCode(200).extract().response();

        HealthCheck healthCheck = response.getBody().as(HealthCheck.class);

        assertThat(healthCheck.getStatus()).isEqualTo("UP");
    }

    @Test
    public void makeSureThatInfoCheckEndpointUp() {
        Response response = request.get("actuator/info").then().statusCode(200).extract().response();

        InformationRestService informationRestService = response.getBody().as(InformationRestService.class);

        assertThat(informationRestService.getApp().getWebsite()).isEqualTo("http://localhost:8080/api/v1/");
        assertThat(informationRestService.getJava().getVersion()).isEqualTo("17.0.5");
        assertThat(informationRestService.getJava().getVendor().getName()).isEqualTo("Private Build");

    }
}
