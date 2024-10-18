package com.example;

import com.example.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Главный класс приложения, запускающий Telegram-бота.
 *
 * Класс RunningBot инициализирует контекст Spring и регистрирует экземпляр
 * Telegram-бота в TelegramBotsApi. Это основной класс, с которого начинается
 * выполнение приложения.
 */

@SpringBootApplication
public class RunningBot {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RunningBot.class, args);

        // Получаем экземпляр бота из контекста Spring
        TelegramBot bot = context.getBean(TelegramBot.class);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}