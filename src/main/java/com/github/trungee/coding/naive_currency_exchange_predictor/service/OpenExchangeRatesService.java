package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class OpenExchangeRatesService implements ExchangeRatesService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private DateTimeFormatter dateTimeFormatter;
    @Value("${openexchangerates.api_base_path}")
    private String apiBasePath;
    private String historicalRoutePath = "historical/{date}.json";
    @Value("${openexchangerates.app_id}")
    private String appId;

    public OpenExchangeRatesService() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    @Override
    public Sample getHistoricalExchangesRate(String from, String to, LocalDate date) throws UnirestException {
        String formatedDate = dateTimeFormatter.format(date);
        HttpResponse<JsonNode> response = Unirest.get(apiBasePath.concat(historicalRoutePath)).routeParam("date", formatedDate)
                .queryString("symbols", to).queryString("app_id", appId).asJson();
        BigDecimal exchangeRate = response.getBody().getObject().getJSONObject("rates").getBigDecimal(to);
        return new Sample(date, exchangeRate);
    }

}
