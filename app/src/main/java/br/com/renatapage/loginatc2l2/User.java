package br.com.renatapage.loginatc2l2;

import java.util.UUID;

/**
 * Created by Renata Page on 09/01/2017.
 */

public class User {

    private String uniqueID = UUID.randomUUID().toString();
    private String username;
    private String password;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
