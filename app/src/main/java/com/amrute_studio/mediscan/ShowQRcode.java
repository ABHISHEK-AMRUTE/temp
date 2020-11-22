package com.amrute_studio.mediscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ShowQRcode extends AppCompatActivity {

    ImageView qrCode;
    QRGEncoder qrgEncoder;
    dataClass dataobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_rcode);

        qrCode = findViewById(R.id.qrcode);
        dataobj = new dataClass(ShowQRcode.this);
        dataobj.getData();

        qrgEncoder = new QRGEncoder(dataobj.getUid(), null, QRGContents.Type.TEXT,300);
        try {
            // Getting QR-Code as Bitmap

            // Setting Bitmap to ImageView
            qrCode.setImageBitmap(qrgEncoder.encodeAsBitmap());
        } catch (WriterException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }
}