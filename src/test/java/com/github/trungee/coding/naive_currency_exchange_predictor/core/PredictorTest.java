package com.github.trungee.coding.naive_currency_exchange_predictor.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;



public class PredictorTest {

    private Predictor predictor;
    private List<Sample> initSample() {
        List<Sample> samples = new ArrayList<>(12);
        samples.add(new Sample(getSampleDate(1), new BigDecimal("3.04359")));
        samples.add(new Sample(getSampleDate(2), new BigDecimal("2.945926")));
        samples.add(new Sample(getSampleDate(3), new BigDecimal("2.893866")));
        samples.add(new Sample(getSampleDate(4), new BigDecimal("2.854589")));
        samples.add(new Sample(getSampleDate(5), new BigDecimal("2.970913")));
        samples.add(new Sample(getSampleDate(6), new BigDecimal("2.925991")));
        samples.add(new Sample(getSampleDate(7), new BigDecimal("2.990882")));
        samples.add(new Sample(getSampleDate(8), new BigDecimal("2.944941")));
        samples.add(new Sample(getSampleDate(9), new BigDecimal("2.970704")));
        samples.add(new Sample(getSampleDate(10), new BigDecimal("3.089218")));
        samples.add(new Sample(getSampleDate(11), new BigDecimal("3.284672")));
        samples.add(new Sample(getSampleDate(12), new BigDecimal("3.503773")));
        return samples;
    }
    
    private LocalDate getSampleDate(int month) {
        return LocalDate.of(2005, month, 15);
    }
    
    @Test
    public void predictorTest() {
        predictor = new Predictor(initSample());
        Assert.assertEquals(new BigDecimal("3.104668"), predictor.predict(Month.JANUARY.getValue()));
        Assert.assertEquals(new BigDecimal("3.091987"), predictor.predict(Month.FEBRUARY.getValue()));
        Assert.assertEquals(new BigDecimal("3.079306"), predictor.predict(Month.MARCH.getValue()));
    }
    
    @Test
    public void giventExchangeRatesKeepConstant_whenPredictForFuture_thenResultMustCorrect100Percent() {
        List<Sample> samples = new ArrayList<>(12);
        final String EXCHANGE_RATE_USD_TO_VND = "23.7899";
        samples.add(new Sample(getSampleDate(1), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(2), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(3), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(4), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(5), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(6), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(7), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(8), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(9), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(10), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(11), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        samples.add(new Sample(getSampleDate(12), new BigDecimal(EXCHANGE_RATE_USD_TO_VND)));
        predictor = new Predictor(samples);
        assertThat(0, equalTo(new BigDecimal("23.7899").compareTo(predictor.predict(Month.JANUARY.getValue()))));
        assertThat(0, equalTo(new BigDecimal("23.7899").compareTo(predictor.predict(Month.JUNE.getValue()))));
        assertThat(0, equalTo(new BigDecimal("23.7899").compareTo(predictor.predict(Month.SEPTEMBER.getValue()))));
    }
}
