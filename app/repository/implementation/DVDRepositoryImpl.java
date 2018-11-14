package repository.implementation;

import models.DVD;
import repository.IDVDRepository;

import database.Connection;
import org.mongodb.morphia.Key;
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
}
