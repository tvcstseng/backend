package com.ttstudios.granny_watcher.backend.dto;

import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ttseng on 5/6/17.
 */
public class MeasurementDto extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = -4747375990087269943L;

    private double measurement;

    private String measurementUnit;

    private String unixTimestamp;

    private String sensorUid;

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

    public String getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(String unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public String getSensorUid() {
        return sensorUid;
    }

    public void setSensorUid(String sensorUid) {
        this.sensorUid = sensorUid;
    }
}
