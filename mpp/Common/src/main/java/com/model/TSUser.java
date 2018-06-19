package com.model;


import java.io.Serializable;

public class TSUser implements Serializable{

    private int userId;
    private String username;
    private String password;

    public TSUser(String username, String password) {
        this.userId = 0;
        this.username = username;
        this.password = password;
    }

    public TSUser(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSUser users = (TSUser) o;

        if (userId != users.userId) return false;
        if (username != null ? !username.equals(users.username) : users.username != null) return false;
        return password != null ? password.equals(users.password) : users.password == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return username;
    }
}
