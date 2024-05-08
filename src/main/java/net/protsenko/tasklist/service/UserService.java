package net.protsenko.tasklist.service;

import net.protsenko.tasklist.domain.User;

import java.util.Optional;

public interface UserService {

    User getById(Long id);

    User update(User user);

    User create(User user);

    void delete(Long id);

    User getByName(String name);

    boolean isTaskOwner(Long userId, Long taskId);
}
