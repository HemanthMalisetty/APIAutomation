import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class SampleTestWithTestNG {

    @Test
    public void testLogin(){
        RestAssured.useRelaxedHTTPSValidation();

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("preferred_username", "arvind3222@mailinator.com");
        jsonAsMap.put("password", "Password123");

        Response response =
                given().
                        baseUri("https://api.ref.o2.co.uk/auth/").
                        contentType("application/json").
                        auth().basic("smoke", "smokeSecret").
                        body(jsonAsMap).
                when().
                        post("/v1/auth/password_o2").
                then().
                        extract().response();


        String jsonAsString = response.asString();
        System.out.println("hi");
        System.out.println(jsonAsString);
        Reporter.log(jsonAsString);

    }
}
