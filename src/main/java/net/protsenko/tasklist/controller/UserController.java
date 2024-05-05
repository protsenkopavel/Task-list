package net.protsenko.tasklist.controller;

import lombok.RequiredArgsConstructor;
import net.protsenko.tasklist.domain.Task;
import net.protsenko.tasklist.domain.User;
import net.protsenko.tasklist.service.TaskService;
import net.protsenko.tasklist.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    private final TaskService taskService;
    private final UserService userService;

    public MainController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PutMapping
    public Task updateTask(@RequestBody Task task) {
        return taskService.update(task);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/{id}/tasks")
    public Task createTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.create(task, id);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasksByUserId(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.delete(id);
    }

}
