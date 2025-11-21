

-- Таблица ролей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(64)
);

-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(64) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Связующая таблица для пользователей и ролей
CREATE TABLE users_roles (
                             user_id INT NOT NULL,
                             role_id INT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Таблица чатов
CREATE TABLE chats (
                       id SERIAL PRIMARY KEY,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Связующая таблица для пользователей и чатов
CREATE TABLE users_chats (
                             chat_id INT NOT NULL,
                             user_id INT NOT NULL,
                             PRIMARY KEY (chat_id, user_id),
                             CONSTRAINT fk_users_chats_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
                             CONSTRAINT fk_users_chats_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица ключей для чатов
CREATE TABLE chat_keys (
                           user_id INT PRIMARY KEY,
                           private_key TEXT,
                           public_key TEXT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_chat_keys_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
