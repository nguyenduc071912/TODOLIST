package org.example.todolist.Service;

import org.example.todolist.Entity.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAll();
    Todo getById(Long id);
    Todo createTodo(Todo todo);
    Todo updateTodo(Todo todo,Long id);
    Todo deleteTodo(Long id);
}
