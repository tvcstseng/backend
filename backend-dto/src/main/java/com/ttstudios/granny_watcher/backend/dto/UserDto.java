package com.ttstudios.granny_watcher.backend.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Timothy Tseng on 10-6-2017.
 */
public class UserDto extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 6861961813147181734L;

    private String id;

    @NotNull
    private String uid;

    @NotBlank
    //@Indexed(direction = IndexDirection.ASCENDING)
    private String firstName;

    @NotBlank
    private String lastName;

    private String password;

    //@DBRef
    //@Field("email")
    //@CascadeSave
    @NotBlank
    private String email;

    private String photoUrl;

    private int age;

    private Date dateOfBirth;

    private String location;

    private int watcherType;

    private List<String> followeeIds;

    public UserDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public int getWatcherType() {
        return watcherType;
    }

    public void setWatcherType(int watcherType) {
        this.watcherType = watcherType;
    }

    public List<String> getFolloweeIds() {
        return followeeIds;
    }

    public void setFolloweeIds(List<String> followeeIds) {
        this.followeeIds = followeeIds;
    }
}
