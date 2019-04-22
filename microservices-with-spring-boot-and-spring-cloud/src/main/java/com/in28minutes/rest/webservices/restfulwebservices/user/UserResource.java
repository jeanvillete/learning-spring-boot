package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    @GetMapping( "/users" )
    public List< User > retrieveAllUsers() {
        return this.service.findAll();
    }

    @GetMapping( "/users/{id}" )
    public Resource< User > retrieveUser(@PathVariable Integer id ) {
        User user = this.service.findOne(id);

        if ( user == null ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        Resource< User > resource = new Resource<>( user );
        resource.add(
            linkTo(
                methodOn( this.getClass() ).retrieveAllUsers()
            )
            .withRel( "all-users" )
        );
        resource.add(
            linkTo(
                methodOn( this.getClass() ).retrieveUser( id )
            )
            .withSelfRel()
        );

        return resource;
    }

    @PostMapping( "/users" )
    public ResponseEntity createUser( @Valid @RequestBody User user ) {
        User savedUser = this.service.save( user );

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path( "/{id}" )
            .buildAndExpand( savedUser.getId() )
            .toUri();

        return ResponseEntity
            .created( location )
            .build();
    }

    @DeleteMapping( "/users/{id}" )
    public ResponseEntity createUser( @PathVariable Integer id ) {
        User deletedUser = this.service.delete( id );

        if ( deletedUser == null ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        return ResponseEntity
            .noContent()
            .build();
    }

}
