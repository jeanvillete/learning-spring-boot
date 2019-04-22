insert into user values(nextval( 'hibernate_sequence' ), sysdate(), 'AB' ) ;
insert into user values(nextval( 'hibernate_sequence' ), sysdate(), 'Jack' ) ;
insert into user values(nextval( 'hibernate_sequence' ), sysdate(), 'Jill' ) ;

insert into post values (nextval( 'hibernate_sequence' ), 'first post', (select id from user where name = 'Jill'));
insert into post values (nextval( 'hibernate_sequence' ), 'second post', (select id from user where name = 'Jill'));
insert into post values (nextval( 'hibernate_sequence' ), 'third post', (select id from user where name = 'Jill'));