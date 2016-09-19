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
        Map<String, Object> createAssetRequestBody = new HashMap<>();
        Response createAssetResponse = assethelper.createAssetLoggedin(env,username,password,"O2OPEN",helper.generateRandomString(),createAssetRequestBody);
        Assert.assertEquals(createAssetResponse.getStatusCode(),204);
    }

    @Test(dataProvider = "userCredential", dataProviderClass = TestDataProvider.class,groups = {})
    public void DeleteO2OPENasset(String env,String username, String password){
        Response deleteAssetResponse = assethelper.deleteAssetLoggedin(env, username, password, "O2OPEN", helper.generateRandomString());
        Assert.assertEquals(deleteAssetResponse.getStatusCode(),204);
    }

}
