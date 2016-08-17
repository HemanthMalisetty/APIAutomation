

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SampleTests {

    public static void main(String[] args) {
        RestAssured.useRelaxedHTTPSValidation();
        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("preferred_username", "arvind3222@mailinator.com");
        jsonAsMap.put("password", "Password123");

        given().
                baseUri("https://api.ref.o2.co.uk/auth/").
                contentType("application/json").
                auth().basic("smoke","smokeSecret").
                body(jsonAsMap).
        when().
                post("/v1/auth/password_o2").
        then().
                statusCode(200).
                log().all();


    }

}
