package com.example.demo.api;

import com.example.demo.model.health.Health;
import com.example.demo.model.info.Info;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        Response response = request
                .auth()
                .form("admin@qa.team", "123456")
                .get("actuator/health").then().statusCode(200).extract().response();

        Health healthCheck = response.getBody().as(Health.class);
        assertThat(healthCheck.getStatus()).isEqualTo("UP");
    }

    @Test
    public void testMakeSureThatInfoCheckEndpointUp() {
        Response response = request
                .auth()
                .form("admin@qa.team", "123456")
                .get("actuator/info").then().statusCode(200).extract().response();

        Info informationRestService = response.getBody().as(Info.class);

        assertThat(informationRestService.getApp().getWebsite()).isEqualTo("http://localhost:8080/api/v1/");
        assertThat(informationRestService.getApp().getName()).isEqualTo("Spring boot application");
        assertThat(informationRestService.getApp().getDescription()).isEqualTo("Spring boot application");
        assertThat(informationRestService.getApp().getVersion()).isEqualTo("1.0.0");

        assertThat(informationRestService.getJava().getVersion()).isEqualTo("17.0.5");
        assertThat(informationRestService.getJava().getVendor().getName()).isEqualTo("Private Build");
    }

    @Test
    public void testUploadCorrectFileSize() {
        File file = new File("src/main/resources/upload.json");
        Response response = request.auth()
                .form("admin@qa.team", "123456")
                .multiPart("file", file, "application/json")
                .when().request().post(ENDPOINT);
        assertThat(response.getStatusCode()).isEqualTo(302);
    }
}
