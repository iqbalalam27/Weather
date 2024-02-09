package com.iqbal.iqbalriodweather.Model;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private float temp;

    @SerializedName("feels_like")
    private float feels_like;

    @SerializedName("humidity")
    private int humidity;

    public Main() {
    }

    public Main(float temp, float feels_like, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
