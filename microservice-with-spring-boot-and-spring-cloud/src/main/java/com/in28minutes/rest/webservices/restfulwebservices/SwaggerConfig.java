package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket( DocumentationType.SWAGGER_2 )
            .apiInfo(
                new ApiInfo(
                    "Awesome API title",
                    "Awesome API description",
                    "1.0",
                    "urn:tos",
                    new Contact("Jean Villete", "", "jean.villete@gmail.com"),
                    "Apache 2.0",
                    "http://www.apache.org/licenses/LICENSE-2.0"
                )
            )
            .consumes( new HashSet<>( Arrays.asList( "application/json", "application/xml" ) ) )
            .produces( new HashSet<>( Arrays.asList( "application/json", "application/xml" ) ) );
    }

}
