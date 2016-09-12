package TestSuite;

import TestFrameWork.BaseTestScript;
import TestFrameWork.HelperClass;
import TestFrameWork.Setup;
import TestFrameWork.TestDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TestScriptAsset extends BaseTestScript {


    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {"local","ref"})
    public void testGetAllAssets(String username, String password){

        HelperClass helper = new HelperClass();
        String accessToken = helper.getTokenWithSignedinProof(username, password);

        Setup.setupPersonURL();
        Response assetResponse =
                        given().
                                log().all().
                                contentType("aplication/json").
                                header("Authorization", "Bearer " + accessToken).
                        when().
                                get("v1/person/CURRENT/asset").
                        then().
                                log().all().
                                extract().response();

        String jsonAsString = assetResponse.asString();
        Reporter.log(jsonAsString);

    }


    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {"local","ref"})
    public void testGetPAYMAssetsUsingPathParam(String username, String password){

        HelperClass helper = new HelperClass();
        String accessToken = helper.getTokenWithSignedinProof(username, password);

        Setup.setupPersonURL();
        Response assetResponse =
                given().
                        log().all().
                        contentType("aplication/json").
                        header("Authorization", "Bearer " + accessToken).
                        when().
                        get("v1/person/CURRENT/asset/PAYM").
                        then().
                        log().all().
                        extract().response();

        String jsonAsString = assetResponse.asString();
        Reporter.log(jsonAsString);

    }
}
