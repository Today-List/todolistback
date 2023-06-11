package net.skhu.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TodoRequest {
    private LocalDate date;
    private String task;
    private int todoId;

    public TodoRequest() {
    }

    public TodoRequest(LocalDate date, String task) {
        this.date = date;
        this.task = task;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public int getTodoId() {
        return todoId;
    }
}

