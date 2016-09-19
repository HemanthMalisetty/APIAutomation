package TestFrameWork;


import io.restassured.RestAssured;
import org.testng.ITestContext;

import static io.restassured.RestAssured.basic;

public class Setup {

    public static void setupAuthServerURL(String env){
        RestAssured.useRelaxedHTTPSValidation();
        if(env == "local"){
            RestAssured.baseURI = "https://localhost.o2.co.uk";
            RestAssured.basePath = "/authserver";
            RestAssured.port = 8424;
            RestAssured.authentication = basic("shop.1", "shopSecret");
        }else if(env == "ref"){
            RestAssured.baseURI = "https://api.ref.o2.co.uk";
            RestAssured.basePath = "/auth";
            RestAssured.port = 443;
            RestAssured.authentication = basic("smoke", "smokeSecret");
        }
    }

    public static void setupIdentityURL(String env){
        RestAssured.useRelaxedHTTPSValidation();
        if(env == "local"){
            RestAssured.baseURI = "https://localhost.o2.co.uk";
            RestAssured.basePath = "/identity";
            RestAssured.port = 8424;
            RestAssured.authentication = basic("shop.1", "shopSecret");
        }else if(env == "ref"){
            RestAssured.baseURI = "https://api.ref.o2.co.uk";
            RestAssured.basePath = "/identity";
            RestAssured.port = 443;
            RestAssured.authentication = basic("smoke", "smokeSecret");
        }
    }

    public static void setupPersonURL(String env){
        RestAssured.useRelaxedHTTPSValidation();
        if(env == "local"){
            RestAssured.baseURI = "https://localhost.o2.co.uk";
            RestAssured.basePath = "/person";
            RestAssured.port = 8424;
            RestAssured.authentication = basic("shop.1", "shopSecret");
        }else if(env == "ref"){
            RestAssured.baseURI = "https://api.ref.o2.co.uk";
            RestAssured.basePath = "/person";
            RestAssured.port = 443;
            RestAssured.authentication = basic("smoke", "smokeSecret");
        }
    }

    public static void setupCredhandlerURL(String env){
        RestAssured.useRelaxedHTTPSValidation();
        if(env == "local"){
            RestAssured.baseURI = "https://localhost.o2.co.uk";
            RestAssured.basePath = "/credhandler";
            RestAssured.port = 8443;
        }else if(env == "ref"){
            RestAssured.baseURI = "https://identity.ref.o2.co.uk";
            RestAssured.port = 443;
        }
    }

    public static void setupMPSSOAsimURL(){
        RestAssured.baseURI = "http://localhost.o2.co.uk";
        RestAssured.basePath = "/MPS-soa-service-simulator";
        RestAssured.port = 18081;
    }

}
