package repository.implementation;

import database.Connection;
import models.Book;
import org.mongodb.morphia.Key;
import repository.IItemRepository;

import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements IItemRepository {
    @Override
    public Key<Book> save(Book item) {
        System.out.println(item.getName());
        Key<Book> savedItem = Connection.getDatastore().save(item);
        return savedItem;
    }

    @Override
    public List<Book> findAll() {
        List<Book> items = new ArrayList<>();
        List<Book> books = Connection.getDatastore().createQuery(Book.class).asList();
        books.forEach(book -> {
            items.add(book);
        });
        System.out.println(items);
        return items;
    }
}
