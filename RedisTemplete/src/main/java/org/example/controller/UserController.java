package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Client;
import org.example.domain.entity.RedisHashUser;
import org.example.domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public Client getUser(@PathVariable Long id) {
        return userService.getUser3(id);
    }

    @GetMapping("/redishash-users/{id}")
    public RedisHashUser getUser2(@PathVariable Long id) {
        return userService.getUser2(id);
    }
}
