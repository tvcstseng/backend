package com.ttstudios.pi.temperature.controller;

import com.ttstudios.common.db.database_connectivity.MongoDBDatabaseManager;
import com.ttstudios.granny_watcher.backend.dto.TemperatureDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.ttstudios.backend.dao.entities.MeasurementEntity;
import nl.ttstudios.backend.dao.transform.DtoToEntityMapper;
import org.mapstruct.factory.Mappers;
import org.mongojack.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< 975bf8c130b5025d6810b6abc48a821e3aea3623:java-rest-server/src/main/java/uk/co/cyberbliss/temperature/controller/TemperatureResourceImpl.java
import org.springframework.web.bind.annotation.*;
import uk.co.cyberbliss.temperature.repository.TemperatureRepository;
=======
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.ttstudios.pi.temperature.dto.TemperatureDto;
import com.ttstudios.pi.temperature.repository.TemperatureRepository;
>>>>>>> 1525bfc4e253dd5155f51b3fcb8e5ed9966dc9ed:java-rest-server/src/main/java/com/ttstudios/pi/temperature/controller/TemperatureResourceImpl.java

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping( "/api" )
@Api( value = "Temperature", description = "Operations about Temperature readings" )
public class TemperatureResourceImpl implements TemperatureResource {

    public static final DtoToEntityMapper INSTANCE = Mappers.getMapper( DtoToEntityMapper.class );


    @Autowired
    private TemperatureRepository temperatureReadingsRepo;

    @Override
    @RequestMapping( value = "/temperature_readings/{sensor_uid}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseStatus( HttpStatus.OK )
    @ResponseBody
    @ApiOperation( value = "Get a temperature using its SensorUID" )
    public ResponseEntity<TemperatureDto> getTemperatureBySensorUid(@PathVariable String sensor_uid) {
        TemperatureDto result = temperatureReadingsRepo.getReading( sensor_uid );
        HttpStatus httpStatus;
        if ( result != null ) {
            result.removeLinks();
            result.add( linkTo( methodOn( TemperatureResourceImpl.class ).getTemperatureBySensorUid( sensor_uid ) ).withSelfRel() );
            httpStatus = HttpStatus.OK;
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<TemperatureDto>( result, httpStatus );

    }

    @Override
    @RequestMapping( value = "/temperature_reading", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Add a temperature reading" )
    public ResponseEntity<TemperatureDto> addTemperatureReading(@Valid @RequestBody TemperatureDto dto) {

        MeasurementEntity entity = INSTANCE.toEntity(dto);
        MongoDBDatabaseManager.getInstance().insert(entity, MeasurementEntity.class);

        temperatureReadingsRepo.addReading( dto );
        dto.add( linkTo( methodOn( TemperatureResourceImpl.class ).getTemperatureBySensorUid( dto.getSensorUID() ) ).withSelfRel() );
        dto.add( linkTo( methodOn( TemperatureResourceImpl.class ).getAllTemperatureReadings() ).withRel( "TemperatureReadings" ) );

        return new ResponseEntity<>( dto, HttpStatus.CREATED );
    }

    // http://192.168.1.185:9080/api/temperature_readings
    @RequestMapping( value = "/temperature_readings", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @ResponseBody
    @Override
    @ApiOperation( value = "List all temperature readings" )
    public ResponseEntity<List<TemperatureDto>> getAllTemperatureReadings() {
        List<TemperatureDto> temperatureReadings = temperatureReadingsRepo.getAllReadings();
        temperatureReadings.forEach( reading -> {
            reading.removeLinks();
            reading.add( linkTo( methodOn( TemperatureResourceImpl.class ).getTemperatureBySensorUid( reading.getSensorUID() ) ).withSelfRel() );
        } );
        return new ResponseEntity<>( temperatureReadings, HttpStatus.OK );
    }

    @Override
    @RequestMapping( value = "/temperature_readings/{sensorUid}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE )
    @ApiOperation( value = "Update a book" )
    public ResponseEntity<TemperatureDto> updateTemperatureReading(@PathVariable String sensorUid, @RequestBody TemperatureDto temperatureReading) {
        HttpStatus httpStatus;
        if ( temperatureReadingsRepo.isReadingAvailable( sensorUid ) ) {
            temperatureReading.setuID( sensorUid );
            temperatureReadingsRepo.addReading( temperatureReading );
            httpStatus = HttpStatus.OK;
            temperatureReading.add( linkTo( methodOn( TemperatureResourceImpl.class ).getTemperatureBySensorUid( sensorUid ) ).withSelfRel() );
            temperatureReading.add( linkTo( methodOn( TemperatureResourceImpl.class ).getAllTemperatureReadings() ).withRel( "TemperatureReadings" ) );
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( temperatureReading, httpStatus );
    }

    @Override
    @RequestMapping( value = "/temperature_readings/{sensor_uid}", method = RequestMethod.DELETE )
    @ApiOperation( value = "Delete a temperature reading" )
    public ResponseEntity<Void> deleteTemperatureReading(@PathVariable String sensorUid) {
        HttpStatus httpStatus;
        if ( temperatureReadingsRepo.removeReading( sensorUid ) ) {
            httpStatus = HttpStatus.OK;
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( httpStatus );
    }

}
