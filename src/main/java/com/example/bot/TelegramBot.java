package com.example.bot;

import com.example.command.AddElementCommand;
import com.example.command.HelpCommand;
import com.example.command.RemoveElementCommand;
import com.example.command.ViewTreeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private HelpCommand helpCommand;

    @Autowired
    private AddElementCommand addElementCommand;

    @Autowired
    private RemoveElementCommand removeElementCommand;

    @Autowired
    private ViewTreeCommand viewTreeCommand;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            try {
                if (messageText.equals("/help")) {
                    String response = helpCommand.execute(new String[]{});
                    sendMessage(chatId, response);
                } else if (messageText.startsWith("/addElement ")) {
                    String[] args = messageText.split(" ", 2);
                    String response = addElementCommand.execute(args.length > 1 ? args[1].split(" ") : new String[]{});
                    sendMessage(chatId, response);
                } else if (messageText.equals("/viewTree")) {
                    String response = viewTreeCommand.execute(new String[]{});
                    sendMessage(chatId, response);
                } else if (messageText.startsWith("/removeElement ")) {
                    String elementName = messageText.substring(15);
                    String response = removeElementCommand.execute(new String[]{elementName});
                    sendMessage(chatId, response);
                } else {
                    sendMessage(chatId, "Некорректная команда. Пожалуйста, используйте /help для просмотра доступных команд.");
                }
            } catch (Exception e) {
                sendMessage(chatId, "Произошла ошибка при обработке вашего запроса. Пожалуйста, попробуйте ещё раз.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
