package com.example.command;

import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveElementCommand implements Command {
    @Autowired
    private CategoryService categoryService;

    @Override
    public String execute(String[] args) {
        if (args.length < 1) {
            return "Укажите название элемента для удаления.";
        }

        String elementName = args[0];
        boolean success = categoryService.removeCategory(elementName);

        if (success) {
            return "Элемент '" + elementName + "' успешно удален.";
        } else {
            return "Элемент '" + elementName + "' не найден.";
        }
    }
}
