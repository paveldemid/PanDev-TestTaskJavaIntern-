package com.example.command;

import org.springframework.stereotype.Component;

/**
 * Интерфейс Command определяет контракт для выполнения команд в приложении.
 *
 * Каждая команда должна реализовать метод execute, который принимает массив аргументов
 * и возвращает строку с результатом выполнения команды.
 */

@Component
public interface Command {
    String execute(String[] args);
}
