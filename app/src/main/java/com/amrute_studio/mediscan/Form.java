package com.amrute_studio.mediscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Form extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    EditText name,nameMemberOne,nameMemberTwo,phone,phoneMemberOne,phoneSecondmember,gender,bloodGroup,ageField,heightField,weightField;
    float age,height,weight;
    int edit=0;
    Button submit;
    dataClass dataObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        dataObject = new dataClass(Form.this);
        dataObject.getData();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        edit = getIntent().getIntExtra("Edit",0);
        name = findViewById(R.id.name);
        nameMemberOne = findViewById(R.id.name1);
        nameMemberTwo = findViewById(R.id.name2);
        phone = findViewById(R.id.phoneNumber);
        phoneMemberOne = findViewById(R.id.phoneNumber1);
        phoneSecondmember = findViewById(R.id.phoneNumber2);
        gender = findViewById(R.id.gender);
        bloodGroup = findViewById(R.id.bloodGrp);
        ageField = findViewById(R.id.age);
        heightField = findViewById(R.id.height);
        weightField = findViewById(R.id.weight);


        if(edit==1)setInitially();
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = Float.parseFloat(ageField.getText().toString());
                height = Float.parseFloat(heightField.getText().toString());
                weight = Float.parseFloat(weightField.getText().toString());
                dataObject.setData(
                        name.getText().toString(),
                        nameMemberOne.getText().toString(),
                        nameMemberTwo.getText().toString(),
                        phone.getText().toString(),
                        phoneMemberOne.getText().toString(),
                        phoneSecondmember.getText().toString(),
                        gender.getText().toString(),
                        bloodGroup.getText().toString(),
                        firebaseUser.getUid(),
                        age,
                        height,
                        weight

                );


                /////////////////posting user

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(Form.this);

                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("name", name.getText().toString());
                    jsonBody.put("age",ageField.getText().toString());
                    jsonBody.put("my_ph",phone.getText().toString());
                    jsonBody.put("ph1",phoneMemberOne.getText().toString());
                    jsonBody.put("ph2",phoneSecondmember.getText().toString());
                    jsonBody.put("blood_group",bloodGroup.getText().toString());
                    jsonBody.put("name1",nameMemberOne.getText().toString());
                    jsonBody.put("height",heightField.getText().toString());
                    jsonBody.put("weight",weightField.getText().toString());
                    jsonBody.put("gender",gender.getText().toString());
                    jsonBody.put("name2",nameMemberTwo.getText().toString());


                    if(edit==1)
                    {  final String requestBody = jsonBody.toString();


                        String URL = "https://645e12d3efc4.ngrok.io/update_account/" + firebaseUser.getUid().trim();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                                Toast.makeText(Form.this, "" + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(Form.this, "Registered for account.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Form.this, DashBoard.class));
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
                    }else {

                        jsonBody.put("id", firebaseUser.getUid());
                        final String requestBody = jsonBody.toString();

                        String URL = "https://645e12d3efc4.ngrok.io/create_account/" + firebaseUser.getUid().trim();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                                Toast.makeText(Form.this, "" + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(Form.this, "Registered for account.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Form.this, DashBoard.class));
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



    }

    private void setInitially() {
        name.setText(dataObject.name);
        phone.setText(dataObject.phone);
        nameMemberOne.setText(dataObject.getNameMemberOne());
        nameMemberTwo.setText(dataObject.getNameSecondName());
        phoneMemberOne.setText(dataObject.getPhoneMemberOne());
        phoneSecondmember.setText(dataObject.getPhoneSecondName());
        ageField.setText(dataObject.getAge()+"");
        weightField.setText(dataObject.getWeight()+"");
        heightField.setText(dataObject.getHeight()+"");
        gender.setText(dataObject.getGender());
        bloodGroup.setText(dataObject.getBloodGroup());
    }
}