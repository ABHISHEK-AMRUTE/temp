package com.amrute_studio.mediscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DashBoard extends AppCompatActivity {
LinearLayout form;
FirebaseUser user;
dataClass dataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        LinearLayout form = findViewById(R.id.post_form);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dataObject = new dataClass(DashBoard.this);
        dataObject.getData();

        findViewById(R.id.gallary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,ShowQRcode.class));
            }
        });


        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(DashBoard.this,ScanQRCode.class));
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,Form.class).putExtra("Edit",1));
            }
        });

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(form.getVisibility() == View.VISIBLE)
                {
                    form.setVisibility(View.GONE);
                }else{
                    form.setVisibility(View.VISIBLE);
                }


            }
        });


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(DashBoard.this);

                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("name", dataObject.getName());
                    jsonBody.put("age", dataObject.getAge() + "");
                    jsonBody.put("my_ph", dataObject.getPhone());
                    jsonBody.put("ph1", dataObject.getPhoneMemberOne());
                    jsonBody.put("ph2", dataObject.getPhoneSecondName());
                    jsonBody.put("blood_group", dataObject.getBloodGroup());
                    jsonBody.put("name1", dataObject.getNameMemberOne());
                    jsonBody.put("height", dataObject.getHeight() + "");
                    jsonBody.put("weight", dataObject.getWeight());
                    jsonBody.put("gender", dataObject.getGender());
                    jsonBody.put("name2", dataObject.getNameSecondName());
                    EditText disese = findViewById(R.id.disese);
                    String arr[] = {disese.getText().toString()};
                    jsonBody.put("others", arr);

                    final String requestBody = jsonBody.toString();

                    String URL = "https://645e12d3efc4.ngrok.io/update_account/" + user.getUid().trim();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(DashBoard.this, "" + user.getUid(), Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(DashBoard.this, "Added", Toast.LENGTH_SHORT).show();
                                    form.setVisibility(View.GONE);

                                }
                            }, 1000);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                }catch(Exception e)
                {

                }

            }
        });

    }



}