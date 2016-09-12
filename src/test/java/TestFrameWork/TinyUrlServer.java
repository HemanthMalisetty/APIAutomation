package TestFrameWork;

import com.pyruby.stubserver.StubMethod;
import com.pyruby.stubserver.StubServer;
import org.json.Property;

import java.util.Properties;

public class TinyUrlServer {
    public TinyUrlServer() {
        this.stubServer = new StubServer(9122);
    }

    public String createValidTinyUrl(String tinyUrl) {

        Properties responseBody = new Properties();
        responseBody.setProperty("urlId", "urlId1");
        responseBody.setProperty("tiny_url", tinyUrl);

        StubMethod method = StubMethod.put("/tinyurl");
        stubServer.expect(method).thenReturn(200, "application/json", Property.toJSONObject(responseBody).toString());

        return tinyUrl;
    }

    public String createValidTinyUrl() {
        return createValidTinyUrl("https://localhost.o2.co.uk:8443/credhandler/tinyurl/redeem/id");
    }

    public void getValidTargetUrl(String id, String targetUrlIntended) {
        Properties responseBody = new Properties();
        responseBody.setProperty("urlId", "urlId1");
        responseBody.setProperty("target_url", targetUrlIntended);

        StubMethod method = StubMethod.get("/tinyurl/" + id);
        stubServer.expect(method).thenReturn(200, "application/json", Property.toJSONObject(responseBody).toString());

    }

    public void getValidTargetUrl(String targetUrlIntended) {
        getValidTargetUrl("id", targetUrlIntended);
    }

    public void getInvalidTargetUrl() {
        StubMethod method = StubMethod.get("/tinyurl/id");
        stubServer.expect(method).thenReturn(404, "application/json", "");
    }

    public void start() {
        stubServer.start();
    }

    public void stop() {
        stubServer.stop();

    }

    private StubServer stubServer;
}
