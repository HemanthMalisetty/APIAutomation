package TestSuite;

import TestFrameWork.AssetHelperClass;
import TestFrameWork.BaseTestScript;
import TestFrameWork.HelperClass;
import TestFrameWork.TestDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class O2OPENAssetTestSuite extends BaseTestScript {

    AssetHelperClass assethelper;
    HelperClass helper;

    @BeforeMethod(alwaysRun = true)
    public void setupTest(){
        assethelper = new AssetHelperClass();
        helper = new HelperClass();
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void CreateO2OPENasset(String env,String username, String password){
        String accessToken = helper.getTokenWithSignedinProof(env,username,password);
        assethelper.deleteAllAssetByAssetType(env,accessToken,"O2OPEN");
        Map<String, Object> createAssetRequestBody = new HashMap<>();
        Response createAssetResponse = assethelper.createAssetLoggedin(env,accessToken,"O2OPEN",helper.generateRandomString(),createAssetRequestBody);
        Assert.assertEquals(createAssetResponse.getStatusCode(),204);
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void DeleteO2OPENasset(String env,String username, String password){
        String accessToken = helper.getTokenWithSignedinProof(env,username,password);
        String assetIdToDelete = helper.generateRandomString();
        assethelper.createAssetIfNotPresent(env,accessToken,"O2OPEN",assetIdToDelete);
        Response deleteAssetResponse = assethelper.deleteAssetLoggedin(env, accessToken, "O2OPEN", assetIdToDelete);
        Assert.assertEquals(deleteAssetResponse.getStatusCode(),204);
    }

}
