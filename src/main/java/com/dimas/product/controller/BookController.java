package com.dimas.product.controller;


import com.dimas.product.model.Book;
import com.dimas.product.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {

        this.bookService = bookService;
    }

    @PostMapping
    public List<Book> uploadBooks(@RequestParam("file") MultipartFile file) throws Exception {

        List<Book> bookList = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry ze = zis.getNextEntry();
        int i = 0;
        while (ze != null){
            int buffer = 0;
            String filename = ze.getName();
            String stripped = filename.replaceAll("\\D+","");
            String bytes = Base64.getEncoder().encodeToString(zis.readAllBytes());
            Book newBook = new Book();
            newBook.setTitle(filename);
            newBook.setImage(bytes);
            newBook.setImageOrder(Integer.parseInt(stripped));
            bookList.add(newBook);

            bookService.saveBook(bookList);

            ze = zis.getNextEntry();
        }

        return bookList;
    }

    @GetMapping("/{order}")
    @Transactional
    public ResponseEntity<Book> getBookByOrder(@PathVariable("order") Integer order){

        Book book= bookService.getBookByOrder(order);

        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){

        List<Book> bookList = bookService.findAll();

        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/size")
    public Integer getBooksPageSize(){

    long length = bookService.countBookSize();

        return (int) length;
    }

}


