package com.bramuna.daely.data.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TheySaidSo {

    @SerializedName("contents")
    @Expose
    private QuoteContainer quoteContainer;

    private class QuoteContainer {
        @SerializedName("quotes")
        @Expose
        private List<Quote> quotes;
    }

    private class Quote {
        @SerializedName("quote")
        @Expose
        private String quoteText;

        @SerializedName("author")
        @Expose
        private String quoteAuthor;

        @SerializedName("date")
        @Expose
        private String date;
    }

    public String getQuoteText() {
        return quoteContainer.quotes.get(0).quoteText;
    }

    public String getQuoteAuthor() {
        return quoteContainer.quotes.get(0).quoteAuthor;
    }

    public String getQODDate() {
        return quoteContainer.quotes.get(0).date;
    }
}
