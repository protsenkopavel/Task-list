package net.protsenko.tasklist.controller;

import lombok.RequiredArgsConstructor;
import net.protsenko.tasklist.domain.Task;
import net.protsenko.tasklist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @PutMapping
    public Task updateTask() {
        return null;
    }

    @PutMapping
    public User updateUser() {
        return null;
    }

    @PostMapping("/{id}")
    public Task createTask(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    public List<Task> getTasksByUserId(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {

    }

}
