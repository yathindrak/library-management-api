package repository;

import com.google.inject.ImplementedBy;
import exceptions.ISBNAlreadyExistsException;
import models.Book;
import models.Reader;
import org.mongodb.morphia.Key;
import repository.implementation.BookRepositoryImpl;
import models.DateTime;

import java.util.List;

@ImplementedBy(BookRepositoryImpl.class)
public interface IBookRepository {
    Key<Book> save(Book item) throws ISBNAlreadyExistsException;

    List<Book> findAll();

    Book findById(String isbn);

    boolean updateBorrowing(String id, Book book, DateTime dateTime, Reader reader);

    boolean updateReturning(String id);

    boolean delete(String id);
}
