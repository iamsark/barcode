package com.iot.barcode.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.iot.iotbarcode.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class LookupAdapter extends ArrayAdapter<String> {

    Context cont = null;
    Button btn = null;
    LookupAdapter me = null;
    ListView listView = null;

    public LookupAdapter(Context context, List<String> objects, ListView _listView) {
        super(context, 0, objects);
        cont = context;
        me = this;
        listView = _listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.archive_tag_item, parent, false);
        }

        Button btButton = (Button) convertView.findViewById(R.id.remove_tag_btn);
        TextView nameText = (TextView) convertView.findViewById(R.id.name);

        nameText.setText(item);

        // Cache row position inside the button using `setTag`
        btButton.setTag(position);
        // Attach the click event handler
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                String item = getItem(position);

                SharedPreferences iotPref = cont.getSharedPreferences("iot_", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = iotPref.edit();

                String lookupType = iotPref.getString("iot_lookup_view", "");
                String lookupRecordKey = "";

                if (lookupType.equals("CAT")) {
                    lookupRecordKey = String.valueOf(R.string.iot_barcode_categories);
                } else if(lookupType.equals("BRAND")) {
                    lookupRecordKey = String.valueOf(R.string.iot_barcode_brands);
                } else if(lookupType.equals("COLOR")) {
                    lookupRecordKey = String.valueOf(R.string.iot_barcode_colors);
                }

                String records = iotPref.getString(lookupRecordKey, "[]");
                JSONArray arr = null;
                try {
                    arr = new JSONArray(records);
                    for (int i = 0; i < arr.length(); i++) {
                        if (((String) arr.get(i)).equals(item)) {
                            arr.remove(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                editor.putString(lookupRecordKey, arr.toString());
                editor.apply();

                me.remove(item);
                me.notifyDataSetChanged();
                listView.invalidateViews();
            }
        });

        return convertView;
    }

}
