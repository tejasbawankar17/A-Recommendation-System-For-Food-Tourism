package com.parsh.rrs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.identity.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email_edit_text,password_edit_text;
    Button login_button;
    ProgressBar progress_bar;
    TextView create_account_text_view_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edit_text=findViewById(R.id.email_edit_text);
        password_edit_text=findViewById(R.id.password_edit_text);
        login_button=findViewById(R.id.login_button);
        progress_bar=findViewById(R.id.progress_bar);
        create_account_text_view_btn=findViewById(R.id.create_account_text_view_btn);

        login_button.setOnClickListener(v-> login_User());
        create_account_text_view_btn.setOnClickListener(v->startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)));
    }

    private void login_User() {
        String email=email_edit_text.getText().toString();
        String password=password_edit_text.getText().toString();
        boolean isValidate=validateData(email,password);
        if(!isValidate)
            return;
        LoginAccountInFireBase(email,password);

    }



    void LoginAccountInFireBase(String email,String password)
    {
        changeInProgress(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful())
                {
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Try Again....", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Try Again....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void changeInProgress(boolean inProgress)
    {
        if(inProgress) {
            progress_bar.setVisibility(View.VISIBLE);
            login_button.setVisibility(View.GONE);
        }
        else
        {
            progress_bar.setVisibility(View.GONE);
            login_button.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String email,String password)
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_edit_text.setError("Email is Invalid!");
            return false;
        }
        if(password.length()<6)
        {
            password_edit_text.setError("Password Length is Invalid");
            return false;
        }


        return true;
    }
}