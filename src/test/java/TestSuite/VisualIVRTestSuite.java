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

    @BeforeMethod(alwaysRun = true)
    public void setupTinyurlstub() {
        tinyUrlServer = new TinyUrlServer();
        tinyUrlServer.start();
        msgsim = new MessagingHelperClass();
        msgsim.clearHistory();
        helper = new HelperClass();
        restoperation = new RestOperations();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        tinyUrlServer.stop();
    }

    @Test(dataProvider = "PAYMmsisdns", dataProviderClass = TestDataProvider.class,groups = {})
    public void HappyPathEndtoEndScenario(String env,String phonenumber){

        if(env == "local" || env == "test"){
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

        String tinyRedeemUrl=null;
        if(env == "local" || env == "test"){
            String smscontent = msgsim.getSMS(phonenumber);
            tinyRedeemUrl = helper.getURLfromsms(smscontent);
        }else if(env == "ref"){
            Scanner scanner = new Scanner (System.in);
            System.out.println("Enter the redeem url you got in the sms : ");
            tinyRedeemUrl = scanner.next();
        }


        String resolvedTargetUrl = "https://localhost.o2.co.uk:8443/credhandler/redeem/ts/" +session_id;
        tinyUrlServer.getValidTargetUrl("id1", resolvedTargetUrl);

        Response redeemtinyurlresponse = restoperation.getMethodWithRedirect(tinyRedeemUrl);
        String credRedeemUrl = redeemtinyurlresponse.getHeader("Location");

        Response redeemtransfersessionresponse = restoperation.getMethodWithRedirect(credRedeemUrl);
        String targetUrl = redeemtransfersessionresponse.getHeader("Location");
        System.out.println(targetUrl);
        System.out.println(redeemtransfersessionresponse.getCookie("AUTH"));
        Assert.assertTrue(helper.checkAuthCodeForProof(env,"user_id_signed_in",redeemtransfersessionresponse.getCookie("AUTH")),"Auth Cookie doesn't have sign in proof");

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
