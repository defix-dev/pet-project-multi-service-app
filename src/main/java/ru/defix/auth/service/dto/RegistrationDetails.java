package ru.defix.auth.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDetails {
    @Size(min = 3, max = 24)
    @NotEmpty
    private String username;

    @Size(min = 8, max = 255)
    @NotEmpty
    private String password;
}
