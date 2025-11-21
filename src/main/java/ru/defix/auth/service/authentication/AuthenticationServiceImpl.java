package ru.defix.auth.service.authentication;

import ru.defix.auth.service.registration.RegistrationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.defix.auth.api.dto.request.Credentials;
import ru.defix.auth.service.jwt.JwtGenerator;
import ru.defix.auth.exception.UsernameOrPasswordInvalidException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final AuthenticationManager authManager;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String authenticate(Credentials credentials) {
        if(authManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        )) == null) {
            logger.error("Failed to authenticate user: {}", credentials.getUsername());
            throw new UsernameOrPasswordInvalidException();
        }
        return JwtGenerator.generateAccessToken(credentials.getUsername());
    }
}
