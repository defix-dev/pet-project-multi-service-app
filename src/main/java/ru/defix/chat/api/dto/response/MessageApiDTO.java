package ru.defix.chat.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageApiDTO {
    private String message;
    private LocalDateTime createdAt;
    private int senderId;
}
