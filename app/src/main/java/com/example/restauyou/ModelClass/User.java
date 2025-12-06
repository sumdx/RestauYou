package com.example.restauyou.ModelClass;

public class User {

    private String name, email, photoUrl,id, role, phone;

    public User(String name, String email, String photoUrl, String id, String role, String phone) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.id = id;
        this.role = role;
        this.phone = phone;
    }


    public User(){

    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
