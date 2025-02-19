package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
  private Collection<Book> books;

  public BookController() {
    this.books = new ArrayList<>();
    initializeData();
  }

  public void initializeData() {
    Book book1 = new Book(1, "The Hobbit", 1937, 310);
    Book book2 = new Book(2, "The Lord of the Rings", 1954, 1178);
    Book book3 = new Book(3, "The Militarisation", 1977, 365);
    books.add(book1);
    books.add(book2);
    books.add(book3);
  }

  @GetMapping("/books")
  public Collection<Book> greeting() {
    return this.books;
  }


  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBook(@PathVariable int id) {
    return books.stream()
        .filter(b -> b.getId() == id)
        .findFirst()
        .map(b -> ResponseEntity.ok(b))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }



  @PostMapping("/books")
  public ResponseEntity<String> add(@RequestBody Book book) {

    if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("Cannot create a book with an already existing ID.");
    }

    if (book.getTitle() == null
        || book.getTitle().isEmpty()
        || books.stream().anyMatch(b -> b.getTitle().equals(book.getTitle()))) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("Cannot create a book with a null, empty, or duplicate title.");
    }

    books.add(book);
    return ResponseEntity.status(HttpStatus.CREATED).body("Book successfully created.");
  }











}
