package org.example.todolist.Controller;

import lombok.RequiredArgsConstructor;
import org.example.todolist.Entity.Todo;
import org.example.todolist.Service.TodoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/todos")
@RequiredArgsConstructor
public class TodoWebController {
    private final TodoService todoService;


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String listTodos(Model model) {
        List<Todo> todos = todoService.getAll();
        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", new Todo());

        return "todos";
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String saveTodo(@ModelAttribute("newTodo") Todo todo) {
        todoService.createTodo(todo);
        return "redirect:/web/todos";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("todo", todoService.getById(id));
        return "edit-todo";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public String updateTodo(@PathVariable("id") Long id, @ModelAttribute("todo") Todo todo) {
        todoService.updateTodo(todo, id);
        return "redirect:/web/todos";
    }

    @GetMapping("/toggle/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public String toggleTodoCompletion(@PathVariable Long id) {
        Todo todo = todoService.getById(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            todoService.updateTodo(todo, id);
        }
        return "redirect:/web/todos";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(#id)")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/web/todos";
    }

}
