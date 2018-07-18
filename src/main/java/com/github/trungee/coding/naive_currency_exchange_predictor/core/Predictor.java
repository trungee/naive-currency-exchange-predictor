package com.github.trungee.coding.naive_currency_exchange_predictor.core;

import java.math.BigDecimal;
import java.util.List;

public class Predictor {

    private List<Sample> sampleRates;

    public Predictor(List<Sample> sampleRates) {
        this.sampleRates = sampleRates;
    }

    public BigDecimal predict() {
        try {
            System.out.println(sampleRates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
