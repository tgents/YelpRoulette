package stooges.three.finalproject;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

/**
 * Created by Thomas on 3/1/2016.
 */
public class YelpAPI {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";

    public static final String consumer = "7EPO_0MB-5XsfzeE5PpqRw";
    public static final String consumer_secret = "5BYGwh_WC9AdJL6_dKFYr1JKCVc";
    public static final String token = "pXGPbhiaGJ7YiXADvT5uxSOSAFqGzKVy";
    public static final String token_secret = "NEIInw9KhCUBcu6s5qzYl6A5oUo";

    private OAuthService service;
    private Token accessToken;

    public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
//        this.service =
//                new ServiceBuilder().apiKey(consumerKey)
//                        .apiSecret(consumerSecret).build();
//        this.accessToken = new Token(token, tokenSecret);
    }

    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path, service);
        return request;
    }
}
