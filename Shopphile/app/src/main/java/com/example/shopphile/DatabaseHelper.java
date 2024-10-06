package com.example.shopphile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Shopphile.db";
    private static final int DATABASE_VERSION = 1;

    // Product Table
    public static final String TABLE_PRODUCT = "Product";
    public static final String COLUMN_PRODUCT_IMAGE = "ProductImage"; // Integer
    public static final String COLUMN_PRODUCT_NAME = "ProductName"; // String
    public static final String COLUMN_PRODUCT_PRICE = "ProductPrice"; // Float
    public static final String COLUMN_PRODUCT_DESCRIPTION = "ProductDescription"; // String

    // Cart Table
    public static final String TABLE_CART = "Cart";
    public static final String COLUMN_CART_IMAGE = "CartImage"; // Integer
    public static final String COLUMN_CART_NAME = "CartName"; // String
    public static final String COLUMN_CART_PRICE = "CartPrice"; // Float
    public static final String COLUMN_CART_QUANTITY = "CartQuantity"; // Integer

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductTable = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                COLUMN_PRODUCT_IMAGE + " INTEGER, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " REAL, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT)";

        String createCartTable = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_CART_IMAGE + " INTEGER, " +
                COLUMN_CART_NAME + " TEXT, " +
                COLUMN_CART_PRICE + " REAL, " +
                COLUMN_CART_QUANTITY + " INTEGER)";

        db.execSQL(createProductTable);
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Method to add product to Product Table
    public void addProduct(int image, String name, float price, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_IMAGE, image);
        values.put(COLUMN_PRODUCT_NAME, name);
        values.put(COLUMN_PRODUCT_PRICE, price);
        values.put(COLUMN_PRODUCT_DESCRIPTION, description);
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    // Method to get all products
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);
    }

    // Method to add to Cart
    public void addToCart(int image, String name, float price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_IMAGE, image);
        values.put(COLUMN_CART_NAME, name);
        values.put(COLUMN_CART_PRICE, price);
        values.put(COLUMN_CART_QUANTITY, quantity);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // Method to get all items from Cart
    public Cursor getAllCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CART, null);
    }
}