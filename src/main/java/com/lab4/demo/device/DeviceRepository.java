package com.lab4.demo.device;

import com.lab4.demo.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
List<Device> findAllByUserIdEquals(Long id);


}
