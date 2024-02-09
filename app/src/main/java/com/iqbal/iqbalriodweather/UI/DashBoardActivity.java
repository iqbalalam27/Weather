package com.iqbal.iqbalriodweather.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.iqbal.iqbalriodweather.Model.WeatherDetails;
import com.iqbal.iqbalriodweather.Model.WeatherViewModel;

import com.iqbal.iqbalriodweather.R;
import com.iqbal.iqbalriodweather.databinding.ActivityDashBoardBinding;
public class DashBoardActivity extends AppCompatActivity {

    private ActivityDashBoardBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    private WeatherViewModel weatherViewModel;
    private String apiKey ="27cb1506a399ab8295f4c25ac8113b95";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        SearchMethod();

    }

    @Override
    protected void onStart() {
        super.onStart();
        location();
    }

    private void SearchMethod(){

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Show progress bar while typing
                binding.progressCircular.setVisibility(View.VISIBLE);
                binding.mainLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString();

                // Check if the search text is empty
                if (searchText.isEmpty()) {
                    // Hide progress bar and any other views
                    location();
                } else {
                    // Perform the weather search
                    weatherViewModel.getWeather(searchText, apiKey).observeForever(new Observer<WeatherDetails>() {
                        @Override
                        public void onChanged(WeatherDetails weatherDetails) {
                            // Hide progress bar after receiving the result
                            binding.progressCircular.setVisibility(View.INVISIBLE);

                            if (weatherDetails != null) {
                                // Show main layout and update UI
                                binding.mainLayout.setVisibility(View.VISIBLE);
                                updateUI(weatherDetails);
                            } else {
                                // Show error view
                                binding.mainLayout.setVisibility(View.INVISIBLE);
                                binding.error.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });

    }


    private void location(){
        binding.progressCircular.setIndeterminate(true);
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.mainLayout.setVisibility(View.INVISIBLE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            getLocation();
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // Handle permission denial
            }
        }
    }

    private void getLocation() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            weatherViewModel.getWeatherDataByPhoneLocation(longitude,latitude,apiKey).observeForever(new Observer<WeatherDetails>() {
                                @Override
                                public void onChanged(WeatherDetails weatherDetails) {
                                    if(weatherDetails!=null){
                                        updateUI(weatherDetails);
                                    }else{
                                        binding.progressCircular.setVisibility(View.GONE);
                                        binding.mainLayout.setVisibility(View.INVISIBLE);
                                        binding.error.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            // Use latitude and longitude as needed
                        }
                    })
                    .addOnFailureListener(this, e -> {
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.mainLayout.setVisibility(View.INVISIBLE);
                        binding.error.setVisibility(View.VISIBLE);
                        // Handle failure to get location
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(WeatherDetails weatherDetails) {
           binding.progressCircular.setVisibility(View.GONE);
           binding.mainLayout.setVisibility(View.VISIBLE);
           String temp = String.valueOf(convertCelsius(weatherDetails.getMain().getTemp())) + "°";
           String weather_type = weatherDetails.getWeatherList().get(0).getMain();
           @SuppressLint("DefaultLocale") String wind_speed = String.format("%.1f",weatherDetails.getWind().getSpeed());
           float visibility = convertMetreToKm(weatherDetails.getVisibility());
           @SuppressLint("DefaultLocale") String vis = String.format("%.1f",visibility);
           String humidity = weatherDetails.getMain().getHumidity()+"%";
           String name = weatherDetails.getName();
           int drawableResId = getWeatherIconResourceId(weather_type);


           binding.temp.setText(temp);
           binding.weatherType.setText(weather_type);
           binding.windSpeed.setText(wind_speed);
           binding.humidity.setText(humidity);
           binding.visibility.setText(vis);
           binding.cityName.setText(name);
           binding.weatherIcon.setImageResource(drawableResId);

    }

    private int getWeatherIconResourceId(String weather_type) {
        // Map weather condition to drawable resource ID
       String weatherCondition= weather_type.toLowerCase();
        switch (weatherCondition) {
            case "clear":

                return R.drawable.sunny; // Replace with your sunny weather icon
            case "rainy":
                return R.drawable.rainy; // Replace with your rainy weather icon
            case "cloudy":
                return R.drawable.cloudy; // Replace with your cloudy weather icon
            case "haze":
                return R.drawable.haze;
            default:
                return R.drawable.cloudy; // Replace with a default icon or handle unknown conditions
        }
    }

    public float convertMetreToKm(int metre){
        float km = metre/ 1000;
        return km;
    }

    private int convertCelsius(float temp){
        return (int) (temp);
    }



}