package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {

    private static final List< User > users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add( new User( 1, "Adam", new Date() ) );
        users.add( new User( 2, "Eve", new Date() ) );
        users.add( new User( 3, "Jack", new Date() ) );

        usersCount = users.size();
    }

    public List< User > findAll() {
        return Collections.unmodifiableList( users );
    }

    public User save( User user ) {
        if ( user.getId() == null ) {
            user.setId( ++ usersCount );
        }
        users.add( user );
        return user;
    }

    public User findOne( Integer id ) {
        return users.stream()
            .filter( u -> u.getId() == id )
            .findFirst()
            .orElse( null );
    }

    public User delete( Integer id ) {
        Iterator< User > iterator = users.iterator();
        while ( iterator.hasNext() ) {
            User user = iterator.next();
            if ( user.getId() == id ) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

}
