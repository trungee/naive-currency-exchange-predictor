package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.trungee.coding.naive_currency_exchange_predictor.task.GetExcangeRateTask;

@Component
public class NaiveSampleCollector {

	private ExchangeRatesService exchangeRatesService;
	
	public NaiveSampleCollector(@Autowired ExchangeRatesService exchangeRatesService) {
		this.exchangeRatesService = exchangeRatesService;
	}
	
	public List<BigDecimal> collect(String exchangeFrom, String exchangeTo) throws InterruptedException, ExecutionException {
		List<BigDecimal> exchangeRates = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(6);
		List<Future<BigDecimal>> futures = new ArrayList<Future<BigDecimal>>(12);
		for (int month = 1; month <= 12; month++) {
			LocalDate date = getSampleDate(month);
			try {
				executorService.submit(new GetExcangeRateTask(exchangeRatesService, exchangeFrom, exchangeTo, date));
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e);
				System.out.println(e.getMessage());
			}
		}
		for(Future<BigDecimal> future : futures) {
			exchangeRates.add(future.get()); // get will block until the future is done
		}
		executorService.shutdown();
		return exchangeRates;
	}
	
	public LocalDate getSampleDate(int month) {
		return LocalDate.of(2016, month, 15);
	}
}
