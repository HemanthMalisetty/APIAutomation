package TestSuite;

import TestFrameWork.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class VisualIVRTestSuite extends BaseTestScript {

    TinyUrlServer tinyUrlServer;
    MessagingHelperClass msgsim;
    HelperClass helper;
    RestOperations restoperation;
    Boolean enableTinyUrl = false;

    @BeforeMethod(groups = {"test","local"})
    public void setupTinyurlstub() {
        if(enableTinyUrl){
            tinyUrlServer = new TinyUrlServer();
            tinyUrlServer.start();
        }
        msgsim = new MessagingHelperClass();
        msgsim.clearHistory();
    }

    @AfterMethod(groups = {"test","local"})
    public void tearDown() {
        if(enableTinyUrl){
            tinyUrlServer.stop();
        }
    }

    @Test(dataProvider = "PAYMmsisdns", dataProviderClass = TestDataProvider.class,groups = {"ref"})
    public void HappyPathEndtoEndScenario(String env,String phonenumber){
        helper = new HelperClass();
        restoperation = new RestOperations();

        if((env == "local" || env == "test") && enableTinyUrl){
            tinyUrlServer.createValidTinyUrl("https://localhost.o2.co.uk:8443/credhandler/tinyurl/redeem/id1");
        }

        Setup.setupAuthServerURL(env);

        Map<String, Object> datokenRequestBody = new HashMap<>();
        datokenRequestBody.put("msisdn", phonenumber);
        datokenRequestBody.put("channel", "IVR");
        datokenRequestBody.put("description", "Security verification done");

        Response datokenresponse =
                given().
                        log().all().
                        contentType("application/json").
                        body(datokenRequestBody).
                when().
                        put("/v1/customer/verified/token").
                then().
                        log().all().
                        statusCode(200).
                        body(("any { it.key == 'da_token' }"), is(true)).
                extract().
                        response();

        Reporter.log(datokenresponse.asString());

        Map<String, Object> datokenResponsebody = datokenresponse.getBody().as(Map.class);
        String datToken = (String) datokenResponsebody.get("da_token");

        Map<String, Object> transferSessionRequestBody = new HashMap<>();
        transferSessionRequestBody.put("token", datToken);
        transferSessionRequestBody.put("target_url_id", "my_o2_bill_payment");

        Response transfersessionresponse =
                given().
                        log().all().
                        contentType("application/json").
                        body(transferSessionRequestBody).
                when().
                        put("/v1/transfer/session/issue").
                then().
                        log().all().
                        statusCode(200).
                        body(("any { it.key == 'transfer_session_id' }"), is(true)).
                extract().
                        response();

        Reporter.log(transfersessionresponse.asString());

        Map<String, Object> transfersessionResponsebody = transfersessionresponse.getBody().as(Map.class);
        String session_id = (String) transfersessionResponsebody.get("transfer_session_id");

        String redeemUrl=null;
        Response redeemurlresponse;

        if(env == "local" || env == "test"){
            String smscontent = msgsim.getSMS(phonenumber);
            redeemUrl = helper.getURLfromsms(smscontent);
        }else if(env == "ref"){
            redeemUrl = "https://identity.ref.o2.co.uk/redeem/ts/" +session_id;
        }

        if((env == "local" || env == "test") && enableTinyUrl){
            String resolvedTargetUrl = "https://localhost.o2.co.uk:8443/credhandler/redeem/ts/" +session_id;
            tinyUrlServer.getValidTargetUrl("id1", resolvedTargetUrl);
        }

        if(enableTinyUrl){
            redeemurlresponse = restoperation.getMethodWithRedirect(redeemUrl);
            redeemUrl = redeemurlresponse.getHeader("Location");
        }

        redeemurlresponse = restoperation.getMethodWithRedirect(redeemUrl);
        String targetUrl = redeemurlresponse.getHeader("Location");
        System.out.println(targetUrl);
        System.out.println(redeemurlresponse.getCookie("AUTH"));
        Assert.assertTrue(helper.checkAuthCodeForProof(env,"user_id_signed_in",redeemurlresponse.getCookie("AUTH")),"Auth Cookie doesn't have sign in proof");

    }


    @Test(dataProvider = "PAYMmsisdns", dataProviderClass = TestDataProvider.class,groups = {})
    public void CreateTokenforVerifiedCustomer(String env,String phonenumber){
        Setup.setupAuthServerURL(env);

        Map<String, Object> datokenRequestBody = new HashMap<>();
        datokenRequestBody.put("msisdn", phonenumber);
        datokenRequestBody.put("channel", "IVR");
        datokenRequestBody.put("description", "Security verification done");

        Response response =
                given().
                        log().all().
                        contentType("application/json").
                        body(datokenRequestBody).

                when().
                        put("/v1/customer/verified/token").
                then().
                        log().all().
                        statusCode(200).
                        body(("any { it.key == 'da_token' }"), is(true)).
                extract().
                        response();


        String jsonAsString = response.asString();
        Reporter.log(jsonAsString);

    }
}
