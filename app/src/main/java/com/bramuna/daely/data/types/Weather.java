package com.bramuna.daely.data.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("query")
    private ResultContainer resultContainer;

    private class ResultContainer {

        @SerializedName("results")
        private DataContainer dataContainer;

        private class DataContainer {

            @SerializedName("channel")
            private Forecast locationForecast;

            private class Forecast {

                @SerializedName("link")
                private String link; // doesn't work

                @SerializedName("location")
                private Location location;

                @SerializedName("item")
                private Item weatherConditions;

                private class Location {
                    @SerializedName("city")
                    private String cityName;
                }

                private class Item {

                    @SerializedName("condition")
                    private CurrentConditions currentConditions;

                    @SerializedName("forecast")
                    private List<WeatherConditions> forecasts;


                    private class CurrentConditions {
                        @SerializedName("code")
                        private int weatherCode;

                        @SerializedName("temp")
                        private int currentTemp;

                        @SerializedName("text")
                        private String weatherText;
                    }

                    private class WeatherConditions {

                        @SerializedName("high")
                        private int highTemp;

                        @SerializedName("low")
                        private int lowTemp;
                    }
                }


            }

        }
    }

    public String getWeatherState() {
        return resultContainer.dataContainer.locationForecast.weatherConditions.currentConditions.weatherText;
    }

    public int getWeatherCode() {
        return resultContainer.dataContainer.locationForecast.weatherConditions.currentConditions.weatherCode;
    }

    public int getMinTemp() {
        return resultContainer.dataContainer.locationForecast.weatherConditions.forecasts.get(0).lowTemp;
    }

    public int getMaxTemp() {
        return resultContainer.dataContainer.locationForecast.weatherConditions.forecasts.get(0).highTemp;
    }

    public int getCurrentTemp() {
        return resultContainer.dataContainer.locationForecast.weatherConditions.currentConditions.currentTemp;
    }

    public String getLocationName() {
        return resultContainer.dataContainer.locationForecast.location.cityName;
    }
}
