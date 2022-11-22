package com.lab4.demo.device.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DeviceDTO {

    private Long id;

    private String description;

    private String address;

    private Integer consumption;

    private Long userId;
}
