package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hackathon.optfit.Util.ResponseListener;
import com.hackathon.optfit.Util.SessionManager;
import com.hackathon.optfit.dao.DaoManager;
import com.hackathon.optfit.entities.User;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText UserName, Password;
    Button SignUpButton, LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        LoginButton = findViewById(R.id.button_login);
        SignUpButton = findViewById(R.id.button_sign_up);

        LoginButton.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_login) {
            new DaoManager(this).UsersApi.get(UserName.getText().toString(), new ResponseListener<User>() {
                @Override
                public void onComplete(User user) {
                    if (user == null || user.password.equals(Password.getText().toString()) == false)
                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    else {
                        new SessionManager(Login.this).create(user);
                        BackgroundLocationService.start(Login.this);
                        BackgroundAccelerometerService.start(Login.this);
                        openHomeActivity();
                    }
                }
            });
        } else if (id == R.id.button_sign_up) {
            openSignUpActivity();
        }
    }

    private void openHomeActivity() {
        finish();
        Intent homeIntent = new Intent(this, Home.class);
        startActivity(homeIntent);
    }

    private void openSignUpActivity() {
        finish();
        Intent signUpIntent = new Intent(this, SignUp.class);
        startActivity(signUpIntent);
    }
}
