package org.example.todolist.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.User;
import org.example.todolist.Repository.UserRepository;
import org.example.todolist.Service.AuthService;
import org.example.todolist.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        user.setId(null);

        String oncodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(oncodedPassword);
        return userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public User updateUser(User user, Long id) {
        return userRepository.findById(id)
                .map(user1 -> {
                    if (user.getUsername() != null) user1.setUsername(user.getUsername());
                    if (user.getPassword() != null) user1.setPassword(passwordEncoder.encode(user.getPassword()));

                    return userRepository.save(user1);
                }).orElse(null);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public User deleteUser(Long id) {
        User deleteUser = getById(id);
        if (deleteUser != null){
            userRepository.deleteById(id);
        }
        return deleteUser;
    }

    public boolean isOwner(Long id){
        String currentUsername = authService.getUsername();

        return userRepository.findById(id)
                .map(User::getUsername).orElse("").equals(currentUsername);
    }
}
