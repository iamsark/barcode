package com.iot.barcode.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class IotBarcodeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "IotBarcode.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String PRODUCT_COLUMN_ID = "_id";
    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_CODE = "code";
    public static final String PRODUCT_COLUMN_PRICE = "price";
    public static final String PRODUCT_COLUMN_CATEGORY = "category";
    public static final String PRODUCT_COLUMN_COLOR = "color";
    public static final String PRODUCT_COLUMN_BRAND = "brand";
    public static final String PRODUCT_COLUMN_DESCRIPTION = "description";

    // Creating table query
    private static final String CREATE_TABLE = "create table " + PRODUCT_TABLE_NAME + "("
            + PRODUCT_COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PRODUCT_COLUMN_NAME + " TEXT NOT NULL, "
            + PRODUCT_COLUMN_CODE + " TEXT, "
            + PRODUCT_COLUMN_PRICE + " REAL NOT NULL, "
            + PRODUCT_COLUMN_CATEGORY + " TEXT, "
            + PRODUCT_COLUMN_BRAND + " TEXT, "
            + PRODUCT_COLUMN_COLOR + " TEXT, "
            + PRODUCT_COLUMN_DESCRIPTION + " TEXT);";

    public IotBarcodeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        onCreate(db);
    }

}
