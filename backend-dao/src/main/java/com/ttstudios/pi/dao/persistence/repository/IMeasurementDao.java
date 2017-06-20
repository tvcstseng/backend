package com.ttstudios.pi.dao.persistence.repository;

import com.mongodb.WriteResult;
import com.ttstudios.pi.dao.persistence.model.Measurement;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
public interface IMeasurementDao {

    Measurement findOne(Criteria criteria);

    List<Measurement> findAll();

    Measurement create(Measurement entity);

    Measurement saveOrUpdate(Measurement entity);

    WriteResult delete(Measurement entity);

    WriteResult deleteByCriteria(Criteria criteria);

}
