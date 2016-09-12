package TestSuite;

import TestFrameWork.*;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class VisualIVRTestSuite extends BaseTestScript {

    TinyUrlServer tinyUrlServer;
    MessagingHelperClass msgsim;
    HelperClass helper;

    @BeforeMethod(alwaysRun = true)
    public void setupTinyurlstub() {
        tinyUrlServer = new TinyUrlServer();
        tinyUrlServer.start();
        msgsim = new MessagingHelperClass();
        msgsim.clearHistory();
        helper = new HelperClass();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        tinyUrlServer.stop();
    }

    @Test(dataProvider = "PAYMmsisdns", dataProviderClass = TestDataProvider.class,groups = {"test"})
    public void HappyPathEndtoEndScenario(String phonenumber){

        tinyUrlServer.createValidTinyUrl("https://localhost.o2.co.uk:8443/credhandler/tinyurl/redeem/id1");

        String jsonAsString;

        Setup.setupAuthServerURL();

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

        jsonAsString = datokenresponse.asString();
        Reporter.log(jsonAsString);

        Map<String, Object> datokenResponsebody = datokenresponse.getBody().as(Map.class);
        String datToken = (String) datokenResponsebody.get("da_token");
        System.out.println(datToken);


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

        jsonAsString = transfersessionresponse.asString();
        Reporter.log(jsonAsString);

        Map<String, Object> transfersessionResponsebody = transfersessionresponse.getBody().as(Map.class);
        String session_id = (String) transfersessionResponsebody.get("transfer_session_id");

        System.out.println(session_id);
        String smscontent = msgsim.getSMS(phonenumber);
        String url = helper.getURLfromsms(smscontent);
        System.out.println(url);

        String resolvedTargetUrl = "https://localhost.o2.co.uk:8443/credhandler/redeem/ts/" +session_id;
        tinyUrlServer.getValidTargetUrl("id1", resolvedTargetUrl);

    }


    @Test(dataProvider = "PAYMmsisdns", dataProviderClass = TestDataProvider.class,groups = {"local"})
    public void CreateTokenforVerifiedCustomer(String phonenumber){
        Setup.setupAuthServerURL();

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
