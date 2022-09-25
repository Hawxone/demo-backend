package com.dimas.product.service;

import com.dimas.product.model.Book;

import java.util.List;

public interface BookService {
    void saveBook(List<Book> bookList) throws Exception;

    Book getBookByOrder(Integer order);

    List<Book> findAll();

    long countBookSize();
}