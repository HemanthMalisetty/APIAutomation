package TestFrameWork;

import io.restassured.response.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagingHelperClass {
    RestOperations operations;

    public MessagingHelperClass() {
        Setup.setupMPSSOAsimURL();
        operations=new RestOperations();
    }

    public String getSMS(String phonenumber){
        Setup.setupMPSSOAsimURL();
        Response msghistoryResponse = operations.getMethod("/serviceprovider/simulatorhistory/" +phonenumber);

        return msghistoryResponse.asString();

    }

    public void clearHistory(){
        Setup.setupMPSSOAsimURL();
        operations.deleteMethod("/serviceprovider/simulatorhistory");

    }

}
