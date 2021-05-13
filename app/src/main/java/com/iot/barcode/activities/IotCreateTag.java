package com.iot.barcode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iot.iotbarcode.R;

import org.json.JSONArray;
import org.json.JSONException;

public class IotCreateTag extends AppCompatActivity {

    private TextView tagText;
    private String lookupType;
    private String lookupRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_create_tag);

        Bundle bundle = getIntent().getExtras();
        lookupType = bundle.getString("iot.barcode.lookup.type");
        tagText = findViewById(R.id.iot_new_tag_text);

        Context context = getApplicationContext();
        SharedPreferences iotPref = context.getSharedPreferences(getString(com.iot.iotbarcode.R.string.preference_file_key), Context.MODE_PRIVATE);

        if (lookupType.equals("CAT")) {
            tagText.setHint("Category");
            lookupRecord = iotPref.getString(String.valueOf(R.string.iot_barcode_categories), "[]");
        } else if(lookupType.equals("BRAND")) {
            tagText.setHint("Brand");
            lookupRecord = iotPref.getString(String.valueOf(R.string.iot_barcode_brands), "[]");
        } else if(lookupType.equals("COLOR")) {
            tagText.setHint("Color");
            lookupRecord = iotPref.getString(String.valueOf(R.string.iot_barcode_colors), "[]");
        }

        findViewById(R.id.iot_tag_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tagText.getText().toString().length() > 0) {

                    Context context = getApplicationContext();
                    SharedPreferences iotPref = context.getSharedPreferences(getString(com.iot.iotbarcode.R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = iotPref.edit();

                    JSONArray arr = null;
                    try {
                        arr = new JSONArray(lookupRecord);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    arr.put(tagText.getText().toString());
                    if (lookupType.equals("CAT")) {
                        editor.putString(String.valueOf(R.string.iot_barcode_categories), arr.toString());
                    } else if(lookupType.equals("BRAND")) {
                        editor.putString(String.valueOf(R.string.iot_barcode_brands), arr.toString());
                    } else if(lookupType.equals("COLOR")) {
                        editor.putString(String.valueOf(R.string.iot_barcode_colors), arr.toString());
                    }
                    editor.apply();
                    finish();

                } else {
                    tagText.setError("Please Enter "+ tagText.getHint());
                }

            }
        });

    }
}