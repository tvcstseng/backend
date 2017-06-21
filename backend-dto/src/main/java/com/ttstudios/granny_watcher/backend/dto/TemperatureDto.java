package com.ttstudios.granny_watcher.backend.dto;

import com.google.gson.Gson;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TemperatureDto extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 2601665415524779298L;

    @NotBlank
    private String uID;

    @NotBlank
    private String sensorUID;

    @NotBlank
    private String deviceUID;

    @NotNull
    private double temperature;

    @NotBlank
    private String temperatureUnit;

    @NotBlank
    private String temperatureCRC;

    @NotNull
    private boolean isTemperatureOK;

    @NotBlank
    private String unixTimestamp;

    public TemperatureDto() {
    }

    public TemperatureDto(String uId, String sensorUID, String deviceUID, double temperature, String temperatureUnit, String temperatureCRC, boolean isTemperatureOK, String unixTimestamp) {
        this.uID = uId;
        this.sensorUID = sensorUID;
        this.deviceUID = deviceUID;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
        this.temperatureCRC = temperatureCRC;
        this.isTemperatureOK = isTemperatureOK;
        this.unixTimestamp = unixTimestamp;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getSensorUID() {
        return sensorUID;
    }

    public void setSensorUID(String sensorUID) {
        this.sensorUID = sensorUID;
    }

    public String getDeviceUID() {
        return deviceUID;
    }

    public void setDeviceUID(String deviceUID) {
        this.deviceUID = deviceUID;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getTemperatureCRC() {
        return temperatureCRC;
    }

    public void setTemperatureCRC(String temperatureCRC) {
        this.temperatureCRC = temperatureCRC;
    }

    public boolean isTemperatureOK() {
        return isTemperatureOK;
    }

    public void setTemperatureOK(boolean isTemperatureOK) {
        this.isTemperatureOK = isTemperatureOK;
    }

    public String getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(String unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    @Override
    public String toString() {
        return toJson();
    }

    private String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson( this );
        return json;
    }

}
