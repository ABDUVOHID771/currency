package com.example.currencyexchangeservice.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class CurrencyExchangeController {

    private final ExchangeValueRepository exchangeValueRepository;

    public CurrencyExchangeController(ExchangeValueRepository exchangeValueRepository) {
        this.exchangeValueRepository = exchangeValueRepository;
    }


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to,
                                               @RequestHeader Map<String, String> headers) {

        printAllHeaders(headers);

        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);

        log.info("!!!!{} {} {}", from, to, exchangeValue);

        if (exchangeValue == null) {
            throw new RuntimeException("Unable to find data to convert " + from + " to " + to);
        }

        return exchangeValue;
    }

    private void printAllHeaders(Map<String, String> headers) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
    }

}
