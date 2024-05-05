package net.protsenko.tasklist.repository;

import net.protsenko.tasklist.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void delete(Long id) {

    }

}
