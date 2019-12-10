    package com.hackathon.optfit;

    import android.app.Service;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.hardware.Sensor;
    import android.hardware.SensorEvent;
    import android.hardware.SensorEventListener;
    import android.hardware.SensorManager;
    import android.os.IBinder;
    import android.util.Log;
    import android.widget.Toast;

    import com.hackathon.optfit.dao.DaoManager;
    import com.hackathon.optfit.entities.AccelerationReading;

    import java.io.FileOutputStream;
    import java.io.FileWriter;

    /*
        BackgroundAccelerometer
        Nicolas Hahn
        - Android application to record accelerometer data to log file
        - Runs in background, restarts itself at bootup
    */
    public class BackgroundAccelerometerService extends Service implements SensorEventListener{
        static final String LOG_TAG = MainActivity.class.getSimpleName();
        private boolean mInitialized;
        private SensorManager mSensorManager;
        private Sensor mAccelerometer;
        // epoch time since last file write
        private long lastTime = 0;
        // minimum time in seconds to write to file after previous write
        private int period = 5;
        private DaoManager DaoManager;
        private int UserId;

        public BackgroundAccelerometerService() {
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            Log.d("Service Started","Service Started");
            mInitialized = false;
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            DaoManager = new DaoManager(this);

            SharedPreferences prefs = this.getSharedPreferences(
                    "com.hackathon.optfit.backgroundaccelerometer", Context.MODE_PRIVATE);
            UserId =  prefs.getInt("UserId",2);
            
            return START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
            Log.d("Service Destroyed", "Service Destroyed");
            mSensorManager.unregisterListener(this);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (!mInitialized) mInitialized = true;
            long tsLong = System.currentTimeMillis()/1000;
            if (tsLong > lastTime+period) {
                lastTime = tsLong;
                recordAccelData(x, y, z, tsLong);
            }
        }

        // write to file a line in format:
        // epochtime, x, y, z
        public void recordAccelData(float x, float y, float z, Long tsLong){
            String ts = tsLong.toString();
            String accelLine = ts+", "+Float.toString(x)+", "+Float.toString(y)+", "+Float.toString(z)+"\n";
            try {
                AccelerationReading ar = new AccelerationReading();
                ar.userId = UserId;
                ar.x = x;
                ar.y = y;
                ar.z = z;
                DaoManager.AccelerationsApi.post(ar);
                Log.e(LOG_TAG, "writing to file " + accelLine);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "exception when writing file in recordAccelData");
            }
        }
    }