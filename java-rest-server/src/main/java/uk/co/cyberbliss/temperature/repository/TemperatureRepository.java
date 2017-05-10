package uk.co.cyberbliss.temperature.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ttstudios.granny_watcher.backend.dto.TemperatureDto;
import org.springframework.stereotype.Component;

@Component
public class TemperatureRepository {
    HashMap<String, TemperatureDto> readings = new HashMap<>();

    public void addReading(TemperatureDto reading){

        readings.put(reading.getuID(), reading);
    }

    public TemperatureDto getReading(String uId){
        return readings.get(uId);
    }

    public List<TemperatureDto> getAllReadings(){
        return new ArrayList<>(readings.values());
    }

    public Boolean isReadingAvailable(String uId) {
        return readings.containsKey(uId);
    }

    public Boolean removeReading(String uId) {
        if (readings.containsKey(uId)){
            readings.remove(uId);
            return true;
        }else{
            return false;
        }
    }
}
