package com.lab4.demo.device;

import com.lab4.demo.device.model.dto.DeviceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@CrossOrigin
@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<DeviceDTO> allItems() {
        return deviceService.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public DeviceDTO create(@RequestBody DeviceDTO item) {
        return deviceService.create(item);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(ITEMS_ID_PART)
    public void delete(@PathVariable Long id){
        deviceService.delete(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(ITEMS_ID_PART)
    public DeviceDTO edit(@PathVariable Long id, @RequestBody DeviceDTO item) {
        return deviceService.edit(id, item);
    }
    @GetMapping(ITEMS_ID_PART)
    public List<DeviceDTO> allItemsForUser(@PathVariable Long id) {
        return deviceService.getDevicesForUser(id);
    }



}
