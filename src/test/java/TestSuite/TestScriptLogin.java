package TestSuite;


import TestFrameWork.BaseTestScript;
import TestFrameWork.HelperClass;
import TestFrameWork.Setup;
import TestFrameWork.TestDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestScriptLogin extends BaseTestScript {


    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void SuccessfulLogin(String env, String username, String password){
        Setup.setupAuthServerURL(env);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("preferred_username", username);
        jsonAsMap.put("password", password);

        Response response =
                given().
                        log().all().
                        contentType("application/json").
                        body(jsonAsMap).

                when().
                        post("/v1/auth/password_o2").
                then().
                        log().all().
                extract().
                        response();


        String jsonAsString = response.asString();
        Reporter.log(jsonAsString);

    }


}
