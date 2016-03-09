package stooges.three.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sean Ker on 3/1/2016
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";
    CircularProgressButton rollAgainButton;

    TextView restaurantNameTextView;
    ImageView restaurantRatingImageView;
    ImageView restaurantImageView;
    TextView restaurantCategoriesTextView;

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
        generatesRestaurantSetsView(restaurants);

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

    private void generatesRestaurantSetsView(ArrayList<Restaurant> restaurants) {
        Random r = new Random();
        // r.nextInt returns random integer from 0 to n (exclusive)
        int randomNum = r.nextInt(restaurants.size());

        Restaurant generated = restaurants.get(randomNum);
        String name = generated.name;
        String ratingUrl = generated.rating;
        String imageUrl = generated.imageUrl;
        String categories = generated.categories;
//        String categories = generated.categories;

        restaurantNameTextView = (TextView)findViewById(R.id.restaurant_name);
        restaurantNameTextView.setText(name);

        restaurantRatingImageView = (ImageView)findViewById(R.id.restaurant_rating);
        // Downloads image of rating
        DownloadImageTask dit = new DownloadImageTask(restaurantRatingImageView);
        dit.execute(ratingUrl);

        restaurantImageView = (ImageView)findViewById(R.id.restaurant_image);
        dit = new DownloadImageTask(restaurantImageView);
        dit.execute(imageUrl);

        restaurantCategoriesTextView = (TextView)findViewById(R.id.restaurant_category);
        // getting rid of braces [ and ] at the beginning and end of the string
        // ["Japanese","japanese"],["Soup","soup"]
        categories = categories.substring(1, categories.length() - 1);
        restaurantCategoriesTextView.setText(categories);

        // testing
//        Log.v(TAG, categories);

    }

    // Downloads an image using the URL and displays it in an ImageView
    // resource found: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }

}
