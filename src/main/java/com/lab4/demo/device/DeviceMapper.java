package com.lab4.demo.device;

import com.lab4.demo.device.model.Device;
import com.lab4.demo.device.model.dto.DeviceDTO;
import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface DeviceMapper {

        DeviceDTO toDto(Device device);

        Device fromDto(DeviceDTO item);

}
