package com.ttstudios.pi.transform;

import com.ttstudios.pi.dao.persistence.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * Created by Timothy Tseng on 27-6-2017.
 */
@Component
@Mapper( unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring" )
public abstract class MergeMapper {

    @Mapping( target = "id", ignore = true)
    public abstract User merge (User inbound);
}
