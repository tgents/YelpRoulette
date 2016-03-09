package stooges.three.finalproject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    FavoriteAdapter dataAdapter = null;
    ArrayList<Favorite> favorites;
    DatabaseHelper favdb;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favdb = new DatabaseHelper(this);

        remove = (Button) findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Favorite f : favorites) {
                    if (f.isChecked) {
                        int temp = favdb.removeFavorite(f.id, f.name);
                        if(temp > 0){
                            Toast.makeText(FavoritesActivity.this, f.name + " successfully removed.", Toast.LENGTH_SHORT).show();
                        }
                        //refresh favorites list
                        getFavorites();
                    }
                }
            }
        });

        getFavorites();
    }

    //populates the current favorites
    private void getFavorites(){
        Cursor cursor = favdb.getAllFavorites();
        favorites = new ArrayList<Favorite>();
        if (cursor.getCount() < 1) {
            remove.setVisibility(View.INVISIBLE);
            TextView noFav = (TextView) findViewById(R.id.nofavs);
            noFav.setVisibility(View.VISIBLE);
        } else {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Favorite f = new Favorite(cursor.getString(0), cursor.getString(1));
                    favorites.add(f);
                    cursor.moveToNext();
                }
            }
        }
        dataAdapter = new FavoriteAdapter(this, R.layout.fav_detail, favorites);
        ListView listView = (ListView) findViewById(R.id.favlist);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }

    //stores favorite info
    private class Favorite {
        public String id;
        public String name;
        public boolean isChecked;

        public Favorite(String i, String na) {
            id = i;
            name = na;
            isChecked = false;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    //custom adapter for favorites list
    private class FavoriteAdapter extends ArrayAdapter<Favorite> {
        private ArrayList<Favorite> favList;
        private Context context;
        private int resourceId;

        public FavoriteAdapter(Context context, int resource,
                               ArrayList<Favorite> favs) {
            super(context, resource, favs);
            this.favList = new ArrayList<Favorite>();
            this.favList.addAll(favs);
            this.context = context;
            this.resourceId = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceId, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.favname);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkbox);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favList.get(pos).isChecked = cb.isChecked();
                }
            });
            name.setText(favList.get(position).name);
            if (favList.get(position).isChecked)
                cb.setChecked(true);
            else
                cb.setChecked(false);
            return convertView;
        }
    }

}
