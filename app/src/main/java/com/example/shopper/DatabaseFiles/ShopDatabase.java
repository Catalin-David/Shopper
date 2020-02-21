package com.example.shopper.DatabaseFiles;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shopper.Models.GroceryItem;
import com.example.shopper.Models.Review;

@Database(entities = {GroceryItem.class, Review.class, CartItem.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class ShopDatabase extends RoomDatabase {

    public abstract GroceryItemDao groceryItemDao();
    public abstract ReviewDao reviewDao();
    public abstract CartItemDao cartItemDao();

    public static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new initialAsyncTask(instance).execute();
        }
    };

    private static class initialAsyncTask extends AsyncTask<Void, Void, Void>{

        private GroceryItemDao groceryItemDao;

        public initialAsyncTask(ShopDatabase database) {
            groceryItemDao = database.groceryItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            groceryItemDao.insert(new GroceryItem("Ice Cream", "Made of fresh milk and sprinkled with chocolate chips",
                    "https://images-na.ssl-images-amazon.com/images/I/510AGt0X0lL.jpg", "Food", 23, 3));
            groceryItemDao.insert(new GroceryItem("Cheese", "This deluxe gourmet meat and cheese sampler features a carefully selected assortment of artisan meats, cheeses and crackers on a keepsake wooden serving tray.",
                    "https://images-na.ssl-images-amazon.com/images/I/71msf7UPOCL._SL1000_.jpg", "Food", 20, 12.99));
            groceryItemDao.insert(new GroceryItem("Orange juice", "Minute Maid Juice, Orange, 10 Fl Oz, 6 Ct (Pack of 2)",
                    "https://images-na.ssl-images-amazon.com/images/I/51NWYvbSdbL.jpg", "Drinks", 13, 9.99));
            groceryItemDao.insert(new GroceryItem("Tomato Sauce", "Light and fresh, Tomato Basil sauce is bursting with the fresh smell and taste of bright basil, juicy San Marzano tomatoes and fresh onions.",
                    "https://images-na.ssl-images-amazon.com/images/I/7164iwwvy8L._SL1500_.jpg", "Food", 6, 19.99));
            groceryItemDao.insert(new GroceryItem("Chicken nuggets", "Evaxo Chicken Nuggets, Frozen (5 lbs.)",
                    "https://images-na.ssl-images-amazon.com/images/I/817EW5djF4L._SL1500_.jpg", "Food", 23, 24.99));
            groceryItemDao.insert(new GroceryItem("Pepsi", "Pepsi splash variety pack, 8 pepsi mango, 8 pepsi lime, 8 pepsi blueberry, 24 cans/12 oz, total 288 fl oz",
                    "https://images-na.ssl-images-amazon.com/images/I/611%2BiQ2o%2B5L._SL1122_.jpg", "Drinks", 94, 29.99));
            groceryItemDao.insert(new GroceryItem("Shampoo", "Old Spice, Shampoo and Conditioner 2 in 1, Pure Sport for Men, 32 fl oz, Twin Pack",
                    "https://images-na.ssl-images-amazon.com/images/I/81mPM0yLdFL._SL1500_.jpg", "Personal care", 29, 15.01));
            groceryItemDao.insert(new GroceryItem("Deodorant", "Gives you that old man strength",
                    "https://images-na.ssl-images-amazon.com/images/I/81M9MonSOaL._SL1500_.jpg", "Personal care", 68, 8.45));
            groceryItemDao.insert(new GroceryItem("Chocolate", "Your girlfriend will love this!",
                    "https://images-na.ssl-images-amazon.com/images/I/61S5rMToZIL._SL1000_.jpg", "Food", 45, 17));

            return null;
        }
    }
}
