package net.protsenko.tasklist.service;

import net.protsenko.tasklist.domain.User;
import net.protsenko.tasklist.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(User user) {
        userRepository.update(user);
        return user;
    }

    @Override
    public User create(User user) {
        userRepository.create(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
