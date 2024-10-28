package com.example.Crud_Application.controller;

import com.example.Crud_Application.model.Book;
import com.example.Crud_Application.repo.Book_Repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
public class Book_Controller {

    @Autowired
    private Book_Repo Book_Repo;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Fetching all books");
        try {
            List<Book> bookList = Book_Repo.findAll();
            if (bookList.isEmpty()) {
                log.warn("No books found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Books retrieved successfully: {}", bookList);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error fetching books: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.info("Fetching book with ID: {}", id);
        Optional<Book> bookData = Book_Repo.findById(id);
        if (bookData.isPresent()) {
            log.info("Book retrieved successfully: {}", bookData.get());
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        log.warn("Book not found with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        log.info("Adding new book: {}", book);
        try {
            Book bookObj = Book_Repo.save(book);
            log.info("Book added successfully: {}", bookObj);
            return new ResponseEntity<>(bookObj, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Error adding book: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        log.info("Updating book with ID: {}", id);
        Optional<Book> oldBookData = Book_Repo.findById(id);

        if (oldBookData.isPresent()) {
            Book updatedBookData = oldBookData.get();
            updatedBookData.setTitle(newBookData.getTitle());
            updatedBookData.setAuthor(newBookData.getAuthor());

            Book bookObj = Book_Repo.save(updatedBookData);
            log.info("Book updated successfully: {}", bookObj);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        }
        log.warn("Book not found with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        try {
            Book_Repo.deleteById(id);
            log.info("Book deleted successfully with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error deleting book with ID {}: {}", id, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
