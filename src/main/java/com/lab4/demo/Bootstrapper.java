package com.lab4.demo;

import com.lab4.demo.device.DeviceRepository;
import com.lab4.demo.device.DeviceService;
import com.lab4.demo.device.model.Device;
import com.lab4.demo.listener.QueueConsumer;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//import java.net.http.HttpResponse;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final DeviceRepository deviceRepository;

    private final DeviceService deviceService;



    @Value("true")
    private Boolean bootstrap;

    @Value("true")
    private Boolean read;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            deviceRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("tudor@gmail.com")
                    .username("tudor")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("tudor1@gmail.com")
                    .username("tudor1")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("tudor11@gmail.com")
                    .username("tudor11")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            deviceRepository.deleteAll();
            deviceRepository.save(Device.builder().id(10L).description("Tilivizor")
                    .address("Marinescu")
                    .consumption(700)
                    .userId(userRepository.findAll().get(1).getId())
                    .build());
            deviceRepository.save(Device.builder().id(11L).description("Tilivizor Doi")
                    .address("Marinescu Doi")
                    .consumption(700)
                    .userId(userRepository.findAll().get(2).getId())
                    .build());
        }
        if (read)
        {
            QueueConsumer q=new QueueConsumer(deviceService);
            try {
                q.readMessages();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
