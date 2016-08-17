package TestFrameWork;


import io.restassured.RestAssured;

import static io.restassured.RestAssured.basic;

public class Setup {

    public static void setupAuthServerURL()
    {
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/authserver";
        RestAssured.port = 8424;
        RestAssured.authentication = basic("shop.1", "shopSecret");
    }

    public static void setupIdentityURL()
    {
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/identity";
        RestAssured.port = 8424;
    }

    public static void setupCredhandlerURL()
    {
        RestAssured.baseURI = "https://localhost.o2.co.uk";
        RestAssured.basePath = "/credhandler";
        RestAssured.port = 8443;
    }

}
