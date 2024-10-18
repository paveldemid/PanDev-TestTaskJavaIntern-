package com.example.command;

import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddElementCommand implements Command {
    @Autowired
    private CategoryService categoryService;

    @Override
    public String execute(String[] args) {
        // Исправленная проверка количества аргументов
        if (args.length < 1 || args.length > 2) {
            return "Неверное количество аргументов. Используйте: /addElement <родительский элемент> <дочерний элемент> или /addElement <название элемента> для корневого.";
        }

        String parentName = args.length == 2 ? args[0] : null; // Первый аргумент - родитель
        String name = args.length == 2 ? args[1] : args[0]; // Второй аргумент - дочерний или корневой

        // Проверка на пустое имя
        if (name == null || name.isEmpty()) {
            return "Название элемента не может быть пустым.";
        }

        boolean success = categoryService.addCategory(name, parentName);
        if (success) {
            return "Элемент '" + name + "' успешно добавлен.";
        } else {
            return "Родительский элемент '" + parentName + "' не найден.";
        }
    }
}
