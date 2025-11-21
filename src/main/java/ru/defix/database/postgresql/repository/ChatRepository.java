package ru.defix.database.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.defix.database.postgresql.entity.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("SELECT COUNT(c) > 0 FROM Chat c " +
            "JOIN c.members m1 " +
            "JOIN c.members m2 " +
            "WHERE m1.id = :userA AND m2.id = :userB")
    boolean hasChat(@Param("userA") int userA, @Param("userB") int userB);

    @Query("SELECT c FROM Chat c " +
            "JOIN c.members m1 " +
            "JOIN c.members m2 " +
            "WHERE m1.id = :userA AND m2.id = :userB")
    Chat getChat(@Param("userA") int userA, @Param("userB") int userB);

    @Query("SELECT c FROM Chat c JOIN c.members m WHERE m.id = :userId ORDER BY c.createdAt DESC")
    List<Chat> getSortedChatsByUserId(@Param("userId") int userId);
}
