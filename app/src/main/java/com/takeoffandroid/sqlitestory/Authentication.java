package com.takeoffandroid.sqlitestory;

/**
 * Created by chandrasekar.kuppusa on 06-07-2015.
 */
public class Authentication {

    private String id;

    private String name;

    private String mobile;

    private String email;

    private String password;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }


    public String getMobile () {
        return mobile;
    }

    public void setMobile (String mobile) {
        this.mobile = mobile;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public Authentication ( String name, String mobile, String email, String password) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }


    public Authentication () {
    }
}
