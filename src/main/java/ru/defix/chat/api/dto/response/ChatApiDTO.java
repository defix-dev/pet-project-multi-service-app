package ru.defix.chat.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatApiDTO {
    private String targetUsername;
    private int targetId;
    private String lastMessage;
    private LocalDateTime localDateTime;
}
