package repository;

import com.google.inject.ImplementedBy;
import models.Book;
import org.mongodb.morphia.Key;
import repository.implementation.BookRepositoryImpl;

import java.util.List;

@ImplementedBy(BookRepositoryImpl.class)
public interface IBookRepository {
    Key<Book> save(Book item);

    List<Book> findAll();
}
