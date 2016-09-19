package TestFrameWork;


import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Reporter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fasterxml.jackson.databind.JsonMappingException.from;
import static io.restassured.RestAssured.given;

public class HelperClass {
    RestOperations operations;

    public String getTokenWithSignedinProof(String env,String username,String password){

        Setup.setupAuthServerURL(env);

        Map<String, Object> authcodeRequestbody = new HashMap<>();
        authcodeRequestbody.put("preferred_username", username);
        authcodeRequestbody.put("password", password);

        operations=new RestOperations();
        Response authcodeResponse = operations.postMethod(authcodeRequestbody, "/v1/auth/password_o2");

        Map<String, Object> authcodeResponsebody = authcodeResponse.getBody().as(Map.class);
        String authCookie = (String) authcodeResponsebody.get("auth_cookie_code");

        Map<String, Object> accesstokenRequestbody = new HashMap<>();
        accesstokenRequestbody.put("code", authCookie);
        accesstokenRequestbody.put("grant_type", "auth_cookie");
        List<String> scopes = Arrays.asList("openid", "profile", "email", "phone", "otac");
        accesstokenRequestbody.put("scope", scopes);

        Response accessTokenResponse = operations.postMethod(accesstokenRequestbody, "/v1/token");

        Map<String, Object> accessTokenResponsebody = accessTokenResponse.getBody().as(Map.class);
        String accessToken = (String) accessTokenResponsebody.get("access_token");

        return accessToken;

    }

    public boolean checkAuthCodeForProof(String env,String proofExpression, String authCode){

        Setup.setupAuthServerURL(env);
        Map<String, Object> accesstokenRequestbody = new HashMap<>();
        accesstokenRequestbody.put("code", authCode);
        accesstokenRequestbody.put("grant_type", "auth_cookie");
        List<String> scopes = Arrays.asList("openid", "profile", "email", "phone", "otac");
        accesstokenRequestbody.put("scope", scopes);

        operations=new RestOperations();
        Response accessTokenResponse = operations.postMethod(accesstokenRequestbody,"/v1/token");

        Map<String, Object> accessTokenResponsebody = accessTokenResponse.getBody().as(Map.class);
        String accessToken = (String) accessTokenResponsebody.get("access_token");

        Response checkResponse = operations.getMethod("/v1/check/" + proofExpression, accessToken);
        if(checkResponse.getStatusCode() == 204){
            return true;
        }else{
            return false;
        }
    }

    public String getURLfromsms(String smsbody){
        String url = null;
        Pattern p = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = p.matcher(smsbody);
        while (m.find()){
            int matchStart = m.start(1);
            int matchEnd = m.end();
            url=smsbody.substring(matchStart,matchEnd);
        }

        return url;
    }

    public String generateRandomString(){
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public String generateRandomPhoneNumber(){
        return "+447" +RandomStringUtils.randomNumeric(9);
    }
}
