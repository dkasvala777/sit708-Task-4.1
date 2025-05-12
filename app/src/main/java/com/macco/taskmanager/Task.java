package com.macco.taskmanager;

public class Task {

    private int id;  // Add ID as a field
    private String title;
    private String description;
    private String dueDate;

    // Constructor with 4 parameters (id, title, description, dueDate)
    public Task(int id, String title, String description, String dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Constructor with 3 parameters (title, description, dueDate)
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
}
