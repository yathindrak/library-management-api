package repository.implementation;

import models.DVD;
import models.Reader;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import repository.IDVDRepository;

import database.Connection;
import org.mongodb.morphia.Key;
import utils.DateTime;

import java.util.List;

public class DVDRepositoryImpl implements IDVDRepository {
    @Override
    public Key<DVD> save(DVD item) {
        Key<DVD> savedItem = Connection.getDatastore().save(item);
        return savedItem;
    }

    @Override
    public List<DVD> findAll() {
//        List<DVD> items = new ArrayList<>();
        List<DVD> dvds = Connection.getDatastore().createQuery(DVD.class).asList();
//        dvds.forEach(dvd -> {
//            items.add(dvd);
//        });
        return dvds;
    }

    @Override
    public DVD findById(String id) {
        DVD dvd= Connection.getDatastore().get(DVD.class, new ObjectId(id));
        System.out.println(dvd);
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
