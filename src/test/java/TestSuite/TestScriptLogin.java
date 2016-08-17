package TestSuite;


import TestFrameWork.Setup;
import TestFrameWork.TestDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestScriptLogin extends Setup {

    @BeforeClass
    public void setupTest(){
        RestAssured.useRelaxedHTTPSValidation();
        Setup.setupAuthServerURL();
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class)
    public void SuccessfulLogin(String username, String password){
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


        String jsonAsString = response.asString();
        Reporter.log(jsonAsString,true);


    }

}
