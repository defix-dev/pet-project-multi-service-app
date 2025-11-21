package ru.defix.database.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.defix.database.postgresql.entity.ChatKeys;

@Repository
public interface ChatKeysRepository extends JpaRepository<ChatKeys, Integer> {
}
