

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SampleTests {

    public static void main(String[] args) throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        Map<String, Object> jsonAsMap = new HashMap<>();
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

        File file = new File("sampleresult.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        output.write("hi hello");
        output.close();


    }

}
