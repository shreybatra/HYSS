package com.hyss.vihaanuser.Pojos;


/**
 * Created by yash on 27/10/17.
 */

public class Sos {

    public String type;
    public String username;
    public String password;
    public Double latitude;
    public Double longitude;
    public String time;

    public Sos(String user,String pass)
    {
        this.username = user;
        this.password = pass;
        long a = System.currentTimeMillis()/1000;
        this.time = a+"";

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
