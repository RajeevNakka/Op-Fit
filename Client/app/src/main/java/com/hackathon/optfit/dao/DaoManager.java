package com.hackathon.optfit.dao;

import android.content.Context;

import com.hackathon.optfit.entities.AccelerationReading;
import com.hackathon.optfit.entities.GpsReading;
import com.hackathon.optfit.entities.HeartRateReading;
import com.hackathon.optfit.entities.User;


public class DaoManager{

    public static final String EndPoint = "http://192.168.0.105:81";
    public final WebApiWrapper<User> UsersApi;
    public final WebApiWrapper<AccelerationReading> AccelerationsApi;
    public final WebApiWrapper<GpsReading> GpsApi;
    public final WebApiWrapper<HeartRateReading> HeartRateApi;


    public DaoManager(Context context) {
        UsersApi = new WebApiWrapper<User>(EndPoint + "/Users",context);
        AccelerationsApi = new WebApiWrapper<AccelerationReading>(EndPoint + "/Accelerations",context);
        GpsApi = new WebApiWrapper<GpsReading>(EndPoint + "/GeoLocations",context);
        HeartRateApi = new WebApiWrapper<HeartRateReading>(EndPoint + "/HeartRates",context);
    }
}
