package uk.co.cyberbliss.temperature.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.hateoas.ResourceSupport;

import com.google.gson.Gson;

public class TemperatureDto extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 2601665415524779298L;

    @NotBlank
    private String sensorUID;

    @NotBlank
    private String deviceUID;

    @NotBlank
    private String temperatureCelsius;

    @NotBlank
    private String temperatureCRC;

    @NotBlank
    private String isTemperatureOK;

    @NotBlank
    private String unixTimestamp;

    public TemperatureDto() {
    }

    public TemperatureDto(String sensorUID, String deviceUID, String temperatureCelsius, String temperatureCRC, String isTemperatureOK, String unixTimestamp) {
        this.sensorUID = sensorUID;
        this.deviceUID = deviceUID;
        this.temperatureCelsius = temperatureCelsius;
        this.temperatureCRC = temperatureCRC;
        this.isTemperatureOK = isTemperatureOK;
        this.unixTimestamp = unixTimestamp;
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

    public String getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(String temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getTemperatureCRC() {
        return temperatureCRC;
    }

    public void setTemperatureCRC(String temperatureCRC) {
        this.temperatureCRC = temperatureCRC;
    }

    public String getIsTemperatureOK() {
        return isTemperatureOK;
    }

    public void setIsTemperatureOK(String isTemperatureOK) {
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
    
    private String toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    

}
