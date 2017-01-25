package me.thomastseng.hungry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorites.db";
    private static final String TABLE_NAME = "favorites";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_RATING = "rating";
    private static final String COL_IMAGE = "image";
    private static final String COL_ADDRESS = "address";
    private static final String COL_URL = "url";
    private static final String COL_CATEGORIES = "categories";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COL_ID + " TEXT PRIMARY KEY NOT NULL, " +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_RATING + " TEXT, " +
                    COL_IMAGE + " TEXT, " +
                    COL_ADDRESS + " TEXT, " +
                    COL_URL + " TEXT, " +
                    COL_CATEGORIES + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        this.db = db;
    }

    public Cursor getAllFavorites(){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Log.v(TAG, cursor.toString());
        return cursor;
    }

    //inserts the given restaurant into the favorites table
    public String insertRestaurant(Restaurant r) {
        // Checking to see if input ID is already stored in the database
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(COL_ID, r.id);
        values.put(COL_NAME, r.name);
        values.put(COL_RATING, r.rating);
        values.put(COL_IMAGE, r.imageUrl);
        values.put(COL_ADDRESS, r.address);
        values.put(COL_URL, r.yelpUrl);
        values.put(COL_CATEGORIES, r.categories);
        db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();
        return "New favorite added";
    }

    //removes the restaurant based on the given id and name
    public int removeFavorite(String id, String name){
        db = this.getWritableDatabase();
        Log.v("db", "before remove");
        int success = db.delete(TABLE_NAME,"id=? and name=?",new String[]{id, name});
        Log.v("db", "after remove");
        return success;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}