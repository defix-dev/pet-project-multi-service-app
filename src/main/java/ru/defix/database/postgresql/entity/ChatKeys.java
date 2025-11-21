package ru.defix.database.postgresql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_keys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatKeys {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "public_key")
    private String publicKey;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
