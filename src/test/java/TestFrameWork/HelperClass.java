package TestFrameWork;


import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.JsonMappingException.from;
import static io.restassured.RestAssured.given;

public class HelperClass {

    public String getTokenWithSignedinProof(String username,String password){
        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("preferred_username", username);
        jsonAsMap.put("password", password);

        Response response =
                given().
                        contentType("application/json").
                        body(jsonAsMap).

                when().
                        post("/v1/auth/password_o2").
                then().
                        extract().response();



    }
}
