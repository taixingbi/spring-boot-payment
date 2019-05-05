package com.example.demo.model;

import javax.persistence.Id;

public class Checkout {
    @Id
    private int id;
    private String city;

    public int getId() {
        return id;
    }
    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    //city

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }



}