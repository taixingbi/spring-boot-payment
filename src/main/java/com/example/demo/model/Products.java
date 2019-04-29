package com.example.demo.model;
import javax.persistence.*;

@Entity
@Table(name = "bikerentnyc_products")

public class Products {

    @Id
    private int id;
    private String name;
    private String price;

    //id
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

    //Name
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    //price
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}


