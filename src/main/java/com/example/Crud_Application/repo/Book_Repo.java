package com.example.Crud_Application.repo;

import com.example.Crud_Application.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface Book_Repo extends JpaRepository<Book, Long> {
}
