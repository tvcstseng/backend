package com.ttstudios.pi.dao.persistence.repository;

import com.ttstudios.pi.dao.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */

public interface UserRepository extends MongoRepository<User, String> , QueryByExampleExecutor<User> {
//
}