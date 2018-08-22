package com.bramuna.daely.data;

import com.bramuna.daely.data.api.types.Weather;

public class WeatherData {

    private String locationName;
    private String weatherState;
    private long lastFetchedAt;
    private double minTemp;
    private double maxTemp;
    private double currentTemp;
    private int humidity;

    private WeatherData() {
    }

    public static WeatherData from(Weather weather) {
        WeatherData weatherData = new WeatherData();
        weatherData.locationName = weather.getLocationName();
        weatherData.weatherState = weather.getWeatherState();
        weatherData.lastFetchedAt = System.currentTimeMillis();
        weatherData.minTemp = convertCelsiusToFahrenheit(weather.getMinTemp());
        weatherData.maxTemp = convertCelsiusToFahrenheit(weather.getMaxTemp());
        weatherData.currentTemp = convertCelsiusToFahrenheit(weather.getCurrentTemp());
        weatherData.humidity = weather.getHumidity();
        return weatherData;
    }

    private static double convertCelsiusToFahrenheit(double celsius) {
        return (celsius * 1.8) + 32;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public long getLastFetchedAt() {
        return lastFetchedAt;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public int getHumidity() {
        return humidity;
    }
}
