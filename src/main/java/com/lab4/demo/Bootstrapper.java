package com.lab4.demo;

import com.lab4.demo.device.DeviceRepository;
import com.lab4.demo.device.model.Device;
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

    @Value("false")
    private Boolean bootstrap;

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
            deviceRepository.save(Device.builder().description("Tilivizor")
                            .address("Marinescu")
                            .consumption(700)
                            .userId(10L)
                    .build());
            deviceRepository.save(Device.builder().description("Tilivizor Doi")
                    .address("Marinescu Doi")
                    .consumption(700)
                    .userId(10L)
                    .build());
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
            deviceRepository.save(Device.builder().description("Tilivizor")
                    .address("Marinescu")
                    .consumption(700)
                    .userId(userRepository.findAll().get(1).getId())
                    .build());
            deviceRepository.save(Device.builder().description("Tilivizor Doi")
                    .address("Marinescu Doi")
                    .consumption(700)
                    .userId(userRepository.findAll().get(1).getId())
                    .build());
        }
    }
}
