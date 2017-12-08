package com.henteti.feres.webservicevolley.Models;

/**
 * Created by Feres on 12/7/2017.
 */

public class User {

    private String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

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
}
