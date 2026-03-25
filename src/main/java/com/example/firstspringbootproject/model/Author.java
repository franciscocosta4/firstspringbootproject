package com.example.firstspringbootproject.model;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id //chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)// autoincrement
    private Long id;
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
    private String name; 
    
    @Lob
    private String biography; // equivale a TEXT no SQL

    private LocalDate bornDate; // equivale a DATE no SQL

    private LocalDate deathDate;

     // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

    public LocalDate getBornDate() { return bornDate; }
    public void setBornDate(LocalDate bornDate) { this.bornDate = bornDate; }

    public LocalDate getDeathDate() { return deathDate; }
    public void setDeathDate(LocalDate deathDate) { this.deathDate = deathDate; }

    

}
