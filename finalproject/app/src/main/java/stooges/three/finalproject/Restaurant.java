package stooges.three.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 3/4/2016.
 */
public class Restaurant implements Parcelable {

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String id;
    public String name;
    public String rating;
    public String imageUrl;
    public String address;
    public String yelpUrl;
    public String categories;

    //constructor with given info
    public Restaurant(String id, String name, String rate, String img, String addr, String yelp, String cat) {
        this.id = id;
        this.name = name;
        this.rating = rate;
        this.imageUrl = img;
        this.address = addr;
        this.yelpUrl = yelp;
        this.categories = cat;
    }

    //private constructer that creates a Restaurant from a given parcel
    private Restaurant(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.rating = in.readString();
        this.imageUrl = in.readString();
        this.address = in.readString();
        this.yelpUrl = in.readString();
        this.categories = in.readString();
    }

    @Override
    public String toString() {
        return id + "," + name + "," + rating + "," + imageUrl + "," + address + "," + yelpUrl + "," + categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.rating);
        dest.writeString(this.imageUrl);
        dest.writeString(this.address);
        dest.writeString(this.yelpUrl);
        dest.writeString(this.categories);
    }
}
