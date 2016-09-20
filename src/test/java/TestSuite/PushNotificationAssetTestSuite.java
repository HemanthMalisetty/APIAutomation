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

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void CreatePUSHNOTIFICATIONasset(String env,String username, String password){
        String accessToken = helper.getTokenWithSignedinProof(env,username,password);
        assethelper.deleteAllAssetByAssetType(env,accessToken,"PUSHNOTIFICATION");
        Response createAssetResponse = assethelper.createAssetWithoutAssetIDLoggedin(env,accessToken,"PUSHNOTIFICATION",new HashMap<>());
        Assert.assertEquals(createAssetResponse.getStatusCode(), 200);
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void OneAssetPerIdentityRuleForPUSHNOTIFICATIONasset(String env,String username, String password){
        String accessToken = helper.getTokenWithSignedinProof(env,username,password);
        assethelper.deleteAllAssetByAssetType(env,accessToken,"PUSHNOTIFICATION");
        Response createAssetResponse = assethelper.createAssetWithoutAssetIDLoggedin(env, accessToken, "PUSHNOTIFICATION", new HashMap<>());
        Assert.assertEquals(createAssetResponse.getStatusCode(), 200);
        Response createAssetResponse1 = assethelper.createAssetWithoutAssetIDLoggedin(env,accessToken,"PUSHNOTIFICATION",new HashMap<>());
        Assert.assertEquals(createAssetResponse1.getStatusCode(), 409);
        Assert.assertEquals(createAssetResponse1.getBody().as(Map.class).get("error"), "asset_creation_limit_reached");
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void DeletePUSHNOTIFICATIONasset(String env,String username, String password){
        String assetIdToDelete = helper.generateRandomString();
        assethelper.createAssetIfNotPresent(env,username,password,"PUSHNOTIFICATION",assetIdToDelete);
        Response deleteAssetResponse = assethelper.deleteAssetNonLoggedin(env, "PUSHNOTIFICATION", assetIdToDelete);
        Assert.assertEquals(deleteAssetResponse.getStatusCode(),204);
    }

}
