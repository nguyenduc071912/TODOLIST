package org.example.todolist.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.Todo;
import org.example.todolist.Entity.User;
import org.example.todolist.Repository.TodoRepository;
import org.example.todolist.Repository.UserRepository;
import org.example.todolist.Service.AuthService;
import org.example.todolist.Service.TodoService;
import org.example.todolist.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Todo> getAll() {
        String currentUsername = authService.getUsername();
        return todoRepository.findAllByOwnerUsername(currentUsername);
    }

    @Override
    public Todo getById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public Todo createTodo(Todo todo) {
        String currentUsername = authService.getUsername();

        User owner = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found in DB after authentication."));

        todo.setUser(owner);
        todo.setOwnerUsername(authService.getUsername());
        todo.setId(null);
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Todo todo, Long id) {
        return todoRepository.findById(id).map(todo1 -> {
            if (todo.getTitle() != null) todo1.setTitle(todo.getTitle());
            if (todo.getDescription() != null) todo1.setDescription(todo.getDescription());
            todo1.setCompleted(todo.isCompleted());
            if (todo.getUser() != null) todo1.setUser(todo.getUser());
            if (todo.getOwnerUsername() != null) todo1.setOwnerUsername(todo.getOwnerUsername());

            return todoRepository.save(todo1);
        }).orElse(null);
    }

    @Override
    public Todo deleteTodo(Long id) {
        Todo deleteTodo = getById(id);
        if (deleteTodo != null){
            todoRepository.deleteById(id);
        }
        return deleteTodo;
    }


}
