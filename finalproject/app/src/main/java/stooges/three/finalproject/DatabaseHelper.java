package stooges.three.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorites.db";
    private static final String TABLE_NAME = "favorites";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "userId";
    private static final String COL_RATING = "image";
    private static final String COL_IMAGE = "image";
    private static final String COL_ADDRESS = "address";
    private static final String COL_URL = "address";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COL_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_RATING + " TEXT, " +
                    COL_IMAGE + " TEXT, " +
                    COL_ADDRESS + " TEXT, " +
                    COL_URL + " TEXT);";

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
        Cursor cursor = db.rawQuery("select * from table", null);
        return cursor;
    }

    public String insertRestaurant(Restaurant r) {
        // Checking to see if input ID is already stored in the database
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(COL_ID, count);
        values.put(COL_NAME, r.name);
        values.put(COL_RATING, r.rating);
        values.put(COL_IMAGE, r.imageUrl);
        values.put(COL_ADDRESS, r.address);
        values.put(COL_URL, r.yelpUrl);
        db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();
        return "New favorite added";
    }



//    public String searchName(String userId) {
//        db = this.getReadableDatabase();
//        String query = "SELECT " + COL_USERID + ", " + COL_NAME + " FROM " + TABLE_NAME;
//        Cursor cursor = db.rawQuery(query, null);
//        String result = null;
//        if (cursor.moveToFirst()) {
//            do {
//                String a = cursor.getString(0);
//                if (a.equals(userId)) {
//                    result = cursor.getString(1);
//                    break;
//                }
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return result;
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}