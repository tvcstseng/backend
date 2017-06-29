package com.ttstudios.pi.user;

import com.ttstudios.granny_watcher.backend.dto.UserDto;
import com.ttstudios.pi.dao.persistence.model.User;
import com.ttstudios.pi.dao.persistence.service.UserService;
import com.ttstudios.pi.transform.DtoToEntityMapper;
import com.ttstudios.pi.transform.MergeMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.ttstudios.pi.dao.persistence.service.UserService.UID;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping( "/api" )
@Api( value = "UserDto", description = "Operations about Users" )
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private MergeMapper mapper;

    @Autowired
    private DtoToEntityMapper toDtoMapper;

    @Override
    @RequestMapping( value = "/users/{uid}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    @ApiOperation( value = "Get a user using its UID" )
    public ResponseEntity<UserDto> getUserByUId(@PathVariable String uid) {

        User user = service.findOne( Criteria.where(UID).is(uid) );
        UserDto userDto = toDtoMapper.toDto(user);
        HttpStatus httpStatus;
        if ( userDto != null ) {
            userDto.removeLinks();
            userDto.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( uid ) ).withSelfRel() );
            httpStatus = HttpStatus.OK;
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<UserDto>( userDto, httpStatus );

    }

    @Override
    @RequestMapping( value = "/user", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Add a user" )
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {

        service.saveOrUpdate( toDtoMapper.toEntity(userDto) );

        userDto.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( userDto.getUid() ) ).withSelfRel() );
        userDto.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "Users" ) );

        return new ResponseEntity<>( userDto, HttpStatus.CREATED );
    }

    @Override
    @RequestMapping( value = "/followee/{uid}/{followeeId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    @ApiOperation( value = "Add a followee to ur account" )
    public ResponseEntity<UserDto> addFollowee(@PathVariable String uid, @RequestBody String followeeId) {

        UserDto userDto = toDtoMapper.toDto(service.findOne(Criteria.where("uid").is(uid)));

        if(userDto.getFolloweeIds() == null){
            userDto.setFolloweeIds(new ArrayList<>());
        }
        userDto.getFolloweeIds().add(followeeId);

        userDto.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( uid ) ).withSelfRel() );
        userDto.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "Users" ) );

        service.saveOrUpdate(toDtoMapper.toEntity(userDto));

        return new ResponseEntity<>( userDto, HttpStatus.CREATED );
    }

    @RequestMapping( value = "/users", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseBody
    @Override
    @ApiOperation( value = "List all users" )
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = service.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach( user -> {
            UserDto userDto = toDtoMapper.toDto(user);
            userDto.removeLinks();
            userDto.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( user.getUid() ) ).withSelfRel() );
            userDtos.add(userDto);
        } );
        return new ResponseEntity<>( userDtos, HttpStatus.OK );
    }

    @Override
    @RequestMapping( value = "/users/{sensorUid}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Update a user" )
    public ResponseEntity<UserDto> updateUser(@PathVariable String sensorUid, @RequestBody UserDto userDto) {
        HttpStatus httpStatus;
        if ( service.findOne( sensorUid ) != null ) {
            userDto.setUid( sensorUid );
            service.saveOrUpdate( toDtoMapper.toEntity(userDto) );
            httpStatus = HttpStatus.OK;
            userDto.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( sensorUid ) ).withSelfRel() );
            userDto.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "UserReadings" ) );
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( userDto, httpStatus );
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
