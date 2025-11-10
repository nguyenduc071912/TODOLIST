package org.example.todolist.Service;

import org.example.todolist.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User createUser(User user);
    User updateUser(User user, Long id);
    User deleteUser(Long id);
}
