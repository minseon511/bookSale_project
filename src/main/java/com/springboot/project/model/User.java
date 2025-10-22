package com.springboot.project.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String name;
    private String email;
    private String role;
    private String picture;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}