package com.example.demo.repository;

import com.example.demo.model.Pos_rents_orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosRentsOrdersRepository extends JpaRepository<Pos_rents_orders, Integer> {
    PosRentsOrdersRepository findById(int id);
}
