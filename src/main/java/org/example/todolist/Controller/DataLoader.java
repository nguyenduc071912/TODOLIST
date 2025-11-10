package org.example.todolist.Controller;

// DataLoader.java

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.User;
import org.example.todolist.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin@test.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin@test.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(admin);
            System.out.println("Tạo tài khoản ADMIN mặc định: admin@test.com / 123456");
        }
        if (userRepository.findByUsername("user@test.com").isEmpty()) {
            User user = new User();
            user.setUsername("user@test.com");
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Tạo tài khoản USER mặc định: user@test.com / 123456");
        }
    }
}
