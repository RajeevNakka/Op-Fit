package com.hackathon.optfit.dao;

import android.content.Context;

import com.hackathon.optfit.R;
import com.hackathon.optfit.entities.AccelerationReading;
import com.hackathon.optfit.entities.GpsReading;
import com.hackathon.optfit.entities.HeartRateReading;
import com.hackathon.optfit.entities.User;


public class DaoManager {

    public final WebApiWrapper<User> UsersApi;
    public final WebApiWrapper<AccelerationReading> AccelerationsApi;
    public final WebApiWrapper<GpsReading> GpsApi;
    public final WebApiWrapper<HeartRateReading> HeartRateApi;
    private final String EndPoint;


    public DaoManager(Context context) {
        EndPoint = context.getString(R.string.EndPoint);
        UsersApi = new WebApiWrapper<User>(EndPoint + "/Users", context,User.class);
        AccelerationsApi = new WebApiWrapper<AccelerationReading>(EndPoint + "/Accelerations", context,AccelerationReading.class);
        GpsApi = new WebApiWrapper<GpsReading>(EndPoint + "/GeoLocations", context,GpsReading.class);
        HeartRateApi = new WebApiWrapper<HeartRateReading>(EndPoint + "/HeartRates", context,HeartRateReading.class);
    }
}