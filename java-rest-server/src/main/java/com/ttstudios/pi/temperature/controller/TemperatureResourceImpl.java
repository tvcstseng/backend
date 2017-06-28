package com.ttstudios.pi.temperature.controller;

import com.ttstudios.granny_watcher.backend.dto.MeasurementDto;
import com.ttstudios.pi.dao.persistence.model.Measurement;
import com.ttstudios.pi.dao.persistence.service.MeasurementService;
import com.ttstudios.pi.transform.DtoToEntityMapper;
import com.ttstudios.pi.user.UserResourceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@Api(value = "Temperature", description = "Operations about Temperature readings")
public class TemperatureResourceImpl implements TemperatureResource {

    @Autowired
    private DtoToEntityMapper toDtoMapper;

    @Autowired
    private MeasurementService service;

    @Override
    @RequestMapping(value = "/temperature_readings/{sensor_uid}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "Get a temperature using its SensorUID")
    public ResponseEntity<MeasurementDto> getTemperatureBySensorUid(@PathVariable String sensorUid) {
        Measurement entity = service.findOne(sensorUid);
        MeasurementDto dto = toDtoMapper.toDto(entity);

        HttpStatus httpStatus;
        if (dto != null) {
            dto.removeLinks();
            dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(sensorUid)).withSelfRel());
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<MeasurementDto>(dto, httpStatus);

    }

    @Override
    @RequestMapping(value = "/temperature_reading", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a temperature reading")
    public ResponseEntity<MeasurementDto> addTemperatureReading(@Valid @RequestBody MeasurementDto dto) {

        service.saveOrUpdate(toDtoMapper.toEntity(dto));

        dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(dto.getSensorUid())).withSelfRel());
        dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getAllTemperatureReadings()).withRel("TemperatureReadings"));

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://192.168.1.185:9080/api/temperature_readings
    @RequestMapping(value = "/temperature_readings", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    @ApiOperation(value = "List all temperature readings")
    public ResponseEntity<List<MeasurementDto>> getAllTemperatureReadings() {
        List<Measurement> temperatureReadings = service.findAll();
        List<MeasurementDto> dtos = new ArrayList<>();
        temperatureReadings.forEach(reading -> {
            MeasurementDto dto = toDtoMapper.toDto(reading);
            dto.removeLinks();
            dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(reading.getSensorUid())).withSelfRel());
            dtos.add(dto);
        });
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/temperature_readings/{sensorUid}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a temperature")
    public ResponseEntity<MeasurementDto> updateTemperatureReading(@PathVariable String sensorUid, @RequestBody MeasurementDto temperatureReading) {
        HttpStatus httpStatus;

        Measurement dbEntity = service.findOne( sensorUid );
        if ( dbEntity != null ) {
            temperatureReading.setSensorUid( sensorUid );
            service.saveOrUpdate( toDtoMapper.toEntity(temperatureReading) );

            httpStatus = HttpStatus.OK;
            temperatureReading.add( linkTo( methodOn( UserResourceImpl.class ).getUserByUId( sensorUid ) ).withSelfRel() );
            temperatureReading.add( linkTo( methodOn( UserResourceImpl.class ).getAllUsers() ).withRel( "UserReadings" ) );
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>( temperatureReading, httpStatus );
    }

    @Override
    @RequestMapping(value = "/temperature_readings/{sensor_uid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a temperature reading")
    public ResponseEntity<Void> deleteTemperatureReading(@PathVariable String sensorUid) {
        HttpStatus httpStatus;
        if (service.deleteByUID(sensorUid)) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(httpStatus);
    }

}
