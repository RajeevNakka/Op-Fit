package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hackathon.optfit.dao.UserDao;
import com.hackathon.optfit.entities.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User u = new User();
        u.userName = "fsdfd";
        new UserDao(this).CreateNewUser(u);
    }
}
