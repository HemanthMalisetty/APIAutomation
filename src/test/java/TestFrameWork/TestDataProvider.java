package TestFrameWork;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "userCredential")
    public static Object[][] createUserData(ITestContext context) {
        Object[][] result = null;
        for(String group : context.getIncludedGroups()){
            if (group.equalsIgnoreCase("test") || group.equalsIgnoreCase("local")){
                result = new Object[][] {
                        { "local", "banky@hotmail.com", "Password1" },
                        { "local", "fun-notverified@o2.co.uk", "Password1"}
                };
            }else if(group.equalsIgnoreCase("ref")){
                result = new Object[][] {
                        { "ref", "arvind3222@mailinator.com", "Password123" }
                };
            }
            return result;
        }
        return result;
    }

    @DataProvider(name = "userCredentialwithUID")
    public static Object[][] createUserDataWithUID(ITestContext context) {
        Object[][] result = null;
        for(String group : context.getIncludedGroups()){
            if (group.equalsIgnoreCase("test") || group.equalsIgnoreCase("local")){
                result = new Object[][] {
                        { "local", "banky@hotmail.com", "Password1", "12345" },
                        { "local", "fun-notverified@o2.co.uk", "Password1", "b2b80c53-c41a-41ed-funnotverified-89a451f2b7ca"}
                };
            }else if(group.equalsIgnoreCase("ref")){
                result = new Object[][] {
                        { "ref", "arvind3222@mailinator.com", "Password123", "" }
                };
            }
            return result;
        }
        return result;
    }


    @DataProvider(name = "PAYMmsisdns")
    public static Object[][] createMsisdnData(ITestContext context) {
        Object[][] result = null;
        for(String group : context.getIncludedGroups()){
            if (group.equalsIgnoreCase("test") || group.equalsIgnoreCase("local")){
                result = new Object[][] {
                        {"local", "447881222333" },
                        {"local","447992872782"}
                };
            }else if(group.equalsIgnoreCase("ref")){
                result = new Object[][] {
                        {"ref", "447521005662" }
                };
            }
            return result;
        }
        return result;
    }

}
