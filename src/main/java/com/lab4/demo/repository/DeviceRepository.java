package com.lab4.demo.repository;

import com.lab4.demo.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
List<Device> findAllByUserIdEquals(Long id);


}
