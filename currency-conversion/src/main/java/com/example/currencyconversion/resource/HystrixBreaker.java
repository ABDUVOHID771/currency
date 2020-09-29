package com.example.currencyconversion.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@Slf4j
@Service
public class HystrixBreaker {
    private final CurrencyExchangeServiceProxy proxy;

    public HystrixBreaker(CurrencyExchangeServiceProxy proxy) {
        this.proxy = proxy;
    }

    @HystrixCommand(fallbackMethod = "getFallbackExchange",
            threadPoolKey = "currencyExchange",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "15")
            }
            ,
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public CurrencyConversionBean getExchange(@PathVariable String from, @PathVariable String to,
                                              @PathVariable BigDecimal quantity) {
        return proxy.retrieveExchangeValue(from, to);
    }

    private CurrencyConversionBean getFallbackExchange(@PathVariable String from, @PathVariable String to,
                                                       @PathVariable BigDecimal quantity) {
        log.error("!!!!!!!!!!!! ERROR INSIDE CALLBACK : {} :: {} :: {} !!!!!!!!!!!!", from, to, quantity);
        return new CurrencyConversionBean(null, null, null, BigDecimal.valueOf(0.0), null, null);
    }

}
