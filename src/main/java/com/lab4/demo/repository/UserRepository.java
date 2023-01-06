package com.lab4.demo.repository;

import com.lab4.demo.model.Role;
import com.lab4.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findAllByRolesContaining(Optional<Role> roles);


}
