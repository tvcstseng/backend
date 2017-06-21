package com.ttstudios.pi.dao.persistence;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
public abstract class AbstractMongoDAO<T extends Serializable> {

    private Class<T> clazz;
    private String collectionName;

    @Autowired
    private MongoTemplate mongoTemplate;

    protected abstract T mergeChanges(T dbEntity, T changedEntity);

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public final void setCollectionName(String collectionName){
        this.collectionName = collectionName;
    }

    public T create(final T entity) {
        mongoTemplate.insert(entity, collectionName);
        return entity;
    }

    public T saveOrUpdate(final T entity){
        mongoTemplate.save(entity, collectionName);
        return entity;
    }

    public T findOne(Criteria criteria){
        return mongoTemplate.findOne( Query.query(criteria), clazz);
    }

    public List<T> findAll(){
        return mongoTemplate.findAll(clazz);
    }

    // criteria - Criteria.where("name").is("Jack")
    public void saveOrUpdate(Criteria criteria, T changedEntity){
        T dbEntity = mongoTemplate.findOne(
                Query.query(criteria), clazz);

        dbEntity = mergeChanges( dbEntity, changedEntity);
        mongoTemplate.save(dbEntity, collectionName);
    }

    // Criteria.where("name").is("Alex")
    // Update update = new Update();
    // update.set("name", "James");
    public WriteResult updateFirst(Criteria criteria, Update update){
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.updateFirst(query, update, clazz);
    }

    public WriteResult updateMulti(Criteria criteria, Update update){
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.updateMulti(query, update, clazz);
    }

    public T findAndModify(Criteria criteria, Update update){
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.findAndModify(query, update, clazz);
    }

    public WriteResult upsert(Criteria criteria, Update update){
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.upsert(query, update, clazz);
    }

    public WriteResult delete(T entity){
        return mongoTemplate.remove(entity, collectionName);
    }

    public WriteResult deleteByCriteria(Criteria criteria){
        T entity = findOne(criteria);
        return delete(entity);
    }
}
