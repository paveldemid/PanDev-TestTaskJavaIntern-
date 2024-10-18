package com.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор категории

    private String name; // Название категории

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent; // Родительская категория

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<>(); // Подкатегории

    public Category() {
        // Конструктор по умолчанию
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id != null && id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Проверка на null или пустую строку
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя категории не может быть пустым.");
        }
        this.name = name;
    }

    public void setParent(Category parent) {
        // Проверка на циклическую зависимость родительских категорий
        if (parent == this) {
            throw new IllegalArgumentException("Категория не может быть родителем самой себя.");
        }
        this.parent = parent;
    }

    public Set<Category> getSubcategories() {
        return children;
    }

    public void addChild(Category child) {
        // Проверка на null и предотвращение добавления дубликатов
        if (child == null) {
            throw new IllegalArgumentException("Подкатегория не может быть null.");
        }
        if (!children.contains(child)) {
            children.add(child);
            child.setParent(this);
        }
    }

    public void removeChild(Category child) {
        // Удаление подкатегории
        if (child == null) {
            throw new IllegalArgumentException("Подкатегория не может быть null.");
        }
        if (children.remove(child)) {
            child.setParent(null);
        }
    }
}
