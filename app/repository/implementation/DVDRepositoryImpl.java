package repository.implementation;

import exceptions.ISBNAlreadyExistsException;
import models.DVD;
import models.Reader;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import play.Logger;
import repository.IDVDRepository;

import database.Connection;
import org.mongodb.morphia.Key;
import utils.DateTime;

import java.util.List;

/**
 * Class to perform database operations regarding dvds
 */
public class DVDRepositoryImpl implements IDVDRepository {
    @Override
    public Key<DVD> save(DVD item) throws ISBNAlreadyExistsException {
        DVD dvdByISBN = Connection.getDatastore().find(DVD.class).field("isbn").equal(item.getIsbn()).get();
        Key<DVD> savedItem = null;
        if (dvdByISBN == null) {
            savedItem = Connection.getDatastore().save(item);
        } else {
            throw new ISBNAlreadyExistsException("Isbn already exists");
        }
        return savedItem;
    }

    @Override
    public List<DVD> findAll() {
        List<DVD> dvds = Connection.getDatastore().createQuery(DVD.class).asList();
        return dvds;
    }

    @Override
    public DVD findById(String id) {
        DVD dvd= Connection.getDatastore().get(DVD.class, new ObjectId(id));
        Logger.info("Found DVD of id: "+ id);
        return dvd;
    }

    @Override
    public boolean updateBorrowing(String id, DVD dvd, DateTime borrowedDate, Reader reader) {

        Query query = Connection.getDatastore().find(DVD.class).field("_id").equal(new ObjectId(id));

        UpdateOperations<DVD> operation2 = Connection.getDatastore()
                .createUpdateOperations(DVD.class).set("borrowedDate", borrowedDate);

        UpdateOperations<DVD> operation3 = Connection.getDatastore()
                .createUpdateOperations(DVD.class).set("currentReader", reader);

        Connection.getDatastore().update(query, operation2);
        Connection.getDatastore().update(query, operation3);

        return true;
    }

    @Override
    public boolean updateReturning(String id) {

        Query query = Connection.getDatastore().find(DVD.class).field("_id").equal(new ObjectId(id));

        UpdateOperations<DVD> operation2 = Connection.getDatastore()
                .createUpdateOperations(DVD.class).set("borrowedDate", new DateTime());

        UpdateOperations<DVD> operation3 = Connection.getDatastore()
                .createUpdateOperations(DVD.class).set("currentReader", new Reader());

        Connection.getDatastore().update(query, operation2);
        Connection.getDatastore().update(query, operation3);

        return true;
    }

    @Override
    public boolean delete(String id) {
        Query<DVD> query = Connection.getDatastore().find(DVD.class).field("_id").equal(new ObjectId(id));

        DVD result = Connection.getDatastore().findAndDelete(query);

        if (result != null) {
            return true;
        }
        return false;
    }
}
