package com.iot.barcode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.iot.iotbarcode.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;

public class IotBarcodeView extends AppCompatActivity {

    private Context context;
    private ImageView barcodeView;
    private MultiFormatWriter multiFormatWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_barcode_view);

        context = this;

        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("iot.barcode.product.code");

        multiFormatWriter = new MultiFormatWriter();
        barcodeView = findViewById(R.id.barcode_image);

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.CODE_128,600,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcodeView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        findViewById(R.id.iot_barcode_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintHelper printHelper = new PrintHelper(context);
                printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                // Get the bitmap for the ImageView's drawable.
                Bitmap bitmap = ((BitmapDrawable) barcodeView.getDrawable()).getBitmap();
                // Print the bitmap.
                printHelper.printBitmap("Barcode", bitmap);
            }
        });

        findViewById(R.id.iot_barcode_share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File cachePath = new File(context.getExternalCacheDir(), "images/");
                cachePath.mkdirs();

                File file = new File(cachePath, "image.png");
                FileOutputStream fileOutputStream;

                try {
                    fileOutputStream = new FileOutputStream(file);
                    Bitmap bitmap = ((BitmapDrawable) barcodeView.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Uri imageFileUri = FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".provider", file);

                //create a intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, imageFileUri);
                intent.setType("image/png");
                startActivity(Intent.createChooser(intent, "Share with"));

            }
        });

    }
}