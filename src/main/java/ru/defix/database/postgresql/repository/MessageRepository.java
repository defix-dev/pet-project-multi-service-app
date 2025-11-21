package ru.defix.database.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.defix.database.postgresql.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.chat.id=:chatId ORDER BY m.createdAt DESC LIMIT 1")
    Message getLastMessageByChatId(@Param("chatId") int chatId);

    @Query("SELECT m FROM Message m WHERE m.chat.id=:chatId ORDER BY m.createdAt ASC")
    List<Message> getSortedMessagesByChatId(@Param("chatId") int chatId);
}
