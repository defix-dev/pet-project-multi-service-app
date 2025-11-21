package ru.defix.auth.service.registration;

import ru.defix.auth.service.dto.RegistrationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.defix.user.service.UserService;
import ru.defix.database.postgresql.entity.Role;
import ru.defix.database.postgresql.entity.User;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final static Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationServiceImpl(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public void register(RegistrationDetails details) {
        User user = new User();
        user.setUsername(details.getUsername());
        user.setPassword(encoder.encode(details.getPassword()));
        user.setRoles(Collections.singleton(new Role(1, "USER")));

        userService.createUser(user);
        logger.debug("User registered with id: {}", user.getId());
    }
}
