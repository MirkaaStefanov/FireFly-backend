package com.example.FireFly_backend.services.impl;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeService {

    private final String API_URL = "https://api.exchangerate-api.com/v4/latest/EUR";
    private static final String URL = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=EUR&To=TRY";
    private final ChromeDriver driver;

    public double getEurToTryRate() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        return rates.get("TRY");
    }

    public Double scrapeEuroToTryRate() {
        String exchangeRate = "";
        try {
            System.setProperty("webdriver.chrome.driver", "http://www.google.com/");
            driver.get(URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement rateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.sc-294d8168-1")));

            // Extract the main rate (e.g., 41.08)
            String mainRate = rateElement.getText().split(" ")[0];

            // Extract the decimal part if available
            WebElement fadedDigits = rateElement.findElement(By.cssSelector("span.faded-digits"));
            String decimalPart = fadedDigits.getText();

            // Combine the full exchange rate
            exchangeRate = mainRate + decimalPart;

            System.out.println("Exchange Rate: " + exchangeRate + " TRY per EUR");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
        return Double.parseDouble(exchangeRate);
    }

}
