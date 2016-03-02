package stooges.three.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavoritesActivity extends AppCompatActivity {

    private static final String API_HOST = "api.yelp.com";
    private static final String SEARCH_PATH = "/v2/search";
    
    public static final String consumer = "7EPO_0MB-5XsfzeE5PpqRw";
    public static final String consumer_secret = "5BYGwh_WC9AdJL6_dKFYr1JKCVc";
    public static final String token = "pXGPbhiaGJ7YiXADvT5uxSOSAFqGzKVy";
    public static final String token_secret = "NEIInw9KhCUBcu6s5qzYl6A5oUo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
    }
}
