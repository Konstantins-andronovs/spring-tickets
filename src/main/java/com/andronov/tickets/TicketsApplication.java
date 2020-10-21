package com.andronov.tickets;

import com.andronov.tickets.entities.User;
import com.andronov.tickets.enums.Role;
import com.andronov.tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SpringBootApplication
public class TicketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsApplication.class, args);
    }
}

@Component
class TicketsCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TicketsCommandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        User manager = new User();
        manager.setId(UUID.randomUUID());
        manager.setUsername("username");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.grantAuthority(Role.ROLE_EMPLOYEE);

        userRepository.save(manager);
    }
}
