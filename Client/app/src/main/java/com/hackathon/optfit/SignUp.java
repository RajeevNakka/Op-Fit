package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.hackathon.optfit.Util.NukeSSLCerts;
import com.hackathon.optfit.Util.ResponseListener;
import com.hackathon.optfit.Util.SessionManager;
import com.hackathon.optfit.dao.DaoManager;
import com.hackathon.optfit.entities.User;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText Username,Password,ConfirmPassword,FirstName,LastName,Age;
    RadioGroup Genderr;
    Button CreateButton;
    private DaoManager DaoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Username =  findViewById(R.id.username);
        Password =  findViewById(R.id.password);
        ConfirmPassword =  findViewById(R.id.confirm_password);
        FirstName =  findViewById(R.id.first_name);
        LastName =  findViewById(R.id.last_name);
        Age =  findViewById(R.id.age);
        Genderr =  findViewById(R.id.gender);
        CreateButton = findViewById(R.id.button_create);

        CreateButton.setOnClickListener(this);

        new NukeSSLCerts().nuke();

        DaoManager = new DaoManager(this);
        /*User u = new User();
        u.userName = "fsdfd";


        daoManager.UsersApi.post(u);

        for(int i=0;i<10;i++){
            HeartRateReading hr = new HeartRateReading();
            hr.userId = 5;
            hr.rate = (70 + (int)(Math.random()*10))%130;
            daoManager.HeartRateApi.post(hr);
        }*/
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_create){
            CreateUser();
        }
    }

    private void CreateUser() {
        User user = new User();
        user.userName = Username.getText().toString();
        user.password = Password.getText().toString();
        user.firstName = FirstName.getText().toString();
        user.lastName = LastName.getText().toString();
        user.age = Integer.parseInt(Age.getText().toString());
        user.gender = Genderr.getCheckedRadioButtonId() == R.id.male ? 0 : 1;
        user.deviceId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        DaoManager.UsersApi.post(user, new ResponseListener<User>() {
            @Override
            public void onComplete(User user) {
                new SessionManager(SignUp.this).create(user);
                BackgroundLocationService.start(SignUp.this);
                BackgroundAccelerometerService.start(SignUp.this);
                openHomeActivity();
            }
        });
    }

    private void openHomeActivity() {
        finish();
        Intent homeIntent = new Intent(SignUp.this,Home.class);
        startActivity(homeIntent);
    }
}
