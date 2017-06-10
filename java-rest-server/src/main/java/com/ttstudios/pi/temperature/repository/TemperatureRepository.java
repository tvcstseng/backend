package com.ttstudios.pi.temperature.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ttstudios.granny_watcher.backend.dto.TemperatureDto;
import org.springframework.stereotype.Component;

<<<<<<< 975bf8c130b5025d6810b6abc48a821e3aea3623:java-rest-server/src/main/java/uk/co/cyberbliss/temperature/repository/TemperatureRepository.java
=======
import com.ttstudios.pi.temperature.dto.TemperatureDto;

>>>>>>> 1525bfc4e253dd5155f51b3fcb8e5ed9966dc9ed:java-rest-server/src/main/java/com/ttstudios/pi/temperature/repository/TemperatureRepository.java
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
