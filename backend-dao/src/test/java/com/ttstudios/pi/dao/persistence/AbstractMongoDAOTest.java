package com.ttstudios.pi.dao.persistence;

import com.ttstudios.pi.dao.config.SimpleMongoConfig;
import com.ttstudios.pi.dao.persistence.model.User;
import com.ttstudios.pi.dao.persistence.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { SimpleMongoConfig.class }, loader = AnnotationConfigContextLoader.class )
public class AbstractMongoDAOTest {

    @Autowired
    UserService service;

    @org.junit.Test
    public void testUserCreation(){
        User user = TestData.createUser();
        service.saveOrUpdate(user);
    }

}