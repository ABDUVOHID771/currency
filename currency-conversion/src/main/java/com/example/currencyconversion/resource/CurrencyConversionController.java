package com.example.currencyconversion.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class CurrencyConversionController {

    private final CurrencyExchangeServiceProxy proxy;
    private final HystrixBreaker hystrixBreaker;

    public CurrencyConversionController(CurrencyExchangeServiceProxy proxy, HystrixBreaker hystrixBreaker) {
        this.proxy = proxy;
        this.hystrixBreaker = hystrixBreaker;
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
                                                  @PathVariable BigDecimal quantity) {

        log.info("Received Request to convert from {} {} to {}. ", quantity, from, to);

        CurrencyConversionBean response = hystrixBreaker.getExchange(from, to, quantity);
        BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple());
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                convertedValue);
    }

}
