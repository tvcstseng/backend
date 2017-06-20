package com.ttstudios.pi.transform;

import com.ttstudios.granny_watcher.backend.dto.TemperatureDto;
import com.ttstudios.pi.dao.persistence.model.Measurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

/**
 * Created by ttseng on 5/6/17.
 */
@Component
@Mapper( unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring" )
public abstract class DtoToEntityMapper {

    public abstract java.util.List<Measurement> toEntity(List<TemperatureDto> source);

    //_id, measurement, measurementUnit
    @Mapping( target = "_id", ignore = true)
    @Mapping( target = "measurement", source = "temperature")
    @Mapping( target = "measurementUnit", source = "temperatureUnit")
    @Mapping( target = "unixTimestamp", source = "unixTimestamp", qualifiedBy = TimestampLong.class)
    public abstract Measurement toEntity(TemperatureDto source);

    @TimestampLong
    protected Date timestampToDate(String date){
        return new Date(Long.valueOf(date));
    }

    @Qualifier
    @Target( ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface TimestampLong{}
}
