package TestFrameWork;


import io.restassured.RestAssured;
import org.testng.ITestContext;

import static io.restassured.RestAssured.basic;

public class Setup {

    public static void setupAuthServerURL()
    {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/authserver";
        RestAssured.port = 8424;
        RestAssured.authentication = basic("shop.1", "shopSecret");
    }

    public static void setupIdentityURL()
    {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/identity";
        RestAssured.port = 8424;
    }

    public static void setupPersonURL()
    {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/person";
        RestAssured.port = 8424;
        RestAssured.authentication = basic("shop.1", "shopSecret");
    }

    public static void setupCredhandlerURL()
    {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/credhandler";
        RestAssured.port = 8443;
    }

    public static void setupMPSSOAsimURL()
    {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://localhost.o2.co.uk";
        RestAssured.basePath = "/MPS-soa-service-simulator";
        RestAssured.port = 18081;
    }

}
