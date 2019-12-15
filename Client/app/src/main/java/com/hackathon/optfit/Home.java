package com.hackathon.optfit;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.hackathon.optfit.Util.SessionManager;
import com.hackathon.optfit.Util.Util;

import java.util.Calendar;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Home extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int RC_ALL = 1;
    Button LogoutButton;
    Switch AccelerometerSwitch, GpsSwitch;
    EditText SosNumber, Threshold;
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

        CheckPermissionsAndSetup();
    }

    @AfterPermissionGranted(RC_ALL)
    private void CheckPermissionsAndSetup() {
        String[] perms = {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.MODIFY_AUDIO_SETTINGS,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            AccelerometerSwitch = findViewById(R.id.switch_accelerometer);
            GpsSwitch = findViewById(R.id.switch_gps);

            AccelerometerSwitch.setChecked(Util.isMyServiceRunning(this,BackgroundAccelerometerService.class));
            GpsSwitch.setChecked(Util.isMyServiceRunning(this,BackgroundLocationService.class));

            AccelerometerSwitch.setOnCheckedChangeListener(this);
            GpsSwitch.setOnCheckedChangeListener(this);

            SosNumber = findViewById(R.id.SOS_Number);
            Threshold = findViewById(R.id.RV_Threshold);

            SosNumber.setText(SessionManager.getSosNumber());
            SosNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        SessionManager.setSosNumber(s.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            Threshold.setText(SessionManager.getThreshold()+"");
            Threshold.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        SessionManager.setThreshold(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });


            Intent intent = new Intent(this, MyService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this.getApplicationContext(), 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000, pendingIntent);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.Permissions_Rationale),
                    RC_ALL, perms);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_logout) {
            SessionManager.clear();
            BackgroundLocationService.stop(this);
            BackgroundAccelerometerService.stop(this);
            OpenLoginActivity();
        }
    }

    private void OpenLoginActivity() {
        finish();
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
