package stooges.three.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        long lat = (long) 47.6550739;
        long lon = (long) -122.3081657;

        YelpApi search = new YelpApi();
        Log.v("SEARCHTEST", search.search("restaurant", lat, lon));
    }
}
