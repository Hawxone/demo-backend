package com.dimas.product.service;

import com.dimas.product.bucket.BucketName;
import com.dimas.product.entity.BookEntity;
import com.dimas.product.model.Book;
import com.dimas.product.repository.BookEntityRepository;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookEntityRepository bookEntityRepository;
    private final FileStoreService fileStoreService;

    public BookServiceImpl(BookEntityRepository bookEntityRepository, FileStoreService fileStoreService) {
        this.bookEntityRepository = bookEntityRepository;
        this.fileStoreService = fileStoreService;
    }

    @Override
    public Book saveBook(Book book, MultipartFile file) throws Exception {

       boolean existing =  bookEntityRepository.findBookEntityByTitle(book.getTitle()).isPresent();

        isFileEmpty(file);

        isImage(file);

        Map<String, String> metadata = getMetadata(file);

        String path = String.format("%s/%s", BucketName.BUCKET.getBucketName(),"galleries");


        try {
            fileStoreService.save(path,file.getOriginalFilename(),Optional.of(metadata),file.getInputStream());
        }catch (IOException e){
            throw new IllegalStateException(e);
        }


        if (!existing){
            try {
                BookEntity entity = new BookEntity();
                entity.setTitle(book.getTitle());
                entity.setImageOrder(book.getImageOrder());
                bookEntityRepository.save(entity);

            }catch (Exception e){
                throw new Exception("could not save item " + e);
            }
            return book;
        }

        return book;
    }

    private static void isImage(MultipartFile file) throws Exception {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())){
            throw new Exception("file must be image");
        }
    }

    private static Map<String, String> getMetadata(MultipartFile file) {
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private static void isFileEmpty(MultipartFile file) throws Exception {
        if (file.isEmpty()){
            throw new Exception("file is empty");
        }
    }

    @Override
    public Book getBookByOrder(Integer order) {

        BookEntity bookEntity = bookEntityRepository.findBookEntityByImageOrder(order).get();

        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
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
