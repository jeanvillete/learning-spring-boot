package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping( "/jpa/users" )
    public List< User > retrieveAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping( "/jpa/users/{id}" )
    public Resource< User > retrieveUser(@PathVariable Integer id ) {
        Optional<User> user = this.userRepository.findById(id);

        if ( !user.isPresent() ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        Resource< User > resource = new Resource<>( user.get() );
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

    @PostMapping( "/jpa/users" )
    public ResponseEntity createUser( @Valid @RequestBody User user ) {
        User savedUser = this.userRepository.save( user );

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path( "/{id}" )
            .buildAndExpand( savedUser.getId() )
            .toUri();

        return ResponseEntity
            .created( location )
            .build();
    }

    @DeleteMapping( "/jpa/users/{id}" )
    public ResponseEntity createUser( @PathVariable Integer id ) {
        Optional<User> user = this.userRepository.findById( id );

        if ( !user.isPresent() ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        this.userRepository.delete( user.get() );

        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping( "/jpa/users/{id}/posts" )
    public List<Post> retrieveAllUsers(@PathVariable int id ) {
        Optional<User> user = this.userRepository.findById(id);

        if ( !user.isPresent() ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        return user.get().getPosts();
    }


    @PostMapping( "/jpa/users/{id}/posts" )
    public ResponseEntity createUser( @PathVariable int id, @RequestBody Post post ) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if ( !userOptional.isPresent() ) {
            throw new UserNotFoundException( "id=[" + id + "]" );
        }

        User user = userOptional.get();
        post.setUser( user );

        postRepository.save( post );

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path( "/{id}" )
            .buildAndExpand( post.getId() )
            .toUri();

        return ResponseEntity
            .created( location )
            .build();
    }


}
