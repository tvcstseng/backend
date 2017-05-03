package uk.co.cyberbliss.temperature.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import uk.co.cyberbliss.temperature.dto.TemperatureDto;
import uk.co.cyberbliss.temperature.repository.TemperatureRepository;

@RestController
@RequestMapping( "/api" )
@Api( value = "Temperature", description = "Operations about Temperature readings" )
public class TemperatureResourceImpl implements TemperatureResource {

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
        temperatureReadingsRepo.addReading( dto );
        dto.add( linkTo( methodOn( TemperatureResourceImpl.class ).getTemperatureBySensorUid( dto.getSensorUID() ) ).withSelfRel() );
        dto.add( linkTo( methodOn( TemperatureResourceImpl.class ).getAllTemperatureReadings() ).withRel( "TemperatureReadings" ) );

        return new ResponseEntity<>( dto, HttpStatus.CREATED );
    }

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
