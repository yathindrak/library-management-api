package repository.implementation;

import database.Connection;
import models.Reader;
import org.mongodb.morphia.Key;
import repository.IReaderRepository;
import java.util.List;

public class ReaderRepositoryImpl implements IReaderRepository {
    @Override
    public Key<Reader> save(Reader reader) {
        Key<Reader> savedReader = Connection.getDatastore().save(reader);
        return savedReader;
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = Connection.getDatastore().createQuery(Reader.class).asList();
        return readers;
    }
}
