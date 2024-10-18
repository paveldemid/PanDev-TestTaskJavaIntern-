package com.example.command;

import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public String execute(String[] args) {
        StringBuilder helpMessage = new StringBuilder();

        helpMessage.append("Доступные команды:\n\n");

        helpMessage.append("Команда: /viewTree\n");
        helpMessage.append("Дерево должно отображаться в структурированном виде.\n\n");

        helpMessage.append("Команда: /addElement <название элемента>\n");
        helpMessage.append("Этот элемент будет корневым, если у него нет родителя.\n\n");

        helpMessage.append("Команда: /addElement <родительский элемент> <дочерний элемент>\n");
        helpMessage.append("Добавление дочернего элемента к существующему элементу. ");
        helpMessage.append("Если родительский элемент не существует, выводить соответствующее сообщение.\n\n");

        helpMessage.append("Команда: /removeElement <название элемента>\n");
        helpMessage.append("При удалении родительского элемента, все дочерние элементы также должны быть удалены. ");
        helpMessage.append("Если элемент не найден, выводить соответствующее сообщение.\n\n");

        helpMessage.append("Команда: /help\n");
        helpMessage.append("Выводит список всех доступных команд и краткое их описание.\n");

        return helpMessage.toString();
    }
}
