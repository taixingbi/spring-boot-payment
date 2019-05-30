package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Pos_orders {
    @Id
    private int id;
    private String order_id;

    private String cashier_email;

    private double total_price_before_tax;
    private double total_price_after_tax;

//    @NotNull
    private String customer_name;
    private String customer_lastname;
    private String customer_cc_name;
    private String customer_cc_lastname;
    //
    private String customer_email;
    //
    private int order_completed;
    private String payment_type;
//

    private int reservation;
    //
    private String created_at;
    private String date;
    private String time;

    private int insurance;
    private String barcode;
    private int served;
    private String sequantial;

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

    //cashier_email
    public String getCashier_email() {
        return cashier_email;
    }
    public void setCashier_email(String cashier_email) {
        this.cashier_email = cashier_email;
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

    public String getCustomer_cc_name() {
        return customer_cc_name;
    }
    public void setCustomer_cc_name(String customer_cc_name) {
        this.customer_cc_name = customer_cc_name;
    }

    //customer_cc_lastname;
    public String getCustomer_cc_lastname() {
        return customer_cc_lastname;
    }
    public void setCustomer_cc_lastname(String customer_cc_lastname) {
        this.customer_cc_lastname = customer_cc_lastname;
    }

    //customer_email;
    public String getCustomer_email() {
        return customer_email;
    }
    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
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

    //reservation;
    public int getReservation() {
        return reservation;
    }
    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    //created_at
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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


    //insurance;
    public int getInsurance() {
        return insurance;
    }
    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    //barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    //
    //served;
    public int getServed() {
        return served;
    }
    public void setServed(int served) {
        this.served = served;
    }

    //sequantial
    public String getSequantial() {
        return sequantial;
    }
    public void setSequantial(String sequantial) {
        this.sequantial = sequantial;
    }

}