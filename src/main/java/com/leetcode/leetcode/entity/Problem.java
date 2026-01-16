package com.leetcode.leetcode.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Problem() {

    }

    public Problem(String title, String category, String description) {
        this.title = title;
        this.category = category;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
