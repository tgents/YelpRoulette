package stooges.three.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Sean Ker on 3/1/2016
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        Button searchButton = (Button) findViewById(R.id.search_button);

        //Information for restaurants are in this. They are "name", "address", "image", "url", and "rating"
        Intent intent = getIntent();
        Log.v(TAG, "Intent was received. Can begin inserting information onto screen");



    }


}
