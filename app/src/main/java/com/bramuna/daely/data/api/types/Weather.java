package com.bramuna.daely.data.api.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    @Expose
    @SerializedName("consolidated_weather")
    private List<Forecast> forecasts;

    @Expose
    @SerializedName("title")
    private String locationName;

    private class Forecast {

        @Expose
        @SerializedName("weather_state_name")
        private String weatherState;

        @Expose
        @SerializedName("created")
        private String createdAt;

        @Expose
        @SerializedName("applicable_date")
        private String applicableDate;

        @Expose
        @SerializedName("min_temp")
        private double minTemp;

        @Expose
        @SerializedName("max_temp")
        private double maxTemp;

        @Expose
        @SerializedName("the_temp")
        private double currentTemp;

        @Expose
        @SerializedName("humidity")
        private int humidity;

    }

    public String getWeatherState() {
        Forecast forecast = forecasts.get(0);
        return forecast.weatherState;
    }

    public double getMinTemp() {
        Forecast forecast = forecasts.get(0);
        return forecast.minTemp;
    }

    public double getMaxTemp() {
        Forecast forecast = forecasts.get(0);
        return forecast.maxTemp;
    }

    public double getCurrentTemp() {
        Forecast forecast = forecasts.get(0);
        return forecast.currentTemp;
    }

    public int getHumidity() {
        Forecast forecast = forecasts.get(0);
        return forecast.humidity;
    }

    public String getLocationName() {
        return locationName;
    }
}
