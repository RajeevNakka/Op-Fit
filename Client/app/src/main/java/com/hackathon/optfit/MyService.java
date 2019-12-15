package com.hackathon.optfit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Checking Services",Toast.LENGTH_SHORT).show();
        BackgroundLocationService.start(this);
        BackgroundAccelerometerService.start(this);
        return START_STICKY;
    }
}
