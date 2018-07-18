package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;

public interface SampleCollector {

    List<Sample> collect(String exchangeFrom, String exchangeTo) throws InterruptedException, ExecutionException;

}
