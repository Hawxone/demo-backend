package com.dimas.product.controller;


import com.dimas.product.model.Book;
import com.dimas.product.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {

        this.bookService = bookService;
    }

    @PostMapping("/single")
    public Book addBook(@RequestBody Book book) throws Exception {
        System.out.println(book);
        String order = book.getTitle().replaceAll("\\D+","");
        Book saveBook_co = Book.builder()
                .title(book.getTitle())
                .imageOrder(Integer.parseInt(order))
                .build();

        bookService.saveBook(saveBook_co);

        return null;
    }

    @PostMapping
    public List<Book> uploadBooks(@RequestParam("file") MultipartFile file) throws Exception {

        List<Book> bookList = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(file.getInputStream());
        ZipEntry ze = zis.getNextEntry();

        while (ze != null){
            String filename = ze.getName();
            String stripped = filename.replaceAll("\\D+","");

            Book newBook = new Book();
            newBook.setTitle(filename);
            newBook.setImageOrder(Integer.parseInt(stripped));
            bookList.add(newBook);

            bookService.saveBook(newBook);

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


