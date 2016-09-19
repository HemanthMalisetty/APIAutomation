package TestSuite;

import TestFrameWork.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class PushNotificationAssetTestSuite extends BaseTestScript {

    AssetHelperClass assethelper;
    HelperClass helper;

    @BeforeMethod(alwaysRun = true)
    public void setupTest(){
        assethelper = new AssetHelperClass();
        helper = new HelperClass();
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {"test"})
    public void CreatePUSHNOTIFICATIONasset(String env,String username, String password){
        Map<String, Object> createAssetRequestBody = new HashMap<>();
        Response createAssetResponse = assethelper.createAssetWithoutAssetIDLoggedin(env,username,password,"PUSHNOTIFICATION",createAssetRequestBody);
        Assert.assertEquals(createAssetResponse.getStatusCode(), 200);
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void DeletePUSHNOTIFICATIONasset(String env,String username, String password){
        Response deleteAssetResponse = assethelper.deleteAssetLoggedin(env, username, password, "PUSHNOTIFICATION", helper.generateRandomString());
        Assert.assertEquals(deleteAssetResponse.getStatusCode(),204);
    }

}
