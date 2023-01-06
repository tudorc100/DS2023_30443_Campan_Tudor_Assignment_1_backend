package com.lab4.demo.model.mapper;

import com.lab4.demo.model.Device;
import com.lab4.demo.dtos.DeviceDTO;
import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface DeviceMapper {

        DeviceDTO toDto(Device device);

        Device fromDto(DeviceDTO item);

}
