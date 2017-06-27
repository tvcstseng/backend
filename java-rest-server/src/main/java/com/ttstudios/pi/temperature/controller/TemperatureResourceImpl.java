package com.ttstudios.pi.temperature.controller;

import com.ttstudios.pi.dao.persistence.model.Measurement;
import com.ttstudios.pi.dao.persistence.service.MeasurementService;
import com.ttstudios.pi.transform.DtoToEntityMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
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
@RequestMapping("/api")
@Api(value = "Temperature", description = "Operations about Temperature readings")
public class TemperatureResourceImpl implements TemperatureResource {

    @Autowired
    private DtoToEntityMapper INSTANCE;

    @Autowired
    private MeasurementService service;

    @Override
    @RequestMapping(value = "/temperature_readings/{sensor_uid}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "Get a temperature using its SensorUID")
    public ResponseEntity<Measurement> getTemperatureBySensorUid(@PathVariable String sensor_uid) {
        Measurement result = service.findOne(sensor_uid);
        HttpStatus httpStatus;
        if (result != null) {
            result.removeLinks();
            result.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(sensor_uid)).withSelfRel());
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<Measurement>(result, httpStatus);

    }

    @Override
    @RequestMapping(value = "/temperature_reading", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a temperature reading")
    public ResponseEntity<Measurement> addTemperatureReading(@Valid @RequestBody Measurement dto) {

        //Measurement entity = INSTANCE.toEntity(dto);
        service.saveOrUpdate(dto);

        service.saveOrUpdate(dto);
        dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(dto.get_id())).withSelfRel());
        dto.add(linkTo(methodOn(TemperatureResourceImpl.class).getAllTemperatureReadings()).withRel("TemperatureReadings"));

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://192.168.1.185:9080/api/temperature_readings
    @RequestMapping(value = "/temperature_readings", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    @ApiOperation(value = "List all temperature readings")
    public ResponseEntity<List<Measurement>> getAllTemperatureReadings() {
        List<Measurement> temperatureReadings = service.findAll();
        temperatureReadings.forEach(reading -> {
            reading.removeLinks();
            reading.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(reading.get_id())).withSelfRel());
        });
        return new ResponseEntity<>(temperatureReadings, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/temperature_readings/{sensorUid}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update a temperature")
    public ResponseEntity<Measurement> updateTemperatureReading(@PathVariable String sensorUid, @RequestBody Measurement temperatureReading) {
        HttpStatus httpStatus;

        Measurement entity = service.findOne(Criteria.where("sensorUid").is(sensorUid));

        if (entity != null) {
            temperatureReading.setuID(sensorUid);
            service.saveOrUpdate(temperatureReading);
            httpStatus = HttpStatus.OK;
            temperatureReading.add(linkTo(methodOn(TemperatureResourceImpl.class).getTemperatureBySensorUid(sensorUid)).withSelfRel());
            temperatureReading.add(linkTo(methodOn(TemperatureResourceImpl.class).getAllTemperatureReadings()).withRel("TemperatureReadings"));
        } else {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(temperatureReading, httpStatus);
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
