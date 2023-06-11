package net.skhu.response;

import java.time.LocalDate;

public class TodoStatsResponse {
    private LocalDate date;
    private int totalTodoCount;
    private int completedTodoCount;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalTodoCount() {
        return totalTodoCount;
    }

    public void setTotalTodoCount(int totalTodoCount) {
        this.totalTodoCount = totalTodoCount;
    }

    public int getCompletedTodoCount() {
        return completedTodoCount;
    }

    public void setCompletedTodoCount(int completedTodoCount) {
        this.completedTodoCount = completedTodoCount;
    }
}

