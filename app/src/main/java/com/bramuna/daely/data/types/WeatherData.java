package com.bramuna.daely.data.types;

public class WeatherData {

    private String locationName;
    private String weatherState;
    private int weatherCode;
    private long lastFetchedAt;
    private int minTemp;
    private int maxTemp;
    private int currentTemp;

    private WeatherData() {
    }

    public static WeatherData from(Weather weather) {
        WeatherData weatherData = new WeatherData();
        weatherData.locationName = weather.getLocationName();
        weatherData.weatherState = weather.getWeatherState();
        weatherData.weatherCode = weather.getWeatherCode();
        weatherData.lastFetchedAt = System.currentTimeMillis();
        weatherData.minTemp = weather.getMinTemp();
        weatherData.maxTemp = weather.getMaxTemp();
        weatherData.currentTemp = weather.getCurrentTemp();
        return weatherData;
    }


    public String getLocationName() {
        return locationName;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public long getLastFetchedAt() {
        return lastFetchedAt;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }
}
