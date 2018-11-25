package repository;

import com.google.inject.ImplementedBy;
import com.mongodb.WriteResult;
import models.Book;
import models.Reader;
import org.mongodb.morphia.Key;
import repository.implementation.BookRepositoryImpl;
import utils.DateTime;

import java.util.List;

@ImplementedBy(BookRepositoryImpl.class)
public interface IBookRepository {
    Key<Book> save(Book item);

    List<Book> findAll();

    Book findById(String isbn);

    boolean updateBorrowing(String id, Book book, DateTime dateTime, Reader reader);

    boolean updateReturning(String id);

    boolean delete(String id);
}
