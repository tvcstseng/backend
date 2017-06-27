package com.ttstudios.pi.dao.persistence.repository;

import com.mongodb.WriteResult;
import com.ttstudios.pi.dao.persistence.model.User;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
public interface IUserDao {

    User findOne(Criteria criteria);

    User findOneByUid(String uid);

    List<User> findAll();

    User create(User entity);


    User saveOrUpdate(User entity);

    WriteResult delete(User entity);

    WriteResult deleteByCriteria(Criteria criteria);

    //void deleteById(long entityId);

}
