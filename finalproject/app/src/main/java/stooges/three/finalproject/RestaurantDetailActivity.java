package stooges.three.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sean Ker on 3/1/2016
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    CircularProgressButton rollAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Information for restaurants are in this. They are "name", "address", "image", "url", and "rating"
        Intent intent = getIntent();
        ArrayList<Restaurant> restaurants = (ArrayList<Restaurant>) intent.getExtras().get("restaurants");
        // loop through restaurants
//        for(Restaurant r : restaurants){
//            Log.v(TAG, r.toString());
//        }

        Random r = new Random();
        int randomNum = r.nextInt(restaurants.size());

        Restaurant generated = restaurants.get(randomNum);


        // hard coded coordinates, will replace with location services
        final double lat = 47.655149;
        final double lon = -122.307947;
        final int dist = 8046;


        // initialize Circular Progress Button
        rollAgainButton = (CircularProgressButton) findViewById(R.id.search_button);

        if(rollAgainButton.isIndeterminateProgressMode()) {
            rollAgainButton.setIndeterminateProgressMode(false);
            rollAgainButton.setProgress(0);
        }

        // Within this method, call the async task that connects to Yelp and pulls restaurant data
        rollAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RestaurantDetailActivity.this, "Finding restaurant...", Toast.LENGTH_SHORT).show();
                if (rollAgainButton.isIndeterminateProgressMode() || rollAgainButton.getProgress() != 0) {
                    rollAgainButton.setIndeterminateProgressMode(false);
                    rollAgainButton.setProgress(0);
                } else {
                    if (lat == 0 || lon == 0) {
                        Toast.makeText(RestaurantDetailActivity.this, "Location not found, is location turned on?", Toast.LENGTH_SHORT).show();
                    } else {
                        rollAgainButton.setIndeterminateProgressMode(true);
                        rollAgainButton.setProgress(1); // set progress > 0 & < 100 to display indeterminate progress
                        //Get the necessary information first from preferences, to make sure we are searching correctly.
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String distance = sharedPref.getString("pref_distance", "");

                        if (distance != "")
                            new YelpApi().execute("restaurant", lat + "", lon + "", distance + "");
                        else new YelpApi().execute("restaurant", lat + "", lon + "", dist + "");
                    }
                }
            }
        });
        Log.v(TAG, "Intent was received. Can begin inserting information onto screen");

    }

    @Override
    public void onBackPressed()
    {
        finish();
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }

}
