package com.bramuna.daely.data;

import com.bramuna.daely.data.api.types.TheySaidSo;

public class QuoteData {

    private String quote;
    private String author;
    private String QODDate;

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
