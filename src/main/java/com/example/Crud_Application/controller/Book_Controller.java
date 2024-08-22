package com.example.Crud_Application.controller;

import com.example.Crud_Application.model.Book;
import com.example.Crud_Application.repo.Book_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class Book_Controller {
    @Autowired
    private Book_Repo Book_Repo;
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> bookList = Book_Repo.findAll();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData = Book_Repo.findById(id);
        if (bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book bookObj= Book_Repo.save(book);
        return new ResponseEntity<>(bookObj,HttpStatus.OK);
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id,@RequestBody Book newBookData){
        Optional<Book> OldBookData = Book_Repo.findById(id);

        if (OldBookData.isPresent()){
           Book updatedBookData = OldBookData.get();
           updatedBookData.setTitle(newBookData.getTitle());
           updatedBookData.setAuthor(newBookData.getAuthor());

           Book bookObj = Book_Repo.save(updatedBookData);
           return new ResponseEntity<>(bookObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id){
        Book_Repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
