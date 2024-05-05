package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.domain.Task;

import java.util.List;

public interface TaskRepository {

    Task findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);

}
