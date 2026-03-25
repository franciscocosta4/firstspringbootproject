package com.example.firstspringbootproject.dto.books;

import java.util.List;

/*
 * DTO = camada de transporte
 * nunca expõe entidades diretamente
 */
public class BookRequest {
    private Long id; // novo — necessário para o edit.html construir o URL
    private String title;
    private Integer year;
    private List<Long> authorIds;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }
}