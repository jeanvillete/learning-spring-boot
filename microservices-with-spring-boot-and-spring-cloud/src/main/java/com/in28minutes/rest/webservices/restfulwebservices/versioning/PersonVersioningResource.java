package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningResource {

    @GetMapping( "/v1/person" )
    public PersonV1 uriPersonV1() {
        return new PersonV1( "Bob Charlie" );
    }

    @GetMapping( "/v2/person" )
    public PersonV2 uriPersonV2() {
        return new PersonV2( new Name( "Bob", "Charlie" ) );
    }

    @GetMapping( value = "/person/param", params = "version=1" )
    public PersonV1 paramPersonV1() {
        return new PersonV1( "Bob Charlie" );
    }

    @GetMapping( value = "/person/param", params = "version=2" )
    public PersonV2 paramPersonV2() {
        return new PersonV2( new Name( "Bob", "Charlie" ) );
    }

    @GetMapping( value = "/person/header", headers = "X-API-VERSION=1" )
    public PersonV1 headerPersonV1() {
        return new PersonV1( "Bob Charlie" );
    }

    @GetMapping( value = "/person/header", headers = "X-API-VERSION=2" )
    public PersonV2 headerPersonV2() {
        return new PersonV2( new Name( "Bob", "Charlie" ) );
    }

    // aka Mime-type versioning
    @GetMapping( value = "/person/produces", produces = "application/vnd.company.app-v1+json" )
    public PersonV1 producesPersonV1() {
        return new PersonV1( "Bob Charlie" );
    }
    @GetMapping( value = "/person/produces", produces = "application/vnd.company.app-v2+json" )
    public PersonV2 producesPersonV2() {
        return new PersonV2( new Name( "Bob", "Charlie" ) );
    }

}
