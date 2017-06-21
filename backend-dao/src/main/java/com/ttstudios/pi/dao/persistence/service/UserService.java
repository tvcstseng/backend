package com.ttstudios.pi.dao.persistence.service;

import com.mongodb.WriteResult;
import com.ttstudios.pi.dao.persistence.model.User;
import com.ttstudios.pi.dao.persistence.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    IUserDao dao;

    public UserService() {
        super();
    }

    public void saveOrUpdate(final User entity) {
        dao.saveOrUpdate(entity);
    }

    public User findOne(Criteria criteria) {
        return dao.findOne(criteria);
    }

    public User findOne(String uid) {
        Criteria criteria = Criteria.where("uId").is(uid);
        return dao.findOne(criteria);
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public boolean delete(User user){
        dao.delete(user);
        return true;
    }

    public boolean deleteByUID(String uid){
        Criteria criteria = Criteria.where("uId").is(uid);
        WriteResult result = dao.deleteByCriteria(criteria);
        return true;
    }

}
