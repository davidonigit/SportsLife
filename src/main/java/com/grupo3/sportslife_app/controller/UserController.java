package com.grupo3.sportslife_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo3.sportslife_app.dto.CreateUserDTO;
import com.grupo3.sportslife_app.dto.UserDTO;
import com.grupo3.sportslife_app.model.GoalBoard;
import com.grupo3.sportslife_app.model.User;
import com.grupo3.sportslife_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO body) {
        // Verifica se o email já existe
        if (userRepository.existsByEmail(body.email())) {
            return ResponseEntity.badRequest().build();
        }

        // Cria o usuário com as roles
        GoalBoard board = new GoalBoard();
        User user = new User(null, body.name(), body.email(), passwordEncoder.encode(body.password()), board);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(body.password()));
        user.setName(body.name());
        user.setEmail(body.email());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    
    String email = (String) authentication.getPrincipal();
    User currentUser = userRepository.findByEmail(email)
                         .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    
    //Bloqueia user de se autoexcluir
    //if (currentUser.getId().equals(id)) {
    //   return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    //}
    
    if (!userRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    
    userRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}
}