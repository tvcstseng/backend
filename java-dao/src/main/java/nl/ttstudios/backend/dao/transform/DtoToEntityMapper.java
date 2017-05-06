package nl.ttstudios.backend.dao.transform;

import com.ttstudios.granny_watcher.backend.dto.TemperatureDto;
import nl.ttstudios.backend.dao.entities.MeasurementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import org.mapstruct.ReportingPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

/**
 * Created by ttseng on 5/6/17.
 */
@Mapper( unmappedTargetPolicy = ReportingPolicy.ERROR )
public abstract class DtoToEntityMapper {

    public abstract java.util.List<MeasurementEntity> toEntity(List<TemperatureDto> source);

    //_id, measurement, measurementUnit
    @Mapping( target = "_id", ignore = true)
    @Mapping( target = "measurement", source = "temperature")
    @Mapping( target = "measurementUnit", source = "temperatureUnit")
    @Mapping( target = "unixTimestamp", source = "unixTimestamp", qualifiedBy = TimestampLong.class)
    public abstract MeasurementEntity toEntity(TemperatureDto source);

    @TimestampLong
    protected Date timestampToDate(String date){
        return new Date(Long.valueOf(date));
    }

    @Qualifier
    @Target( ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface TimestampLong{}
}
