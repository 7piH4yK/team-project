//package data_access.api.apiTests;
//
//import data_access.api.RetrofitClient;
//import okhttp3.HttpUrl;
//import org.junit.Test;
//import retrofit2.Retrofit;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//
//public class RetrofitClientTest {
//
//    @Test
//    void getClientReturnsSingletonInstance() {
//        Retrofit client1 = RetrofitClient.getClient();
//        Retrofit client2 = RetrofitClient.getClient();
//
//        assertNotNull(client1, "RetrofitClient.getClient() should not return null");
//        assertSame(client1, client2, "RetrofitClient should return the same instance (singleton)");
//    }
//
//    @Test
//    void getClientHasCorrectBaseUrl() {
//        Retrofit retrofit = RetrofitClient.getClient();
//
//        HttpUrl baseUrl = retrofit.baseUrl();
//        assertEquals("https", baseUrl.scheme());
//        assertEquals("opentdb.com", baseUrl.host());
//        assertEquals("/", baseUrl.encodedPath(), "Base URL path should end with '/'");
//    }
//}
