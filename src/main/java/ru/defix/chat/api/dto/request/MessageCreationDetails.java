package ru.defix.chat.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreationDetails {
    private int senderId;
    private int destinationId;
    private String message;
}
