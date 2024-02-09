package com.iqbal.iqbalriodweather.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.iqbal.iqbalriodweather.Model.WeatherDetails;
import com.iqbal.iqbalriodweather.Model.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    private WeatherApi weatherApi;

    public WeatherRepository() {
        // Initialize Retrofit instance and create WeatherApi
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
    }

    public LiveData<WeatherDetails> getWeather(String city, String apiKey) {
        MutableLiveData<WeatherDetails> weatherData = new MutableLiveData<>();

        weatherApi.getWeather(city,apiKey,"metric").enqueue(new Callback<WeatherDetails>() {
            @Override
            public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                if (response.isSuccessful()) {
                    weatherData.setValue(response.body());
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<WeatherDetails> call, Throwable t) {
                // Handle failure

            }
        });

        return weatherData;
    }

    public LiveData<WeatherDetails> getWeatherDataByPhoneLocation(double latitude, double longitude, String apiKey) {
        MutableLiveData<WeatherDetails> weatherData = new MutableLiveData<>();
        weatherApi.getWeatherByPhoneLocation(latitude, longitude, apiKey, "metric").enqueue(new Callback<WeatherDetails>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDetails> call, @NonNull Response<WeatherDetails> response) {
                if (response.isSuccessful()) {
                    weatherData.setValue(response.body());

                } else {
                    // Handle error
                    weatherData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDetails> call, Throwable t) {
                // Handle failure
            }
        });
        return weatherData;
    }

}

