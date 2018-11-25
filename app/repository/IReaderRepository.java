package repository;

import com.google.inject.ImplementedBy;
import models.Reader;
import org.mongodb.morphia.Key;
import repository.implementation.ReaderRepositoryImpl;

import java.util.List;

@ImplementedBy(ReaderRepositoryImpl.class)
public interface IReaderRepository {
    Key<Reader> save(Reader reader);

    Reader findById(String id);

    List<Reader> findAll();
}