package net.protsenko.tasklist.service;

import net.protsenko.tasklist.domain.User;
import net.protsenko.tasklist.domain.exceptions.ResourceNotFoundException;
import net.protsenko.tasklist.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO wtf?
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    //TODO wtf?
    @Override
    public User getByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    public User create(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        if (passwordEncoder.matches(user.getPassword(), user.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.create(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }
}
