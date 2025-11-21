package ru.defix.user.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.defix.database.postgresql.entity.User;
import ru.defix.database.postgresql.repository.UserRepository;
import ru.defix.user.exception.UserAlreadyExistException;
import ru.defix.user.exception.UserNotFoundException;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username));
        if(user.isEmpty()) throw new UsernameNotFoundException("Username not found.");
        logger.debug("User has been loaded: {}", username);
        return user.get();
    }

    public User findById(int id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException();
        logger.debug("User has been founded: {}", user.get().getUsername());
        return user.get();
    }

    public User findByUsername(String username) {
        Optional<User> user = Optional
                .ofNullable(userRepo.findByUsername(username));
        if(user.isEmpty()) throw new UserNotFoundException();
        logger.debug("User has been finded: {}", user.get().getUsername());
        return user.get();
    }

    public void createUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException();
        }
        userRepo.save(user);
        logger.debug("User created with id={}", user.getId());
    }
}
