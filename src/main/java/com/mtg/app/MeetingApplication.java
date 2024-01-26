package com.mtg.app;

import com.mtg.app.models.Role;
import com.mtg.app.models.User;
import com.mtg.app.repository.RoleRepository;
import com.mtg.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class MeetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

            Role adminRole = roleRepository.save(Role.builder().authority("ADMIN").build());
            roleRepository.save(Role.builder().authority("USER").build());

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            userRepository.save(User.builder()
                    .userId(UUID.randomUUID())
                    .username("admin")
                    .email("admin@test.com")
                    .password(encoder.encode("123456"))
                    .authorities(roles)
                    .build());
        };
    }
}
