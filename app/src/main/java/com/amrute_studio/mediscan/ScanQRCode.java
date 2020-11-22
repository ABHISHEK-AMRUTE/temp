package com.amrute_studio.mediscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ScanQRCode extends AppCompatActivity{

    //View Objects
    private Button buttonScan;
    TextView idnote;
    TextView name,name1,phone,pone1,phone2,age,name2;
    TextView bloodgrp,disease;
    int proceed =0;
    String uid="";
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_code);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);

        idnote = findViewById(R.id.idnote);


        //intializing scan object
        qrScan = new IntentIntegrator(this);


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proceed==0)
                qrScan.initiateScan();
                else{
                    startActivity(new Intent(ScanQRCode.this,woundScan.class).putExtra("uid",uid));
                }
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    uid = result.getContents();


                    RequestQueue requestQueue = Volley.newRequestQueue(ScanQRCode.this);
                    String URL = "https://9494a04ba0e2.ngrok.io/get_account/"+uid.trim();
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject obj ;

                            try {
                                obj = new JSONObject(response);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                                obj =  null;
                            }

                            name = findViewById(R.id.name);

                            name1 = findViewById(R.id.name1);
                            name2 = findViewById(R.id.name2);
                            bloodgrp = findViewById(R.id.bloodgrp);
                            disease = findViewById(R.id.disease);
                            phone = findViewById(R.id.phone);
                            pone1 = findViewById(R.id.phone1);
                            phone2 = findViewById(R.id.phone2);
                            age = findViewById(R.id.age);;

                            try{
                                name.setText("Name : " + obj.getString("name"));
                                phone.setText("Phone : "+obj.getString("my_ph"));
                                age.setText("Age : "+obj.getString("age"));
                                name1.setText("Name : " +obj.getString("name1"));
                                name2.setText("Name : " +obj.getString("name2"));
                                pone1.setText("Phone : "+obj.getString("ph1"));
                                phone2.setText("Phone : "+obj.getString("ph2"));
                                bloodgrp.setText("Blood Group : "+obj.getString("blood_group"));

                                JSONArray arrJson = obj.getJSONArray("others");
                               String st = "Current Diseases: \n";
                                for(int i = 0; i < arrJson.length(); i++)
                                    st += arrJson.getString(i)+"\n";

                                disease.setText(st);

                            }catch (Exception e)
                            {

                            }


                            findViewById(R.id.animationView).setVisibility(View.GONE);
                            findViewById(R.id.LLLB).setVisibility(View.VISIBLE);


                            buttonScan.setText("Scan Wounds");
                            proceed =1;

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request);


//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}