package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;

public class NaiveSampleCollectorTest {

    private NaiveSampleCollector naiveSampleCollector;
    private ExchangeRatesService exchangeRateService;
    
    @Before
    public void initialize() throws Exception {
        exchangeRateService = Mockito.mock(ExchangeRatesService.class);
        mockExchangeRatesRespones();
        naiveSampleCollector = new NaiveSampleCollector(exchangeRateService);
    }

    private void mockExchangeRatesRespones() throws Exception {
        Mockito.when(exchangeRateService.getHistoricalExchangesRate("USD", "VND", LocalDate.of(2016, 1, 15)))
        .thenReturn(new Sample(LocalDate.of(2016, 1, 15), new BigDecimal("123")));
        Mockito.when(exchangeRateService.getHistoricalExchangesRate("USD", "VND", LocalDate.of(2016, 2, 15)))
        .thenReturn(new Sample(LocalDate.of(2016, 2, 15), new BigDecimal("234")));
        Mockito.when(exchangeRateService.getHistoricalExchangesRate("USD", "VND", LocalDate.of(2016, 3, 15)))
        .thenReturn(new Sample(LocalDate.of(2016, 3, 15), new BigDecimal("345")));
        Mockito.when(exchangeRateService.getHistoricalExchangesRate("USD", "VND", LocalDate.of(2016, 4, 15)))
        .thenReturn(new Sample(LocalDate.of(2016, 4, 15), new BigDecimal("456")));
        Mockito.when(exchangeRateService.getHistoricalExchangesRate("USD", "VND", LocalDate.of(2016, 5, 15)))
        .thenReturn(new Sample(LocalDate.of(2016, 5, 15), new BigDecimal("567")));
    }
    
    @Test
    public void shouldCollectSampleFromGiventExchangeRatesService() {
        List<Sample> samples = naiveSampleCollector.collect("USD", "VND");
        
        assertThat(samples.get(0).getDate(), equalTo(LocalDate.of(2016, 1, 15)));
        assertThat(new BigDecimal("123").compareTo(samples.get(0).getExchangeRate()), equalTo(0));
        
        assertThat(samples.get(4).getDate(), equalTo(LocalDate.of(2016, 5, 15)));
        assertThat(new BigDecimal("567").compareTo(samples.get(4).getExchangeRate()), equalTo(0));
    }
    
}
