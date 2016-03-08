package stooges.three.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 3/4/2016.
 */
public class Restaurant implements Parcelable {

    public String name;
    public String rating;
    public String imageUrl;
    public String address;
    public String yelpUrl;

    public Restaurant(String name, String rate, String img, String addr, String yelp){
        this.name = name;
        this.rating = rate;
        this.imageUrl = img;
        this.address = addr;
        this.yelpUrl = yelp;
    }

    private Restaurant(Parcel in) {
        this.name = in.readString();
        this.rating = in.readString();
        this.imageUrl = in.readString();
        this.address = in.readString();
        this.yelpUrl = in.readString();
    }

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public String toString() {
        return name + " " + rating + " " + imageUrl + " " + address + " " + yelpUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.rating);
        dest.writeString(this.imageUrl);
        dest.writeString(this.address);
        dest.writeString(this.yelpUrl);
    }
}
