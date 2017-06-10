package com.ttstudios.pi.dao.persistence.repository;

import com.ttstudios.pi.dao.persistence.AbstractMongoDAO;
import com.ttstudios.pi.dao.persistence.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
@Repository
public class UserDao extends AbstractMongoDAO<User> implements IUserDao {
    public UserDao() {
        super();

        setClazz(User.class);
        setCollectionName("user");
    }

    public User mergeChanges( User dbEntity, User changed ){
        // todo should merge updates right now
        return dbEntity;
    }
}
