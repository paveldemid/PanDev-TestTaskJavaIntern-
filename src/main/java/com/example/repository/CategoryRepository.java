package com.example.repository;

import com.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Интерфейс CategoryRepository предоставляет методы для работы с сущностью Category в базе данных.
 *
 * Этот интерфейс расширяет JpaRepository, что позволяет использовать стандартные методы
 * для выполнения операций CRUD (создание, чтение, обновление, удаление) и добавляет
 * специфические методы для работы с категориями.
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
    boolean existsByName(String name);
}
