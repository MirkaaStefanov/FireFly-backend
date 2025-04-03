package com.example.FireFly_backend.config;


import jakarta.annotation.PreDestroy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfig {

//    private ChromeDriver driver;
//
//    @Bean
//    public ChromeDriver driver() {
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
//        this.driver = new ChromeDriver(chromeOptions);
//        return driver;
//    }
//
//    @PreDestroy
//    public void closeWebDriver() {
//        if (driver != null) {
//            driver.quit();
//            System.out.println("ChromeDriver closed on application shutdown.");
//        }
//    }

}
