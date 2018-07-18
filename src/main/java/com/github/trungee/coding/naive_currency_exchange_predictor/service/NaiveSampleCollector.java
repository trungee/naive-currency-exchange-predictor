package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
import com.github.trungee.coding.naive_currency_exchange_predictor.task.GetExcangeRateTask;

@Component
public class NaiveSampleCollector implements SampleCollector{

    private ExchangeRatesService exchangeRatesService;

    public NaiveSampleCollector(@Autowired ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @Override
    public List<Sample> collect(String exchangeFrom, String exchangeTo) throws InterruptedException, ExecutionException {
        List<Sample> samples = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List<Future<Sample>> futures = new ArrayList<Future<Sample>>(12);
        for (int month = 1; month <= 12; month++) {
            LocalDate date = getSampleDate(month);
            try {
                Future<Sample> future = executorService
                        .submit(new GetExcangeRateTask(exchangeRatesService, exchangeFrom, exchangeTo, date));
                futures.add(future);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Future<Sample> future : futures) {
            samples.add(future.get()); // get will block until the future
                                             // is done
        }
        executorService.shutdown();
        return samples;
    }

    public LocalDate getSampleDate(int month) {
        return LocalDate.of(2016, month, 15);
    }
}
