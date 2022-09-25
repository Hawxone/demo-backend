package com.dimas.product.service;

import com.dimas.product.entity.BookEntity;
import com.dimas.product.model.Book;
import com.dimas.product.repository.BookEntityRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private BookEntityRepository bookEntityRepository;

    public BookServiceImpl(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    @Override
    public void saveBook(List<Book> bookList) throws Exception {

        try {
            for (int index = 0; index < bookList.size(); index++) {

                BookEntity entity = new BookEntity();
                entity.setTitle(bookList.get(index).getTitle());
                entity.setImageOrder(bookList.get(index).getImageOrder());
                entity.setImage(bookList.get(index).getImage().getBytes());
                bookEntityRepository.save(entity);

            }

        }catch (Exception e){
            throw new Exception("could not save item " + e);
        }

    }

    @Override
    public Book getBookByOrder(Integer order) {

        BookEntity bookEntity = bookEntityRepository.findBookEntityByImageOrder(order).get();

        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setImage("data:image/png;base64,"+new String(bookEntity.getImage(), StandardCharsets.UTF_8));
        book.setImageOrder(bookEntity.getImageOrder());

        return book;
    }

    @Override
    public List<Book> findAll() {

        List<BookEntity> bookEntities = bookEntityRepository.findAll();

        List<Book> bookList = new ArrayList<>();


        bookList = bookEntities.stream().map((bookEntity)->
                Book.builder()
                        .id(bookEntity.getId())
                        .title(bookEntity.getTitle())
                        .imageOrder(bookEntity.getImageOrder())
                        .image("data:image/png;base64,"+new String(bookEntity.getImage(),StandardCharsets.UTF_8))
                        .build()
        ).collect(Collectors.toList());

        return bookList;
    }

    @Override
    public long countBookSize() {

        long length = bookEntityRepository.count();

        return length;
    }
}
