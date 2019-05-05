package com.example.demo.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "bikerentnyc_users")

public class Users {

    @Id
    private int id;
    private String email;
    private String first_name;
    private String last_name;

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

    //email
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }


    //first name
    public String getFirst_name() {

        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }


    //last name
    public String getLast_name() {

        return last_name;
    }

    public void setLastName(String last_name) {

        this.last_name = last_name;
    }

}


//@Entity
//@Table(name = "users")
//public class Employee implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "address")
//    private String address;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "dept_id", insertable = false, updatable = false)
////    @Fetch(FetchMode.JOIN)
////    private Department department;
//
//    //getters and setters
//}
