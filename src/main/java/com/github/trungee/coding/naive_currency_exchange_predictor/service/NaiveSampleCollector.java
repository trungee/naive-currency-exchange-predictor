package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
import com.github.trungee.coding.naive_currency_exchange_predictor.task.GetExcangeRateTask;

@Service
public class NaiveSampleCollector implements SampleCollector{

    private static final int DEAULT_SAMPLE_DATE = 15;
    private static final int DEFAULT_SAMPLE_YEAR = 2016;
    private static final int MONTH_START = Month.JANUARY.getValue();
    private static final int MONTH_END = Month.DECEMBER.getValue();
    private static final int NUMBER_OF_THREAD = 6;
    private ExchangeRatesService exchangeRatesService;

    public NaiveSampleCollector(@Autowired @Qualifier("openExchangeRatesService") ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @Override
    public List<Sample> collect(String exchangeFrom, String exchangeTo) {
        List<Sample> samples = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        int numberOfMonths = MONTH_END - MONTH_START + 1;
        List<Future<Sample>> futures = new ArrayList<Future<Sample>>(numberOfMonths);
        for (int month = MONTH_START; month <= MONTH_END; month++) {
            LocalDate date = getSampleDate(month);
            try {
                Future<Sample> future = executorService
                        .submit(new GetExcangeRateTask(exchangeRatesService, exchangeFrom, exchangeTo, date));
                futures.add(future);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("Collecting samples for %d months...", numberOfMonths));
        int numberOfFailedSamples = 0;
        for (Future<Sample> future : futures) {
            // get will block until the future is done
            try {
                samples.add(future.get());
                System.out.println(future.get()); 
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
                numberOfFailedSamples++;
            }
        }
        if (isThereFailedSample(numberOfFailedSamples)) {
            System.err.println(String.format("There are %d failed sample(s) can't be collected.", numberOfFailedSamples));
        }
        executorService.shutdown();
        return samples;
    }

    private boolean isThereFailedSample(int numberOfFailedSamples) {
        return numberOfFailedSamples > 0;
    }

    public LocalDate getSampleDate(int month) {
        return LocalDate.of(DEFAULT_SAMPLE_YEAR, month, DEAULT_SAMPLE_DATE);
    }
}
