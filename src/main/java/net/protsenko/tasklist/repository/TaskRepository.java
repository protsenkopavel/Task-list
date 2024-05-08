package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);

    void assignTaskToUser(Long taskId, Long userId);

}
