package com.example.currencyconversion.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionBean {

    private Long id;

    private String from;

    private String to;

    private BigDecimal conversionMultiple;

    private BigDecimal quantity;

    private BigDecimal totalCalculatedAmount;

}
