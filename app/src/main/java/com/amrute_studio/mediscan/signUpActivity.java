package com.amrute_studio.mediscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText email,password;
    Button signup;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailId);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        pb = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pb.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signUpActivity.this, "SignUp complete! welcome to community.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(signUpActivity.this,Form.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(signUpActivity.this, "Error! please try again later.", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }
        });

    }
}