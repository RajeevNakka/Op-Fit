package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hackathon.optfit.dao.DaoManager;
import com.hackathon.optfit.entities.HeartRateReading;
import com.hackathon.optfit.entities.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User u = new User();
        u.userName = "fsdfd";

        DaoManager daoManager = new DaoManager(this);
        daoManager.UsersApi.post(u);

        for(int i=0;i<10;i++){
            HeartRateReading hr = new HeartRateReading();
            hr.userId = 5;
            hr.rate = (70 + (int)(Math.random()*10))%130;
            daoManager.HeartRateApi.post(hr);
        }
    }
}
