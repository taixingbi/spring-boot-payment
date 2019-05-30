package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "pos_tours_orders_test")

@Entity
public class Pos_tours_orders extends Pos_orders {

    private String tour_type;
    private String tour_place;

    private int adult;
    private int child;
    private int walking;
    private int pedicab;
    private int total_people;
    private int trailer;
    private int kid_trailer;
    private int basket;
    private int seat;

    //tour_type
    public String getTour_type() {
        return tour_type;
    }

    public void setTour_type(String tour_type) {
        this.tour_type = tour_type;
    }

    //tour_place
    public String getTour_place() {
        return tour_place;
    }

    public void setTour_place(String tour_place) {
        this.tour_place = tour_place;
    }

    //adult;
    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    //child;
    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    //walking;
    public int getWalking() {
        return walking;
    }

    public void setWalking(int walking) {
        this.walking = walking;
    }

    //pedicab;
    public int getPedicab() {
        return pedicab;
    }

    public void setPedicab(int pedicab) {
        this.pedicab = pedicab;
    }

    //total_people;
    public int getTotal_people() {
        return total_people;
    }

    public void setTotal_people(int total_people) {
        this.total_people = total_people;
    }

    //trailer;
    public int getTrailer() {
        return trailer;
    }

    public void setTrailer(int trailer) {
        this.trailer = trailer;
    }

    //kid_trailer;
    public int getKid_trailer() {
        return kid_trailer;
    }

    public void setkid_trailer(int kid_trailer) {
        this.kid_trailer = kid_trailer;
    }

    //basket;
    public int getBasket() {
        return basket;
    }

    public void setBasket(int basket) {
        this.basket = basket;
    }

    //seat;
    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

}
