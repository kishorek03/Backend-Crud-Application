package com.example.Crud_Application.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
}
