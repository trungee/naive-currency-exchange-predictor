package com.github.trungee.coding.naive_currency_exchange_predictor.core;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Sample {

    private LocalDate date;
    private BigDecimal exchangeRate;

    public Sample(LocalDate date, BigDecimal exchangeRate) {
        this.date = date;
        this.exchangeRate = exchangeRate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Sample [date=" + date + ", exchangeRate=" + exchangeRate + "]";
    }
}
