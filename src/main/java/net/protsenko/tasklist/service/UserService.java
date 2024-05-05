package net.protsenko.tasklist.service;

import net.protsenko.tasklist.domain.User;

public interface UserService {

    User getById(Long id);

    User update(User user);

    User create(User user);

    void delete(Long id);
}
