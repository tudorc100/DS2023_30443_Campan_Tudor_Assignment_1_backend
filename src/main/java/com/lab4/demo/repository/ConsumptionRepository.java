package com.lab4.demo.repository;

import com.lab4.demo.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption,Long> {
}
