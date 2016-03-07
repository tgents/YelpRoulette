package stooges.three.finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.dd.CircularProgressButton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CircularProgressButton maincircle;
    CircularProgressButton favcircle;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> rating = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> url = new ArrayList<String>();
    final double lat = 47.655149;
    final double lon = -122.307947;
    final int dist = 8046;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button favBtn = (Button) findViewById(R.id.favs);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.settings_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

        maincircle = (CircularProgressButton) findViewById(R.id.search_button);
        maincircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Within this method, call the async task that connects to Yelp and pulls restaurant data
                if (maincircle.isIndeterminateProgressMode()) {

                    maincircle.setIndeterminateProgressMode(false);
                    maincircle.setProgress(0);
                } else {

                    maincircle.setIndeterminateProgressMode(true);
                    maincircle.setProgress(50); // set progress > 0 & < 100 to display indeterminate progress
                    //Get the necessary information first from preferences, to make sure we are searching correctly.
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String distance = sharedPref.getString("pref_distance", "");
                    if(distance != "") new YelpApi().execute("restaurant", lat + "", lon + "", distance + "");
                    else new YelpApi().execute("restaurant", lat + "", lon + "", dist + "");
                }
            }
        });

        favcircle = (CircularProgressButton) findViewById(R.id.favorites_search);
        favcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Within this method, call the async task that will pull restaurant from favorites list
                if(favcircle.isIndeterminateProgressMode()) {
                    favcircle.setIndeterminateProgressMode(false);
                    favcircle.setProgress(0);
                } else {
                    favcircle.setIndeterminateProgressMode(true);
                    favcircle.setProgress(50); // set progress > 0 & < 100 to display indeterminate progress
                }
            }
        });
    }

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
                //insert the information into the arraylist. Should be passed to restaurant activity
                //through an intent, along with all the necessary information. The characters can be parsed there.

                JSONObject json = new JSONObject(response);
                JSONArray businesses;
                businesses = json.getJSONArray("businesses");
                for(int i = 0; i < businesses.length(); i++) {
                    JSONObject rest = businesses.getJSONObject(i);
                    names.add(rest.getString("name"));
                    rating.add(rest.getString("rating_img_url"));
                    image.add(rest.getString("image_url"));
                    address.add(rest.getJSONObject("location").getString("display_address"));
                    url.add(rest.getString("url"));
                }
                //Log.v(TAG, names.get(0) + " " + rating.get(0) + " " + image.get(0) + " " + address.get(0));

                //printResults("SEARCHTEST", response);
                Intent intent = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
                intent.putExtra("names", names);
                intent.putExtra("rating", rating);
                intent.putExtra("image", image);
                intent.putExtra("address", address);
                intent.putExtra("url", url);
                startActivity(intent);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        public void printResults(String TAG, String message) {
            int maxLogSize = 2000;
            for(int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v(TAG, message.substring(start, end));
            }
        }

    }



}

//Instructions for messing with the circular button: https://github.com/dmytrodanylyk/circular-progress-button/wiki/User-Guide

//Spinner spinner = (Spinner) findViewById(R.id.filter_distance);
//// Create an ArrayAdapter using the string array and a default spinner layout
//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//        R.array.distance, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);