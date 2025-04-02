package com.example.FireFly_backend.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeService {

    private final String API_URL = "https://api.exchangerate-api.com/v4/latest/EUR";

    public double getEurToTryRate() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        return rates.get("TRY");
    }

}
