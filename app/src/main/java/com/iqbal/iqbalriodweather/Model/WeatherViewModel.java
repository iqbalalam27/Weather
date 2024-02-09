package com.iqbal.iqbalriodweather.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iqbal.iqbalriodweather.Repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository repository;
    private MutableLiveData<WeatherDetails> weatherData;

    public WeatherViewModel() {
        repository = new WeatherRepository();
        weatherData = new MutableLiveData<>();
    }

    public LiveData<WeatherDetails> getWeather(String city, String apiKey) {
        repository.getWeather(city, apiKey).observeForever(weatherDetails -> weatherData.setValue(weatherDetails));
        return weatherData;
    }
    public  LiveData<WeatherDetails> getWeatherDataByPhoneLocation(double longitude,double latitude,String apiKey){
           repository.getWeatherDataByPhoneLocation(latitude,longitude,apiKey).observeForever(weatherDetails -> weatherData.setValue(weatherDetails));
           return weatherData;
    }

    public LiveData<WeatherDetails> getWeatherData() {
        return weatherData;
    }
}
