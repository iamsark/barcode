package com.iot.barcode.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iot.barcode.models.IotProduct;
import com.iot.barcode.services.IotDbManager;
import com.iot.iotbarcode.R;

import java.util.ArrayList;

public class IotProductList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_product_list);
        populateProductListView();
        findViewById(R.id.iot_create_product_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IotProductList.this, IotCreateProduct.class));
            }
        });
    }

    private void populateProductListView() {
        IotDbManager iotDB = new IotDbManager(getApplicationContext()).open();
        ArrayList<IotProduct> lp = iotDB.list();
        findViewById(R.id.empty_text_view).setVisibility(View.GONE);

        Log.d("IotProductList", "Product size : "+ lp.size());

        if (lp.size() > 0) {
            findViewById(R.id.product_list_view).setVisibility(View.VISIBLE);
            IotProduct[] products = new IotProduct[lp.size()];
            lp.toArray(products);
            ArrayAdapter adapter = new ArrayAdapter<IotProduct>(this, R.layout.archive_item, products);

            ListView listView = (ListView) findViewById(R.id.product_list_view);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a,
                                        View v, int position, long id) {
                    IotProduct product = (IotProduct) a.getItemAtPosition(position);
                    Intent intent = new Intent(v.getContext(), IotViewProduct.class);
                    intent.putExtra("iot.product", product);
                    startActivity(intent);
                }
            });
        } else {
            findViewById(R.id.product_list_view).setVisibility(View.GONE);
            findViewById(R.id.empty_text_view).setVisibility(View.VISIBLE);
        }
    }
}