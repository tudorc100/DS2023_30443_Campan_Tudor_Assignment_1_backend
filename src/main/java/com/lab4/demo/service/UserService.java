package com.lab4.demo.service;

import com.lab4.demo.model.Message;
import com.lab4.demo.repository.RoleRepository;
import com.lab4.demo.repository.UserRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.dtos.UserDTO;
import com.lab4.demo.model.mapper.UserMapper;
import com.lab4.demo.model.ERole;
import com.lab4.demo.model.Role;
import com.lab4.demo.model.User;
import com.lab4.demo.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    private final RoleRepository roleRepository;

    private final WebSocketService webSocketService;

    public List<UserDTO> findAll() {
        Optional<Role> defaultRole = roleRepository.findByName(ERole.CUSTOMER);
        return userRepository.findAllByRolesContaining(defaultRole).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserDTO create(UserDTO userDTO){

        authService.register(SignupRequest.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(Set.of("CUSTOMER"))
                .build());

        return userDTO;
    }
    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }
    public UserDTO edit(Long id, UserDTO userDTO) {
        User actUser = findById(id);
        actUser.setUsername(userDTO.getUsername());
        actUser.setEmail(userDTO.getEmail());

        userRepository.save(actUser);
        return userMapper.toDto(actUser);
    }

    public Boolean existsById(Long id)
    {
        return userRepository.existsById(id);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + username));
    }
    public void sendMessage(String username, Message message) throws Exception {
        User userToSendTo=findByUsername(username);
        webSocketService.notification(message, userToSendTo.getId());

    }
}
