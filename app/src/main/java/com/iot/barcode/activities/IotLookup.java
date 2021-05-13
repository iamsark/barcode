package com.iot.barcode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot.barcode.models.IotProduct;
import com.iot.barcode.utils.LookupAdapter;
import com.iot.iotbarcode.R;

import java.lang.reflect.Type;
import java.util.List;

public class IotLookup extends AppCompatActivity {

    private String lookupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_lookup);

        Bundle bundle = getIntent().getExtras();
        lookupType = bundle.getString("iot.barcode.lookup.type");

        findViewById(R.id.iot_lookup_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IotCreateTag.class);
                intent.putExtra("iot.barcode.lookup.type", lookupType);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareListView();
    }

    private void prepareListView() {

        List<String> list = null;
        Gson converter = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();

        Context context = getApplicationContext();
        SharedPreferences iotPref = context.getSharedPreferences(getString(com.iot.iotbarcode.R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = iotPref.edit();

        String rawRecords = "[]";
        Button actionBtn = findViewById(R.id.iot_lookup_add_btn);
        TextView header = findViewById(R.id.iot_lookup_header_text);

        if (lookupType.equals("CAT")) {
            header.setText("Category List");
            actionBtn.setText("New Category");
            rawRecords = iotPref.getString(String.valueOf(R.string.iot_barcode_categories), "[]");
            editor.putString("iot_lookup_view", "CAT");
        } else if(lookupType.equals("BRAND")) {
            header.setText("Brand List");
            actionBtn.setText("New Brand");
            rawRecords = iotPref.getString(String.valueOf(R.string.iot_barcode_brands), "[]");
            editor.putString("iot_lookup_view", "BRAND");
        } else if(lookupType.equals("COLOR")) {
            header.setText("Color List");
            actionBtn.setText("New Color");
            rawRecords = iotPref.getString(String.valueOf(R.string.iot_barcode_colors), "[]");
            editor.putString("iot_lookup_view", "COLOR");
        }

        editor.apply();

        list = converter.fromJson(rawRecords, type);
        ListView listView = (ListView) findViewById(R.id.iot_lookup_list_view);

        ArrayAdapter adapter = new LookupAdapter(this, list, listView);
        listView.setAdapter(adapter);

    }
}