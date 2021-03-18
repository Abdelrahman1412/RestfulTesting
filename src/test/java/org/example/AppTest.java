package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import static io.restassured.RestAssured.given;

public class AppTest {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in/api/").build();
    }


    @org.junit.Test
    public void ListUsers() {

        given().
                spec(requestSpec).
                when().
                get("users").
                then().
                assertThat().statusCode(200);
        System.out.println("The status code received: 200 ");

    }

    @org.junit.Test
    public void SingleUserNotFound() {

        given().
                spec(requestSpec).
                when().
                get("users/23").
                then().
                assertThat().statusCode(404);
        System.out.println("The status code received: 404 ");

    }

    @org.junit.Test
    public void RegistrationSuccessful()
    {
        RestAssured.baseURI ="https://reqres.in/";
        RestAssured.basePath ="/api";
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"email\": \"eve.holt@reqres.in\",\n" +
                                "    \"password\": \"pistol\"\n" +
                                "}")
                        .when()
                        .post("/register")
                        .then()
                        .using().extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals( 200   , statusCode);
        System.out.println("The status code received: " + statusCode);

        String token = response.jsonPath().get("token");
        Assert.assertEquals( "QpwL5tke4Pnpja7X4"   , token);
        System.out.println("The token received: " + token);
    }

    @org.junit.Test
    public void RegistrationUnSuccessful()
    {
        RestAssured.baseURI ="https://reqres.in/";
        RestAssured.basePath ="/api";
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"email\": \"sydney@fife\"\n" +
                                "}")
                        .when()
                        .post("/register")
                        .then()
                        .using().extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals( 400, statusCode);
        System.out.println("The status code received: " + statusCode);

        String errorMsg = response.jsonPath().get("error");
        Assert.assertEquals(  "Missing password", errorMsg);
        System.out.println("Error Code received Correctly" );

    }

}
