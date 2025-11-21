package ru.defix.chat.service.provider;

public interface BiProvider<RT, IT> {
    RT provide(IT v1, IT v2);
}
