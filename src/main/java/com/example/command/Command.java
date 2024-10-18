package com.example.command;

import org.springframework.stereotype.Component;

@Component
public interface Command {
    String execute(String[] args);
}
