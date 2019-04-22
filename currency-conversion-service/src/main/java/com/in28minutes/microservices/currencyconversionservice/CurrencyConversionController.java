package com.in28minutes.microservices.currencyconversionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    private static final Logger LOGGER = LoggerFactory.getLogger( CurrencyConversionController.class );

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @Autowired
    private Environment environment;

    @GetMapping( "/currency-converter/from/{from}/to/{to}/quantity/{quantity}" )
    public CurrencyConversionBean convertCurrencty(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity
    ){
        CurrencyConversionBean response = new RestTemplate().getForEntity(
            "http://localhost:8000/currency-exchange/from/{from}/to/{to}/",
            CurrencyConversionBean.class,
            new HashMap<String, String>() {{
                put("from", from);
                put("to", to);
            }}
        )
        .getBody();

        return new CurrencyConversionBean(
            response.getId(),
            from,
            to,
            response.getConversionMultiple(),
            quantity,
            quantity.multiply( response.getConversionMultiple() )
        )
        .setPort(
            Integer.parseInt(
                environment.getProperty( "local.server.port" )
            )
        );
    }

    @GetMapping( "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}" )
    public CurrencyConversionBean convertCurrenctyFeign(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity
    ){
        CurrencyConversionBean response = this.currencyExchangeProxy.retrieveExchangeValue( from, to );

        LOGGER.info( "currency exchange response -> {}", response );

        return new CurrencyConversionBean(
            response.getId(),
            from,
            to,
            response.getConversionMultiple(),
            quantity,
            quantity.multiply( response.getConversionMultiple() )
        )
        .setPortCurrencyExchangeService( response.getPort() )
        .setPort(
            Integer.parseInt(
                environment.getProperty( "local.server.port" )
            )
        );
    }


}
