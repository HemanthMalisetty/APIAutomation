package TestFrameWork;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "userCredential")
    public static Object[][] createData() {
        return new Object[][] {
                { "banky@hotmail.com", "Password1" },
                { "fun-notverified@o2.co.uk", "Password1"}

        };
    }

}
