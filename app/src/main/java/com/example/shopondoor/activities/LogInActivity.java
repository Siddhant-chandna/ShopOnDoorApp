package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.FocusFinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopondoor.MainActivity;
import com.example.shopondoor.R;
import com.example.shopondoor.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {
    Button signIn,forgot_btn;
    EditText email,password;
    TextView signUp,forgot;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signIn=findViewById(R.id.login_btn);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        signUp=findViewById(R.id.sign_up);
        forgot=findViewById(R.id.forget);
        forgot_btn=findViewById(R.id.forget_btn);
        forgot_btn.setVisibility(View.GONE);
        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.loginProgress);
        progressBar.setVisibility(View.GONE);
        db= FirebaseFirestore.getInstance();
        password.setFocusable(true);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setFocusable(false);
                forgot_btn.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.GONE);
            }
        });

        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill=email.getText().toString();
                if(TextUtils.isEmpty(emaill)){
                    Toast.makeText(LogInActivity.this,"Email is Empty!",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.sendPasswordResetEmail(emaill)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LogInActivity.this, "Password reset Email sent!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LogInActivity.this,LogInActivity.class));
                                    }
                                    else{
                                        Toast.makeText(LogInActivity.this, "Email is not registeres with us", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LogInActivity.this,WelcomeActivity.class));
                                    }
                                }
                            });
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void loginUser(){
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email is Empty!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Password is Empty!",Toast.LENGTH_SHORT).show();
        }
        else if(userPassword.length()<6) {
            Toast.makeText(this, "Password is too Short,It must be greater than 6 Characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        // LOGIN USER
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogInActivity.this,"LogIn Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LogInActivity.this,"LogIn Error:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}