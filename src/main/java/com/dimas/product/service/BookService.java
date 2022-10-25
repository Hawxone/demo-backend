package com.dimas.product.service;

import com.dimas.product.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Book saveBook(Book bookList, MultipartFile file) throws Exception;

    Book getBookByOrder(Integer order);

    List<Book> findAll();

    long countBookSize();
}
