package com.bramuna.daely.data;

import com.bramuna.daely.data.api.types.History;

import java.util.List;

public class HistoryData {

    private String date;
    private String eventOneYear;
    private String eventOneText;
    private String eventTwoYear;
    private String eventTwoText;

    private HistoryData() {
    }

    public static HistoryData from(History history) {
        HistoryData historyData = new HistoryData();
        historyData.date = history.getDate();
        List<History.Event> events = history.getTwoRandomEvents();
        historyData.eventOneYear = events.get(0).getYear();
        historyData.eventOneText = events.get(0).getText();
        historyData.eventTwoYear = events.get(1).getYear();
        historyData.eventTwoText = events.get(1).getText();
        return historyData;
    }

    public String getDate() {
        return date;
    }

    public String getEventOneYear() {
        return eventOneYear;
    }

    public String getEventOneText() {
        return eventOneText;
    }

    public String getEventTwoYear() {
        return eventTwoYear;
    }

    public String getEventTwoText() {
        return eventTwoText;
    }

}
