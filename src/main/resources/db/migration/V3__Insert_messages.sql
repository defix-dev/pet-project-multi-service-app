CREATE TABLE messages (
                          id SERIAL PRIMARY KEY,
                          message TEXT,
                          sender_id INTEGER,
                          chat_id INTEGER,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_messages_user
                              FOREIGN KEY (sender_id) REFERENCES users(id),

                          CONSTRAINT fk_messages_chat
                              FOREIGN KEY (chat_id) REFERENCES chats(id)
);