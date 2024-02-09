package com.iqbal.iqbalriodweather.Model;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private float speed;

    public Wind() {
    }

    public Wind(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
