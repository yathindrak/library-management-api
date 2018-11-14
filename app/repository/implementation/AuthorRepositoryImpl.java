package repository.implementation;

import database.Connection;
import models.Author;
import org.mongodb.morphia.Key;
import repository.IAuthorRepository;

import java.util.List;

public class AuthorRepositoryImpl implements IAuthorRepository {
    @Override
    public Key<Author> save(Author author) {
        Key<Author> savedAuthor = Connection.getDatastore().save(author);
        return savedAuthor;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = Connection.getDatastore().createQuery(Author.class).asList();
        return authors;
    }
}
