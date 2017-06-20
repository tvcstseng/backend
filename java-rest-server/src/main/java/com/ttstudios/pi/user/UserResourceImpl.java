package com.ttstudios.pi.user;

import com.ttstudios.pi.dao.persistence.model.User;
import com.ttstudios.pi.dao.persistence.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping( "/api" )
@Api( value = "User", description = "Operations about Users" )
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService service;

    @Override
    @RequestMapping( value = "/users/{uid}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    @ApiOperation( value = "Get a user using its UID" )
    public ResponseEntity<User> getUserByUId(@PathVariable String uid) {

        Criteria criteria = Criteria.where("uId").is(uid);
        User result = service.findOne( criteria );
        HttpStatus httpStatus;
        if ( result != null ) {
            result.removeLinks();
            result.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( uid ) ).withSelfRel() );
            httpStatus = HttpStatus.OK;
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<User>( result, httpStatus );

    }

    @Override
    @RequestMapping( value = "/user", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Add a user" )
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        service.saveOrUpdate( user );
        user.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( user.getUId() ) ).withSelfRel() );
        user.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "Users" ) );

        return new ResponseEntity<>( user, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/users", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseBody
    @Override
    @ApiOperation( value = "List all users" )
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.findAll();
        users.forEach( user -> {
            user.removeLinks();
            user.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( user.getUId() ) ).withSelfRel() );
        } );
        return new ResponseEntity<>( users, HttpStatus.OK );
    }

    @Override
    @RequestMapping( value = "/users/{sensorUid}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Update a user" )
    public ResponseEntity<User> updateUser(@PathVariable String sensorUid, @RequestBody User user) {
        HttpStatus httpStatus;
        if ( service.findOne( sensorUid ) != null ) {
            user.setuId( sensorUid );
            service.saveOrUpdate( user );
            httpStatus = HttpStatus.OK;
            user.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( sensorUid ) ).withSelfRel() );
            user.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "UserReadings" ) );
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( user, httpStatus );
    }

    @Override
    @RequestMapping( value = "/users/{sensor_uid}", method = RequestMethod.DELETE )
    @ApiOperation( value = "Delete a user" )
    public ResponseEntity<Void> deleteUser(@PathVariable String sensorUid) {
        HttpStatus httpStatus;
        if ( service.deleteByUID( sensorUid ) ) {
            httpStatus = HttpStatus.OK;
    }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( httpStatus );
    }

}
