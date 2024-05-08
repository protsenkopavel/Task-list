package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    void update(User user);

    void create(User user);

    void delete(Long id);

    boolean isTaskOwner(Long userId, Long taskId);

}
