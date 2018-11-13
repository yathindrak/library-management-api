package repository;

import com.google.inject.ImplementedBy;
import models.Book;
import models.Item;
import org.mongodb.morphia.Key;
import repository.implementation.ItemRepositoryImpl;

import java.util.List;

@ImplementedBy(ItemRepositoryImpl.class)
public interface IItemRepository {
    Key<Book> save(Book item);

    List<Book> findAll();
}
