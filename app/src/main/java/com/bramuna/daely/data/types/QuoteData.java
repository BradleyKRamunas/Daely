package com.bramuna.daely.data.types;

public class QuoteData {

    public static final QuoteData mockData = new QuoteData();

    private String quote;
    private String author;
    private String QODDate;

    static {
        mockData.quote = "It is often times better to mock services rather than repeatedly rely on them when testing.";
        mockData.author = "John Doe";
        mockData.QODDate = "Date";
    }

    private QuoteData() {
    }

    public static QuoteData from(TheySaidSo theySaidSo) {
        QuoteData quoteData = new QuoteData();
        quoteData.quote = theySaidSo.getQuoteText();
        quoteData.author = theySaidSo.getQuoteAuthor();
        quoteData.QODDate = theySaidSo.getQODDate();
        return quoteData;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getQODDate() {
        return QODDate;
    }
}
