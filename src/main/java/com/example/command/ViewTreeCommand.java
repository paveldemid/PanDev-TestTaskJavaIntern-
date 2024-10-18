package com.example.command;

import com.example.model.Category;
import com.example.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static jdk.internal.org.jline.utils.AttributedStringBuilder.append;

@Component
public class ViewTreeCommand implements Command {
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public String execute(String[] args) {
        // Удаляем дубликаты перед отображением
        categoryService.removeDuplicateCategories();


        Set<Category> categories = categoryService.getCategories();
        if (categories.isEmpty()) {
            return "Категории отсутствуют.";
        }
        StringBuilder result = new StringBuilder();
        displayCategories(categories, 0, result, new HashSet<>()); // Передаем пустой набор для отслеживания отображенных категорий
        return result.toString();
    }

    private void displayCategories(Set<Category> categories, int level, StringBuilder result, Set<Category> displayedCategories) {

        for (Category category : categories) {
            // Проверяем, была ли категория уже отображена
            if (!displayedCategories.contains(category)) {
                // Добавляем категорию с отступом в зависимости от уровня
                result.append("  ".repeat(level)).append(category.getName()).append("\n");
                // Добавляем категорию в набор отображенных
                displayedCategories.add(category);

                // Если у категории есть подкатегории, рекурсивно отображаем их
                if (!category.getSubcategories().isEmpty()) {
                    displayCategories(category.getSubcategories(), level + 1, result, displayedCategories);
                }
            }
        }
    }
}
