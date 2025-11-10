package org.example.todolist.Controller;

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.Todo;
import org.example.todolist.Service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping()
    public ResponseEntity<List<Todo>> getAll() {
        List<Todo> all = todoService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Todo> getById(@PathVariable("id") Long id) {
        Todo todos = todoService.getById(id);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo todos = todoService.createTodo(todo);
        return new ResponseEntity<>(todos, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable("id") Long id) {
        Todo todos = todoService.updateTodo(todo, id);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable("id") Long id) {
        Todo todos = todoService.deleteTodo(id);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
}
