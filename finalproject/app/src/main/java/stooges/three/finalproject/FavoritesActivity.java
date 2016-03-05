package stooges.three.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        final double lat = 47.655149;
        final double lon = -122.307947;
        final int dist = 8046;

        Button favBtn = (Button) findViewById(R.id.thomas);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YelpApi search = new YelpApi();
                search.execute("restaurant", lat+"", lon+"", dist+"");
            }
        });



    }
}
