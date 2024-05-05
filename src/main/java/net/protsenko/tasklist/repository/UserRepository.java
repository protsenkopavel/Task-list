package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.domain.User;

public interface UserRepository {

    User findById(Long id);

    void update(User user);

    void create(User user);

    void delete(Long id);

}
