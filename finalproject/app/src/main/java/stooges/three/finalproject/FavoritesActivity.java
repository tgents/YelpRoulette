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

        dataAdapter = new FavoriteAdapter(this,R.layout.fav_detail, favorites);
        ListView listView = (ListView) findViewById(R.id.favlist);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


    }

    private class Favorite{
        public String id;
        public String name;

        public Favorite(String i, String na){
            id = i;
            name = na;
    }

    private class FavoriteAdapter extends ArrayAdapter<Favorite> {

        private ArrayList<Favorite> countryList;

        public FavoriteAdapter(Context context, int textViewResourceId,
                               ArrayList<Favorite> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Favorite>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.fav_detail, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }



            return convertView;

        }

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Favorite> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}
}
