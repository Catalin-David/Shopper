package com.example.shopper.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class Review implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    @ColumnInfo(name = "grocery_item_id")
    private int groceryItemId;
    @ColumnInfo(name = "user_name")
    private String userName;
    private String date;
    private String text;

    public Review(int groceryItemId, String userName, String date, String text) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        this.date = date;
        this.text = text;
    }

    @Ignore
    protected Review(Parcel in) {
        groceryItemId = in.readInt();
        userName = in.readString();
        date = in.readString();
        text = in.readString();
    }

    @Ignore
    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Ignore
    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groceryItemId);
        dest.writeString(userName);
        dest.writeString(date);
        dest.writeString(text);
    }
}
