package com.lab4.demo.consumption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false,columnDefinition = "TIMESTAMP (6)")
    private Timestamp timestamp;

    @Column(length = 512, nullable = false)
    private Double energy;

    @Column(length = 512, nullable = false)
    private Long deviceId;

    public Consumption(Timestamp timestamp, Double energy, Long device_id) {
        this.timestamp=timestamp;
        this.energy=energy;
        this.deviceId=device_id;
    }
}
