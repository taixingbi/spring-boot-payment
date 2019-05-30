package com.example.demo.model;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "pos_rents_orders_test")

@Entity
public class Pos_rents_orders extends Pos_orders {

    private String duration;
    private int adult;
    private int child;
    private int tandem;
    private int road;
    private int mountain;
    private int hand;
    private int electric_bike;
    private int electric_hand;
    private int elliptigo;
    private int tricycle;
    private int carbon_road;
    private int total_bikes;
    private int trailer;
    private int kid_trailer;
    private int basket;
    private int seat;
    private int locks;
    private int dropoff;

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    //adult
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

    //tandem;
    public int getTandem() {
        return tandem;
    }
    public void setTandem(int tandem) {
        this.tandem = tandem;
    }
    //road;
    public int getRoad() {
        return road;
    }
    public void setRoad(int road) {
        this.road = road;
    }

    //mountain;
    public int getMountain() {
        return mountain;
    }
    public void setMountain(int mountain) {
        this.mountain = mountain;
    }
    //hand;
    public int getHand() {
        return hand;
    }
    public void setHand(int hand) {
        this.hand = hand;
    }

    //electric_bike;
    public int getElectric_bike() {
        return electric_bike;
    }
    public void setElectric_bike(int electric_bike) {
        this.electric_bike = electric_bike;
    }

    //electric_hand;
    public int getElectric_hand() {
        return electric_hand;
    }
    public void setElectric_hand(int electric_hand) {
        this.electric_hand = electric_hand;
    }

    //elliptigo;
    public int getElliptigo() {
        return elliptigo;
    }
    public void setElliptigo(int elliptigo) {
        this.elliptigo = elliptigo;
    }

    //tricycle;
    public int getTricycle() {
        return tricycle;
    }
    public void setTricycle(int tricycle) {
        this.tricycle = tricycle;
    }

    //carbon_road;
    public int getCarbon_road() {
        return carbon_road;
    }
    public void setCarbon_road(int carbon_road) {
        this.carbon_road = carbon_road;
    }

    //total_bikes;
    public int getTotal_bikes() {
        return total_bikes;
    }
    public void setTotal_bikes(int total_bikes) {
        this.total_bikes = total_bikes;
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
    public void setKid_trailer(int kid_trailer) {
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

    //locks;
    public int getLocks() {
        return locks;
    }
    public void setLocks(int locks) {
        this.locks = locks;
    }

    //dropoff;
    public int getDropoff() {
        return dropoff;
    }
    public void setDropoff(int dropoff) {
        this.dropoff = dropoff;
    }

}

//
//{
//        "paypal": {
//        "number": "4554889957692955",
//        "type": "visa",
//        "expire_month": "01",
//        "expire_year": "2021",
//        "cvv2": "123",
//        "first_name": "Mar",
//        "last_name": "Singer",
//        "payment_method": "credit_card",
//        "total": "7.0",
//        "currency": "USD"
//        },
//        "pos_tours_orders": {
//        "cashier_email": "reservation@bikerent.nyc",
//        "total_price_before_tax": "6.72",
//        "total_price_after_tax": "7.00",
//        "customer_name": "customer_firstName",
//        "customer_lastname": "customer_lastName",
//        "customer_cc_name": "customer_cc__firstName",
//        "customer_cc_lastname": "customer_cc__lastName",
//        "customer_email": "customer@gmail.com",
//
//        "tour_type": "public(2h)",
//        "tour_place": "Central Park",
//        "adult": 1,
//        "child": 2,
//        "walking": 3,
//        "pedicab": 4,
//        "total_people": 5,
//        "trailer":  6,
//        "kid_trailer": 7,
//        "basket": 8,
//        "seat": 9,
//
//        "insurance": 1
//        }
//        }
//}
