package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hackathon.optfit.Util.SessionManager;

public class Home extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Button LogoutButton;
    Switch AccelerometerSwitch, GpsSwitch;
    private SessionManager SessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SessionManager = new SessionManager(this);
        if (SessionManager.exists() == false) {
            OpenLoginActivity();
        }

        LogoutButton = findViewById(R.id.button_logout);
        LogoutButton.setOnClickListener(this);

        AccelerometerSwitch = findViewById(R.id.switch_accelerometer);
        GpsSwitch = findViewById(R.id.switch_gps);

        AccelerometerSwitch.setChecked(isMyServiceRunning(BackgroundAccelerometerService.class));
        GpsSwitch.setChecked(isMyServiceRunning(BackgroundLocationService.class));

        AccelerometerSwitch.setOnCheckedChangeListener(this);
        GpsSwitch.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_logout) {
            SessionManager.clear();
            OpenLoginActivity();
        }
    }

    private void OpenLoginActivity() {
        finish();
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();

        if (id == R.id.switch_accelerometer) {
            if(buttonView.isChecked())
                BackgroundAccelerometerService.start(this);
            else
                BackgroundAccelerometerService.stop(this);
        } else if (id == R.id.switch_gps) {
            if(buttonView.isChecked())
                BackgroundLocationService.start(this);
            else
                BackgroundLocationService.stop(this);
        }
    }
}
