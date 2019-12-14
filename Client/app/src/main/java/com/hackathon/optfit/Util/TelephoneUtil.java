package com.hackathon.optfit.Util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class TelephoneUtil {
    private final Context Context;
    TelephonyManager manager;
    StatePhoneReceiver myPhoneStateListener;
    boolean callFromApp=false; // To control the call has been made from the application
    boolean callFromOffHook=false; // To control the change to idle state is from the app call

    public  TelephoneUtil(Context context){
        this.Context = context;
    }

    public void makeACall(String phoneNumber, boolean speaker) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        Context.startActivity(intent);

        if (speaker) {
            myPhoneStateListener = new StatePhoneReceiver(Context);
            manager = ((TelephonyManager) Context.getSystemService(Context.TELEPHONY_SERVICE));

        }
    }

    public static void sendSms(String phoneNumber, String message) {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public class StatePhoneReceiver extends PhoneStateListener {
        Context context;

        public StatePhoneReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {

                case TelephonyManager.CALL_STATE_OFFHOOK: //Call is established
                    if (callFromApp) {
                        callFromApp = false;
                        callFromOffHook = true;

                        try {
                            Thread.sleep(1000); // Delay 0,5 seconds to handle better turning on loudspeaker
                        } catch (InterruptedException e) {
                        }

                        //Activate loudspeaker
                        AudioManager audioManager = (AudioManager)
                                context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.MODE_IN_CALL);
                        audioManager.setSpeakerphoneOn(true);
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE: //Call is finished
                    if (callFromOffHook) {
                        callFromOffHook = false;
                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.MODE_NORMAL); //Deactivate loudspeaker
                        manager.listen(myPhoneStateListener, // Remove listener
                                PhoneStateListener.LISTEN_NONE);
                    }
                    break;
            }
        }
    }
}

// Monitor for changes to the state of the phone