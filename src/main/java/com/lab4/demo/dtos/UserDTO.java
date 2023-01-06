package com.lab4.demo.dtos;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
