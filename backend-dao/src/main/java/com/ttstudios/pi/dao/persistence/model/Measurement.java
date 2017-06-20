package com.ttstudios.pi.dao.persistence.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ttseng on 5/6/17.
 */
@QueryEntity
@Document
public class Measurement extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = -4747375990087269943L;

    @Id
    private String _id;

    private String uID;

    private double measurement;

    private String measurementUnit;

    private Date unixTimestamp;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Date getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(Date unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

}
