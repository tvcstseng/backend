package com.ttstudios.pi.dao.persistence.repository;

import com.ttstudios.pi.dao.persistence.AbstractMongoDAO;
import com.ttstudios.pi.dao.persistence.model.Measurement;
import org.springframework.stereotype.Repository;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
@Repository
public class MeasurementDao extends AbstractMongoDAO<Measurement> implements IMeasurementDao {
    public MeasurementDao() {
        super();

        setClazz(Measurement.class);
        setCollectionName("measurement");
    }

    public Measurement mergeChanges( Measurement dbEntity, Measurement changed ){
        // todo should merge updates right now
        return dbEntity;
    }
}
