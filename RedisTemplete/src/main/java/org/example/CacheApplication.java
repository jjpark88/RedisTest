package org.example;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Client;
import org.example.domain.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class CacheApplication implements ApplicationRunner {
    private final UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    @GetMapping("/")
    String home() {
        return "OK";
    }
}
