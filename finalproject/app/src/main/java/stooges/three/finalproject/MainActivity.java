package stooges.three.finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CircularProgressButton maincircle;
    CircularProgressButton favcircle;

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