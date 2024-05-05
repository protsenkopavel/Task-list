package net.protsenko.tasklist.service;

import net.protsenko.tasklist.domain.Task;
import net.protsenko.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public Task update(Task task) {
        taskRepository.update(task);
        return task;
    }

    @Override
    public Task create(Task task, Long userId) {
        taskRepository.create(task);
        return task;
    }

    @Override
    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
