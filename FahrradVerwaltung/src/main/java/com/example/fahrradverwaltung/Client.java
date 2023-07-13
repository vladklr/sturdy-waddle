package com.example.fahrradverwaltung;

import org.json.JSONObject;

public class Client {

    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private String balance;

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
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Client (String username, String password, String first_name, String last_name, String email, String balance) {
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.balance = balance;
    }

    public Client(JSONObject jsonObject) {
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
        this.first_name = jsonObject.getString("first_name");
        this.last_name = jsonObject.getString("last_name");
        this.email = jsonObject.getString("email");
        this.balance = jsonObject.getString("balance");
    }


}
