package TestFrameWork;


import io.restassured.response.Response;
import org.testng.Reporter;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestOperations {

    public Response postMethod(Map<String, Object> jsonBody,String path){

        Reporter.log("REQUEST: ");
        Reporter.log(String.valueOf(jsonBody));

        Response response =
                given().
                        contentType("application/json").
                        body(jsonBody).
                        log().all().

                when().
                        post(path).
                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;
    }

    public Response deleteMethod(String path,String accessToken){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        header("Authorization", "Bearer " + accessToken).
                        log().all().

                when().
                        delete(path).

                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;
        
    }

    public Response deleteMethod(String path){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        log().all().

                when().
                        delete(path).

                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }

    public Response getMethod(String path){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        log().all().

                when().
                        get(path).

                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }

    public Response getMethod(String path, String accessToken){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        header("Authorization", "Bearer " + accessToken).
                        log().all().

                when().
                        get(path).

                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }

    public Response getMethodWithRedirect(String path){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        log().all().
                        redirects().follow(false).
                when().
                        get(path).
                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }

    public Response putMethod(Map<String, Object> jsonBody,String path, String accessToken){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        header("Authorization", "Bearer " + accessToken).
                        body(jsonBody).
                        log().all().
                when().
                        put(path).
                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }

    public Response putMethod(Map<String, Object> jsonBody,String path){

        Reporter.log("REQUEST: ");

        Response response =
                given().
                        contentType("application/json").
                        body(jsonBody).
                        log().all().
                when().
                        put(path).
                then().
                        log().all().
                extract().
                        response();

        Reporter.log("RESPONSE: ");
        Reporter.log(response.asString());
        return response;

    }
}
