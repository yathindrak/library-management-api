package repository.implementation;

import database.Connection;
import models.Book;
import models.Reader;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import repository.IBookRepository;
import org.mongodb.morphia.query.Query;
import utils.DateTime;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements IBookRepository {
    @Override
    public Key<Book> save(Book item) {
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

    @Override
    public Book findById(String id) {
        Book book= Connection.getDatastore().get(Book.class, new ObjectId(id));
        System.out.println(book);
        return book;
    }

    @Override
    public boolean updateBorrowing(String id, Book book, DateTime borrowedDate, Reader reader) {

        Query query = Connection.getDatastore().find(Book.class).field("_id").equal(new ObjectId(id));

        UpdateOperations<Book> operation2 = Connection.getDatastore()
                .createUpdateOperations(Book.class).set("borrowedDate", borrowedDate);

        UpdateOperations<Book> operation3 = Connection.getDatastore()
                .createUpdateOperations(Book.class).set("currentReader", reader);

        Connection.getDatastore().update(query, operation2);
        Connection.getDatastore().update(query, operation3);

        return true;
    }

    @Override
    public boolean updateReturning(String id) {

        Query query = Connection.getDatastore().find(Book.class).field("_id").equal(new ObjectId(id));

        UpdateOperations<Book> operation2 = Connection.getDatastore()
                .createUpdateOperations(Book.class).set("borrowedDate", new DateTime());

        UpdateOperations<Book> operation3 = Connection.getDatastore()
                .createUpdateOperations(Book.class).set("currentReader", new Reader());

        Connection.getDatastore().update(query, operation2);
        Connection.getDatastore().update(query, operation3);

        return true;
    }

    @Override
    public boolean delete(String id) {
        try {
            Query query = Connection.getDatastore().find(Book.class).field("_id").equal(new ObjectId(id));

            Book result = (Book) Connection.getDatastore().findAndDelete(query);

            System.out.println(result);

            if (result != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
