package com.ttstudios.pi.dao.persistence;

import com.ttstudios.pi.dao.persistence.model.Measurement;
import com.ttstudios.pi.dao.persistence.model.User;

import java.util.Date;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
public class TestData {

    public static User createUser(){
        User user = new User();

        user.setName("Timothy");
        user.setLastName("Tseng");
        user.setAge(32);
        user.setDateOfBirth(new Date());
        user.setEmail("tvcstseng@gmail.com");
        user.setLocation("Amsterdam");
        user.setWatcherTypeId(0);
        return user;
    }

    public static Measurement createMeasurement(){
        Measurement measurement = new Measurement();

        measurement.setMeasurement(1.2d);
        measurement.setMeasurementUnit("c");
        measurement.setuID("7777777");
        measurement.setUnixTimestamp(new Date());

        return measurement;
    }
}