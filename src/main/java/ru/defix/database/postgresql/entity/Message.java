package ru.defix.database.postgresql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "message")
    private String message;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "chat_id")
    @ManyToOne
    private Chat chat;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
