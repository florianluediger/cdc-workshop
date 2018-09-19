package io.reflectoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    private boolean completed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void updateFrom(Todo todo) {
        this.title = todo.getTitle();
        this.completed = todo.isCompleted();
    }
}
