package repository.implementation;

import database.Connection;
import models.Publisher;
import org.mongodb.morphia.Key;
import repository.IPublisherRepository;

import java.util.List;

/**
 * Class to perform database operations regarding publishers
 */
public class PublisherRepositoryImpl implements IPublisherRepository {
    @Override
    public Key<Publisher> save(Publisher publisher) {
        Key<Publisher> savedPublisher = Connection.getDatastore().save(publisher);
        return savedPublisher;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = Connection.getDatastore().createQuery(Publisher.class).asList();
        return publishers;
    }
}
