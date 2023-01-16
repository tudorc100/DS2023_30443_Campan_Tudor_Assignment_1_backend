package com.lab4.demo.controller;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.lab4.demo.UrlMapping;
import com.lab4.demo.dtos.UserDTO;
import com.lab4.demo.model.Message;
import com.lab4.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }
    @DeleteMapping(USERS_ID_PART)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
    @PatchMapping(USERS_ID_PART)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.edit(id, user);
    }
    @CrossOrigin
    @PostMapping(UrlMapping.TOUSERNAME)
    @JsonRawValue
    public void sendMessage(@PathVariable String toUsername, @RequestBody Message message) throws Exception {
        userService.sendMessage(toUsername, message);
    }
}
