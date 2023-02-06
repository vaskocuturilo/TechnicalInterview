package com.example.demo;

import com.example.demo.model.health.HealthCheck;
import com.example.demo.model.info.InformationRestService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class SimpleAPITest {
    private static RequestSpecification request;
    private static final String ENDPOINT = "api/v1/questions/upload";


    @BeforeAll
    public static void setupResponseSpecification() {
        RestAssured.baseURI = "http://localhost:8080/";
        request = given();
    }

    @Test
    public void testMakeSureThatHealthCheckEndpointUp() {
        Response response = request.get("actuator/health").then().statusCode(200).extract().response();

        HealthCheck healthCheck = response.getBody().as(HealthCheck.class);

        assertThat(healthCheck.getStatus()).isEqualTo("UP");
    }

    @Test
    public void testMakeSureThatInfoCheckEndpointUp() {
        Response response = request.get("actuator/info").then().statusCode(200).extract().response();

        InformationRestService informationRestService = response.getBody().as(InformationRestService.class);

        assertThat(informationRestService.getApp().getWebsite()).isEqualTo("http://localhost:8080/api/v1/");
        assertThat(informationRestService.getJava().getVersion()).isEqualTo("17.0.5");
        assertThat(informationRestService.getJava().getVendor().getName()).isEqualTo("Private Build");
    }

    @Test
    public void testUploadCorrectFileSize() {
        File file = new File("src/main/resources/upload.json");
        Response response = request
                .multiPart("file", file, "application/json")
                .when().request().post(ENDPOINT);

        assertThat(response.getStatusCode()).isEqualTo(302);
    }
}
