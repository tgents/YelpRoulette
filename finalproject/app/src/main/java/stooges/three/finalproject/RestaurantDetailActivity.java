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

    // XML elements
    CircularProgressButton rollAgainButton;
    TextView restaurantNameTextView;
    ImageView restaurantRatingImageView;
    ImageView restaurantImageView;
    TextView restaurantCategoriesTextView;

    // global variables
    int restaurantSize;
    Random r = new Random();
    ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Information for restaurants are in this. They are "name", "address", "image", "url", and "rating"
        Intent intent = getIntent();
        restaurants = (ArrayList<Restaurant>) intent.getExtras().get("restaurants");
        restaurantSize = restaurants.size();

        generatesRestaurantSetsView();
        setUpRollButton();
    }

    private void generatesRestaurantSetsView() {
        // r.nextInt returns random integer from 0 to n (exclusive)
        Random r = new Random();
        int randomNum = r.nextInt(restaurantSize);

        Restaurant generated = restaurants.get(randomNum);
        String name = generated.name;
        String ratingUrl = generated.rating;
        String imageUrl = generated.imageUrl;
        String categories = generated.categories;

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

    private void setUpRollButton() {
        // initialize Circular Progress Button
        rollAgainButton = (CircularProgressButton) findViewById(R.id.search_button);

        if(rollAgainButton.isIndeterminateProgressMode()) {
            rollAgainButton.setIndeterminateProgressMode(false);
            rollAgainButton.setProgress(0);
        }

        final Random r = new Random();

        // Within this method, call the async task that connects to Yelp and pulls restaurant data
        rollAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rollAgainButton.isIndeterminateProgressMode() || rollAgainButton.getProgress() != 0) {
                    rollAgainButton.setIndeterminateProgressMode(false);
                    rollAgainButton.setProgress(0);
                } else {
                    generatesRestaurantSetsView();
                }
            }
        });
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
