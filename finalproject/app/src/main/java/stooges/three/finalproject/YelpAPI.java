package stooges.three.finalproject;


import android.os.AsyncTask;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Thomas on 3/1/2016.
 */
public class YelpApi extends AsyncTask<String, Void, Response> {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";

    private static final String consumer = "7EPO_0MB-5XsfzeE5PpqRw";
    private static final String consumer_secret = "5BYGwh_WC9AdJL6_dKFYr1JKCVc";
    private static final String token = "pXGPbhiaGJ7YiXADvT5uxSOSAFqGzKVy";
    private static final String token_secret = "NEIInw9KhCUBcu6s5qzYl6A5oUo";

    private OAuthService service;
    private Token accessToken;

    @Override
    protected Response doInBackground(String... params) {
        String term = params[0];
        String latitude = params[1];
        String longitude = params[2];
        this.service = new ServiceBuilder().provider(YelpApi2.class).apiKey(consumer).apiSecret(consumer_secret).build();
        this.accessToken = new Token(token, token_secret);
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("cll", latitude + "," + longitude);
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response;
    }
}
