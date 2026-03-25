package com.example.firstspringbootproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Set;

@Entity // diz ao Spring: “isto é uma tabela na BD”
@Table(name = "books")
public class Book {

    @Id //chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long id;

    private String title;
    private Integer year;


    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),  // coluna que referencia Book
        inverseJoinColumns = @JoinColumn(name = "autor_id") // coluna que referencia Author
    )
    private Set<Author> authors;  // conjunto de autores

    public Book(){} //construtor vazio obrigatorio pelo jpa
    
    // Construtor com parâmetros
    public Book(String title){
        this.title = title; 
    }
    
    
    // getters e setters

    public Long getId(){
        return id; 
    }
    public String getTitle(){
        return title; 
    }
    public Set<Author> getAuthors(){
        return authors; 
    }
    public Integer getYear(){
        return year; 
    }


    public void setId(Long id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setYear(Integer year) {
        this.year = year;  
    }
       public void setAuthors(Set<Author> authors) {
        this.authors = authors; 
    }

}
