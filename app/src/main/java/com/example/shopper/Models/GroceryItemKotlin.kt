package com.example.shopper.Models

import android.os.Parcel
import android.os.Parcelable
import com.example.shopper.Utils
import java.util.*

class GroceryItemKotlin : Parcelable {
    var id: Int
    var name: String
    var description: String
    var imageUrl: String
    var category: String
    var availableAmount: Int
    var price: Double
    var popularityPoint: Int
    var userPoint: Int
    var reviews: ArrayList<Review>
    var rate: Int

    constructor(name: String, description: String, imageUrl: String, category: String, availableAmount: Int,
                price: Double) {
        id = Utils.getId()
        this.name = name
        this.description = description
        this.imageUrl = imageUrl
        this.category = category
        this.availableAmount = availableAmount
        this.price = price
        popularityPoint = 0
        userPoint = 0
        reviews = ArrayList()
        rate = 0
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        name = `in`.readString()
        description = `in`.readString()
        imageUrl = `in`.readString()
        category = `in`.readString()
        availableAmount = `in`.readInt()
        price = `in`.readDouble()
        popularityPoint = `in`.readInt()
        userPoint = `in`.readInt()
        reviews = `in`.createTypedArrayList(Review.CREATOR)
        rate = `in`.readInt()
    }

    override fun toString(): String {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", availableAmount=" + availableAmount +
                ", price=" + price +
                ", popularityPoint=" + popularityPoint +
                ", userPoint=" + userPoint +
                ", reviews=" + reviews +
                ", rate=" + rate +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(imageUrl)
        dest.writeString(category)
        dest.writeInt(availableAmount)
        dest.writeDouble(price)
        dest.writeInt(popularityPoint)
        dest.writeInt(userPoint)
        dest.writeTypedList(reviews)
        dest.writeInt(rate)
    }

    companion object {
        val CREATOR: Parcelable.Creator<GroceryItem> = object : Parcelable.Creator<GroceryItem> {
            override fun createFromParcel(`in`: Parcel): GroceryItem {
                return GroceryItem(`in`)
            }

            override fun newArray(size: Int): Array<GroceryItem?> {
                return arrayOfNulls(size)
            }
        }
    }

    object CREATOR : Parcelable.Creator<GroceryItem> {
        override fun createFromParcel(parcel: Parcel): GroceryItem {
            return GroceryItem(parcel)
        }

        override fun newArray(size: Int): Array<GroceryItem?> {
            return arrayOfNulls(size)
        }
    }
}