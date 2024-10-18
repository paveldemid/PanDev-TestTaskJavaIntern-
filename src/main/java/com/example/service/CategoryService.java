package com.example.service;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Сервисный класс CategoryService предоставляет методы для управления категориями.
 *
 * Этот класс использует репозиторий CategoryRepository для выполнения операций
 * добавления, удаления и получения категорий из базы данных. Он также включает
 * методы для обработки дубликатов категорий.
 */

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Set<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return new HashSet<>(categories);
    }

    @Transactional
    public boolean addCategory(String name, String parentName) {
        // Проверяем на дубликаты
        if (categoryRepository.existsByName(name)) {
            return false; // Дубликат найден
        }

        // Создаем новую категорию
        Category category = new Category();
        category.setName(name);

        // Если передано имя родительской категории
        if (parentName != null && !parentName.isEmpty()) {
            Optional<Category> parentOpt = categoryRepository.findByName(parentName).stream().findFirst();
            if (parentOpt.isPresent()) {
                Category parent = parentOpt.get();
                category.setParent(parent);
                parent.addChild(category); // Добавляем дочернюю категорию к родителю

                // Сохраняем изменения в родительской категории
                categoryRepository.save(parent);
            } else {
                return false; // Родительский элемент не найден
            }
        }

        // Сохраняем новую категорию
        categoryRepository.save(category);
        return true; // Успешно добавлено
    }

    @Transactional
    public boolean removeCategory(String name) {
        List<Category> categories = categoryRepository.findByName(name);
        if (!categories.isEmpty()) {
            for (Category category : categories) {
                // Если есть дочерние категории, переназначаем их родителя или удаляем
                for (Category child : category.getSubcategories()) {
                    child.setParent(null); // Или установите другого родителя
                }
                categoryRepository.delete(category);
            }
            return true; // Успешно удалено
        }
        return false; // Категория не найдена
    }


    public Set<Category> getCategories() {
        return new HashSet<>(categoryRepository.findAll()); // Преобразуем List в Set
    }

    @Transactional
    public void removeDuplicateCategories() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Category> uniqueCategories = new HashMap<>();

        for (Category category : categories) {
            if (uniqueCategories.containsKey(category.getName())) {
                // Удаляем дубликат
                categoryRepository.delete(category);
            } else {
                uniqueCategories.put(category.getName(), category);
            }
        }
    }
}
