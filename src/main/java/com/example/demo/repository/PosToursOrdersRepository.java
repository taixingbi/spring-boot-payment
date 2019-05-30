package com.example.demo.repository;

import com.example.demo.model.Pos_tours_orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosToursOrdersRepository  extends JpaRepository<Pos_tours_orders, Integer>{
    //PosToursOrdersRepository findById(int id);

}

