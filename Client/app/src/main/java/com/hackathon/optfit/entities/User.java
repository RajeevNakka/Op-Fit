package com.hackathon.optfit.entities;

import java.util.List;

public class User {

    public Integer id;
    public String timeStamp;
    public String userName;
    public String password;
    public String firstName;
    public String lastName;
    public Integer age;
    public Integer gender;
    public String deviceId;
    public boolean isAcknowledged;
    public boolean isWearingHelmet;
    public List<AccelerationReading> accelerationReadings = null;
    public List<HeartRateReading> heartRateReadings = null;
    public List<GpsReading> gpsReadings = null;

}