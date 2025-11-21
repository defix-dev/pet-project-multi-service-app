package ru.defix.auth.service.authentication;

import ru.defix.auth.api.dto.request.Credentials;

public interface AuthenticationService {
    String authenticate(Credentials credentials);
}
