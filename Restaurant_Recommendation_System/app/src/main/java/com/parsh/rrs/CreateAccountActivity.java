package com.parsh.rrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    EditText email_edit_text, password_edit_text, confirm_password_edit_text;
    Button create_button;
    ProgressBar progress_bar;
    TextView login_text_view_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        confirm_password_edit_text = findViewById(R.id.confirm_password_edit_text);
        create_button = findViewById(R.id.create_button);
        progress_bar = findViewById(R.id.progress_bar);
        login_text_view_btn = findViewById(R.id.login_text_view_btn);

        create_button.setOnClickListener(v -> createAccount());
        login_text_view_btn.setOnClickListener(v -> startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class)));

    }

    private void createAccount() {
        String email = email_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();
        String confirmPassword = confirm_password_edit_text.getText().toString();
        boolean isValidate = validateData(email, password, confirmPassword);
        if (!isValidate)
            return;
        createAccountInFirebase(email, password);

    }

    private void createAccountInFirebase(String email, String password) {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Succesfully Created Account,Check email to verify", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        } else {
                            Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progress_bar.setVisibility(View.VISIBLE);
            create_button.setVisibility(View.GONE);
        } else {
            progress_bar.setVisibility(View.GONE);
            create_button.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_edit_text.setError("Email is Invalid!");
            return false;
        }
        if (password.length() < 6) {
            password_edit_text.setError("Password Length is Invalid");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirm_password_edit_text.setError("Password not Matched!");
            return false;
        }
        return true;
    }
}