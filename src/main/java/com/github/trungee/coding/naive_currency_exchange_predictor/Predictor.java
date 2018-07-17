package com.github.trungee.coding.naive_currency_exchange_predictor;

import java.math.BigDecimal;
import java.util.List;

public class Predictor {

    private List<BigDecimal> sampleRates;
    
    public Predictor(List<BigDecimal> sampleRates) {
        this.sampleRates = sampleRates;
    }
    
    public BigDecimal predict() {
        try {
        	System.out.print(sampleRates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
