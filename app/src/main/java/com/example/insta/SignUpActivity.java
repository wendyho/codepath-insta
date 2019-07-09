package com.example.insta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private EditText fullNameInput;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameInput = findViewById(R.id.etUser_Signup);
        passwordInput = findViewById(R.id.etPassword_Signup);
        emailInput = findViewById(R.id.etEmail);
        fullNameInput = findViewById(R.id.etFullName);
        signUpBtn = findViewById(R.id.btnSignUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set core properties
                user.setUsername(usernameInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());
                user.setEmail(emailInput.getText().toString());
                // Set custom properties
                user.put("handle", fullNameInput.getText().toString());
                // Invoke signUpInBackground
                signUp(user);
            }
        });



    }

    private void signUp(ParseUser user){
        user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.d("SignUp Activity", "Sign up success");
                    final Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("SignUp Activity", "Sign up failure");
                    e.printStackTrace();
                    }
                }

        });
    }
}
