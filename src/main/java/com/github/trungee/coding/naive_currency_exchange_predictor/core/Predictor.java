package com.github.trungee.coding.naive_currency_exchange_predictor.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Predictor {

    private List<Sample> sampleRates;
    private long sigmaX;
    private BigDecimal slopeB;

    public Predictor(List<Sample> sampleRates) {
        this.sampleRates = sampleRates;
    }

    public BigDecimal predict(int x) {
        return findRegressionEquationY(x);
    }
    
    private long findN() {
        return sampleRates.size();
    }
    
    private long findSigmaX() {
        if (sigmaX == 0) {
            sigmaX = sampleRates.stream()
                    .mapToLong(sample -> sample.getDate().getMonthValue())
                    .sum();
        } 
        return sigmaX;
    }
    
    private BigDecimal findSigmaY() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Sample sample : sampleRates) {
            sum = sum.add(sample.getExchangeRate());
        }
        return sum;
    }
    
    private BigDecimal findSigmaXY() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Sample sample : sampleRates) {
            sum = sum.add(sample.getExchangeRate().multiply(new BigDecimal(sample.getDate().getMonthValue())));
        }
        return sum;
    }
    
    private BigDecimal findSigmaXsquare() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Sample sample : sampleRates) {
            sum = sum.add(sample.getExchangeRate().pow(2));
        }
        return sum;
    }
    
    private BigDecimal findNSigmaXY() {
        return new BigDecimal(findN()).multiply(findSigmaXY()); 
    }
    
    /**
     * NΣX^2
     */
    private BigDecimal findNSigmaXSquare() {
        return new BigDecimal(findN()).multiply(findSigmaXsquare());
    }
    
    /**
     * (ΣX)^2
     */
    private BigDecimal findSquareOfSigmaX() {
        return new BigDecimal(findSigmaX()).pow(2);
    }
    
    /**
     *                  upper              lower
     * Slope(b) = (NΣXY - (ΣX)(ΣY)) / (NΣX^2 - (ΣX)^2)
     */
    private BigDecimal findSlopeB() {
        if (slopeB == null) { 
            BigDecimal upper = findNSigmaXY().subtract(new BigDecimal(findSigmaX()).multiply(findSigmaY()));
            slopeB = upper.divide(findNSigmaXSquare().subtract(findSquareOfSigmaX()), 5, RoundingMode.HALF_DOWN);
        } 
        return slopeB;
    }
    
    /**
     * Intercept(a) = (ΣY - b(ΣX)) / N 
     */
    private BigDecimal fidnInterceptA() {
        return findSlopeB().multiply(new BigDecimal(findSigmaX())).divide(new BigDecimal(findN()));
    }
    
    private BigDecimal findRegressionEquationY(int x) {
        return fidnInterceptA().add(findSlopeB().multiply(new BigDecimal(x)));
    }
}
