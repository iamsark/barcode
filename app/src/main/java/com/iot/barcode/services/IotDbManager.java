package com.iot.barcode.services;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.iot.barcode.activities.IotHome;
import com.iot.barcode.activities.IotMain;
import com.iot.barcode.models.IotProduct;

import java.util.ArrayList;

public class IotDbManager {

    private IotBarcodeDbHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public IotDbManager(Context c) {
        context = c;
    }

    public IotDbManager open() throws SQLException {
        dbHelper = new IotBarcodeDbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String code, Float price, String category, String brand, String color, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_NAME, name);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_CODE, code);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_PRICE, price);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_CATEGORY, category);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_BRAND, brand);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_COLOR, color);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_DESCRIPTION, desc);

        if (database == null) {
            try {
                this.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (database != null) {
            database.insert(IotBarcodeDbHelper.PRODUCT_TABLE_NAME, null, contentValue);
            Toast.makeText(context.getApplicationContext(), "Product Created Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Database could not be opened", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                IotBarcodeDbHelper.PRODUCT_COLUMN_ID,
                IotBarcodeDbHelper.PRODUCT_COLUMN_NAME,
                IotBarcodeDbHelper.PRODUCT_COLUMN_CODE,
                IotBarcodeDbHelper.PRODUCT_COLUMN_PRICE,
                IotBarcodeDbHelper.PRODUCT_COLUMN_BRAND,
                IotBarcodeDbHelper.PRODUCT_COLUMN_COLOR,
                IotBarcodeDbHelper.PRODUCT_COLUMN_CATEGORY,
                IotBarcodeDbHelper.PRODUCT_COLUMN_DESCRIPTION
        };
        Cursor cursor = database.query(IotBarcodeDbHelper.PRODUCT_TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<IotProduct> list() {
        Cursor c = fetch();
        IotProduct product = null;
        ArrayList<IotProduct> list = new ArrayList<IotProduct>();
        if (c != null && c.getCount() > 0) {
            do {
                product = new IotProduct();
                product.setId(c.getInt(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_ID)));
                product.setName(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_NAME)));
                product.setCode(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_CODE)));
                product.setPrice(c.getFloat(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_PRICE)));
                product.setCat(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_CATEGORY)));
                product.setBrand(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_BRAND)));
                product.setColor(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_COLOR)));
                product.setDesc(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_DESCRIPTION)));
                list.add(product);
            } while(c.moveToNext());
        }
        return list;
    }

    public IotProduct get(String _code) {
        String selectQuery = "SELECT  * FROM " + IotBarcodeDbHelper.PRODUCT_TABLE_NAME + " WHERE " + IotBarcodeDbHelper.PRODUCT_COLUMN_CODE + "='" + _code +"'";
        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null) c.moveToFirst();

        IotProduct product = new IotProduct();

        product.setId(c.getInt(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_ID)));
        product.setName(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_NAME)));
        product.setCode(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_CODE)));
        product.setPrice(c.getFloat(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_PRICE)));
        product.setCat(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_CATEGORY)));
        product.setBrand(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_BRAND)));
        product.setColor(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_COLOR)));
        product.setDesc(c.getString(c.getColumnIndex(IotBarcodeDbHelper.PRODUCT_COLUMN_DESCRIPTION)));

        return product;
    }

    public int update(long _id, String name, String code, Float price, String category, String brand, String color, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_NAME, name);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_CODE, code);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_PRICE, price);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_CATEGORY, category);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_BRAND, brand);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_COLOR, color);
        contentValue.put(IotBarcodeDbHelper.PRODUCT_COLUMN_DESCRIPTION, desc);
        int i = database.update(IotBarcodeDbHelper.PRODUCT_TABLE_NAME, contentValue, IotBarcodeDbHelper.PRODUCT_COLUMN_ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(IotBarcodeDbHelper.PRODUCT_TABLE_NAME, IotBarcodeDbHelper.PRODUCT_COLUMN_ID + "=" + _id, null);
    }

}
