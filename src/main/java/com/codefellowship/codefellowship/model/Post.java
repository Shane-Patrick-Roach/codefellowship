package com.codefellowship.codefellowship.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String body;
    Timestamp createdAt;

    @ManyToOne
    ApplicationUser postsOfUser;


    public Post() {

    }

    public Post(String body, Timestamp createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getPostsOfUser() {
        return postsOfUser;
    }

    public void setPostsOfUser(ApplicationUser postsOfUser) {
        this.postsOfUser = postsOfUser;
    }
}
