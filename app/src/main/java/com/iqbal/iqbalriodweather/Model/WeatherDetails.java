package com.iqbal.iqbalriodweather.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetails {

    @SerializedName("weather")
    private List<Weather> weatherList;

    @SerializedName("main")
    private Main main;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("name")
    private String name;

    public WeatherDetails() {
    }

    public WeatherDetails(List<Weather> weatherList, Main main, int visibility, Wind wind, String name) {
        this.weatherList = weatherList;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
