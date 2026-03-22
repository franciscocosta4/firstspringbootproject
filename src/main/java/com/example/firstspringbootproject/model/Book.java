package com.example.firstspringbootproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity // diz ao Spring: “isto é uma tabela na BD”
public class Book {

    @Id //chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long id;

    private String title;
    private String author;
    private Integer year;

    public Book(){} //construtor vazio obrigatorio pelo jpa
    
    // Construtor com parâmetros
    public Book(String title, String author){
        this.title = title;
        this.author = author; 
    }
    
    
    // getters e setters

    public Long getId(){
        return id; 
    }
    public String getTitle(){
        return title; 
    }
    public String getAuthor(){
        return author; 
    }
    public Integer getYear(){
        return year; 
    }


    public void SetId(Long id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setYear(Integer year) {
        this.year = year;  
    }
       public void setAuthor(String author) {
        this.author = author; 
    }

}
