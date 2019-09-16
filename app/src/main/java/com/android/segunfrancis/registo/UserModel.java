package com.android.segunfrancis.registo;

public class UserModel {
    private String username;
    private String email;
    private String telephone;

    public UserModel(String username, String email, String telephone) {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
    }

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
