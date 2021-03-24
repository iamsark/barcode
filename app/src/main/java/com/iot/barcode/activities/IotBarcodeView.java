package com.iot.barcode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

    private String code ="123456789012";
    private ImageView barcodeView;
    private MultiFormatWriter multiFormatWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_barcode_view);

        multiFormatWriter = new MultiFormatWriter();
        barcodeView = findViewById(R.id.barcode_image);

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.UPC_A,600,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcodeView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        findViewById(R.id.iot_barcode_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintHelper printHelper = new PrintHelper(getApplicationContext());
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

                barcodeView.setDrawingCacheEnabled(true);

                Bitmap bitmap = barcodeView.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));

            }
        });

    }
}