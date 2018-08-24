package com.bramuna.daely.data.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("data")
    @Expose
    private DataHolder data;

    private class DataHolder {
        @SerializedName("Events")
        @Expose
        private List<Event> events;

        @SerializedName("Births")
        @Expose
        private List<Event> births;

        @SerializedName("Deaths")
        @Expose
        private List<Event> deaths;
    }

    public class Event {
        @SerializedName("year")
        @Expose
        private String year;

        @SerializedName("text")
        @Expose
        private String text;

        public String getYear() {
            return year;
        }

        public String getText() {
            return text;
        }
    }

    public String getDate() {
        return date;
    }

    public List<Event> getTwoRandomEvents() {
        List<Event> allEvents = new ArrayList<>(data.events);
//        allEvents.addAll(data.births);
//        allEvents.addAll(data.deaths);
        Collections.shuffle(allEvents);
        return new ArrayList<>(allEvents.subList(0, 2));
    }
}
