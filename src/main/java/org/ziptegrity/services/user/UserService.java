package org.ziptegrity.services.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ziptegrity.database.postgresql.entities.User;
import org.ziptegrity.database.postgresql.repositories.UserRepository;
import org.ziptegrity.services.user.exceptions.UserAlreadyExistException;
import org.ziptegrity.services.user.exceptions.UserNotFoundException;

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
        logger.debug("User has been loaded: "+username);
        return user.get();
    }

    public User findById(int id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException();
        logger.debug("User has been finded: "+user.get().getUsername());
        return user.get();
    }

    public User findByUsername(String username) {
        Optional<User> user = Optional
                .ofNullable(userRepo.findByUsername(username));
        if(user.isEmpty()) throw new UserNotFoundException();
        logger.debug("User has been finded: "+user.get().getUsername());
        return user.get();
    }

    public void createUser(User user) {
        if (userRepo.findById(user.getId()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        userRepo.save(user);
        logger.debug("User created with id="+user.getId());
    }

    public boolean isUserExist(int id) {
        return userRepo.existsById(id);
    }
}
