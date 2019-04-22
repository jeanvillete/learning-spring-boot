package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    MessageSource messageSource;

//    @GetMapping( "hello-world-internationalized" )
//    public String helloWorldInternationalization(
//        @RequestHeader( name = "Accept-Language", required = false ) Locale locale
//    ) {
//        return this.messageSource.getMessage( "good.morning.message", null, locale );
//    }

    @GetMapping( "hello-world-internationalized" )
    public String helloWorldInternationalization() {
        return this.messageSource.getMessage( "good.morning.message", null, LocaleContextHolder.getLocale() );
    }

}
