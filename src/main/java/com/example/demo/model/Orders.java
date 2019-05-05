package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "agent_tours_orders_test")

public class Orders {
    @Id
    private int id;
    private String order_id;
    private String agent_email;
    private int order_completed;
    private String payment_type;

    private double total_price_before_tax;
    private double total_price_after_tax;
    private double agent_price_after_tax;

    private String tour_type;
    private String date;
    private String time;
    private String customer_name;
    private String customer_lastname;
    private String customer_email;

    private String comment;
    private String barcode;

    private int adult;
    private int child;
    private int total_people;
    private int served;

    //@CreationTimestamp
    //private LocalDateTime created_at;
    @Basic
    private LocalDateTime  created_at;
    private LocalDateTime  completed_at;
    private LocalDateTime  served_date;

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

    //order_id
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    //agent_email
    public String getAgent_email() {
        return agent_email;
    }
    public void setAgent_email(String agent_email) {
        this.agent_email = agent_email;
    }

    //order_completed
    public int getOrder_completed() {
        return order_completed;
    }
    public void setOrder_completed(int order_completed) {
        this.order_completed = order_completed;
    }

    //payment_type
    public String getPayment_type() {
        return payment_type;
    }
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    //total_price_before_tax
    public double getTotal_price_before_tax() {
        return total_price_before_tax;
    }
    public void setTotal_price_before_tax(double total_price_before_tax) {
        this.total_price_before_tax = total_price_before_tax;
    }

    //total_price_after_tax
    public double getTotal_price_after_tax() {
        return total_price_after_tax;
    }
    public void setTotal_price_after_tax(double total_price_after_tax) {
        this.total_price_after_tax = total_price_after_tax;
    }

    //agent_price_after_tax
    public double getAgent_price_after_tax() {
        return agent_price_after_tax;
    }
    public void seTagent_price_after_tax(double agent_price_after_tax) {
        this.agent_price_after_tax = agent_price_after_tax;
    }

    //tour_type;
    public String getTour_type() {
        return tour_type;
    }
    public void setTour_type(String tour_type) {
        this.tour_type = tour_type;
    }
    //date;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    //time;
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    //customer_name;
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    //customer_lastname;
    public String getCustomer_lastname() {
        return customer_lastname;
    }
    public void setCustomer_lastname(String customer_lastname) {
        this.customer_lastname = customer_lastname;
    }

    //customer_email;
    public String getCustomer_email() {
        return customer_email;
    }
    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }
    //comment;
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    //barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setbarcode(String barcode) {
        this.barcode = barcode;
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

    //total_people;
    public int getTotal_people() {
        return total_people;
    }
    public void setTotal_people(int total_people) {
        this.total_people = total_people;
    }

    //served;
    public int getServed() {
        return served;
    }
    public void setServed(int served) {
        this.served = served;
    }

    //created_at
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    //completed_at;
    public LocalDateTime getCompleted_at() {
        return completed_at;
    }
    public void setCompleted_at(LocalDateTime completed_at) {
        this.completed_at = completed_at;
    }

    //served_date;
    public LocalDateTime getServed_date() {
        return served_date;
    }
    public void setServed_date(LocalDateTime served_date) {
        this.served_date = served_date;
    }

}