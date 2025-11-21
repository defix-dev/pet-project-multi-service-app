package ru.defix.auth.service.registration;

import ru.defix.auth.service.dto.RegistrationDetails;

public interface RegistrationService {
    void register(RegistrationDetails details);
}
