package net.protsenko.tasklist.controller;

import net.protsenko.tasklist.domain.Task;
import net.protsenko.tasklist.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PutMapping
    public Task updateTask(@RequestBody Task task) {
        return taskService.update(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id) {
        taskService.delete(id);
    }
}
