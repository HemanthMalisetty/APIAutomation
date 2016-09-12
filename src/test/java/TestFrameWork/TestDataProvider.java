package TestFrameWork;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "userCredential")
    public static Object[][] createUserData() {
        return new Object[][] {
                { "banky@hotmail.com", "Password1" },
                { "fun-notverified@o2.co.uk", "Password1"}

        };
    }

    @DataProvider(name = "PAYMmsisdns")
    public static Object[][] createMsisdnData() {
        return new Object[][] {
                { "447881222333" },
                { "447992872782"}

        };
    }

}
