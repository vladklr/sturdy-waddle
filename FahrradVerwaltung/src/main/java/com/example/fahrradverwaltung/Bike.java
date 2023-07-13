package com.example.fahrradverwaltung;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

public class Bike {

    private String id;
    private String name;
    private String battery;
    private String price;

    private String rented_by;

    private String rented_until;

    public Bike(String id, String name, String battery, String price, String rented_by, String rented_until) {
        this.id = id;
        this.name = name;
        this.battery = battery;
        this.price = price;
        this.rented_by = rented_by;
        this.rented_until = rented_until;
    }

    public Bike(JSONObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.battery = jsonObject.getString("battery");
        this.price = jsonObject.getString("price");
        this.rented_by = jsonObject.getString("rented_by");
        this.rented_until = jsonObject.getString("rented_until");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }
    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }
    public String getRented_by() { return rented_by; }
    public void setRented_by(String rented_by) { this.rented_by = rented_by; }
    public String getRented_until() { return rented_until; }
    public void setRented_until(String rented_until) { this.rented_until = rented_until; }
}
