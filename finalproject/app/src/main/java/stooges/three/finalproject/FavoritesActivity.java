package stooges.three.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    FavoriteAdapter dataAdapter = null;
    ArrayList<Favorite> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        final double lat = 47.655149;
        final double lon = -122.307947;
        final int dist = 8046;

        DatabaseHelper favdb = new DatabaseHelper(this);
        Cursor cursor = favdb.getAllFavorites();

        Button favBtn = (Button) findViewById(R.id.thomas);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YelpApi search = new YelpApi();
                search.execute("restaurant", lat + "", lon + "", dist + "");
            }
        });


        //dataAdapter = new FavoriteAdapter(this, R.layout.fav_detail, favorites);

        ListView listView = (ListView) findViewById(R.id.favlist);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


    }

    private class Favorite {
        public String id;
        public String name;

        public Favorite(String i, String na) {
            id = i;
            name = na;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class FavoriteAdapter extends ArrayAdapter<Favorite>{
        private ArrayList<Favorite> favList;
        public FavoriteAdapter(Context context, int textViewResourceId,
                               ArrayList<Favorite> countryList) {
            super(context, textViewResourceId, countryList);
            this.favList = new ArrayList<Favorite>();
            this.favList.addAll(countryList);
        }
    }

}
