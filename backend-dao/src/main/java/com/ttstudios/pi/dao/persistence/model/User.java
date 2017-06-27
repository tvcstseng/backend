package com.ttstudios.pi.dao.persistence.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */

@QueryEntity
@Document
public class User extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 6861961813147181734L;

    @Id
    private String id;

    private String uId;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String firstName;

    private String lastName;

    //@DBRef
    //@Field("email")
    //@CascadeSave
    private String email;

    private String photoUrl;

    private int age;

    private Date dateOfBirth;

    private String location;

    private int watcherTypeId;


    public String getUId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWatcherTypeId() {
        return watcherTypeId;
    }

    public void setWatcherTypeId(int watcherTypeId) {
        this.watcherTypeId = watcherTypeId;
    }

}
