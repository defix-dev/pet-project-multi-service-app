package ru.defix.chat.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatKeysCreationDetails {
    private int userId;
    private String privateKey;
    private String publicKey;
}
