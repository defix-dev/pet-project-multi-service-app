package ru.defix.chat.service.creator;

public interface ReturnedCreator<RT, IT> {
    RT create(IT v);
}
