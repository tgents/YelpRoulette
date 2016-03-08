package stooges.three.finalproject;


import android.os.AsyncTask;
import android.util.Log;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Thomas on 3/1/2016.
 */
public class YelpApi extends AsyncTask<String, Void, String> {
    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";

    private static final String consumer = "7EPO_0MB-5XsfzeE5PpqRw";
    private static final String consumer_secret = "5BYGwh_WC9AdJL6_dKFYr1JKCVc";
    private static final String token = "pXGPbhiaGJ7YiXADvT5uxSOSAFqGzKVy";
    private static final String token_secret = "NEIInw9KhCUBcu6s5qzYl6A5oUo";

    private OAuthService service;
    private Token accessToken;

    @Override
    protected String doInBackground(String... params) {
        String term = params[0];
        String latitude = params[1];
        String longitude = params[2];

        //set up service
        this.service = new ServiceBuilder().provider(YelpApi2.class).apiKey(consumer).apiSecret(consumer_secret).build();
        this.accessToken = new Token(token, token_secret);

        //make query and sign
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("radius", params[3]);
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        this.service.signRequest(this.accessToken, request);
        Log.v("SEARCHTEST", request.toString());

        //send query
        Response response = request.send();
        return response.getBody();
    }

    protected void onPostExecute(String response) {
        try{
            printResults("SEARCHTEST", response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void printResults(String TAG, String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.v(TAG, message.substring(start, end));
        }
    }

}