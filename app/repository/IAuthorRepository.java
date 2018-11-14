package repository;

import com.google.inject.ImplementedBy;
import models.Author;
import org.mongodb.morphia.Key;
import repository.implementation.AuthorRepositoryImpl;

import java.util.List;

@ImplementedBy(AuthorRepositoryImpl.class)
public interface IAuthorRepository {
    Key<Author> save(Author author);

    List<Author> findAll();
}
