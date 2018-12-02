package repository;

import com.google.inject.ImplementedBy;
import exceptions.ISBNAlreadyExistsException;
import models.DVD;
import models.Reader;
import org.mongodb.morphia.Key;

import java.util.List;
import repository.implementation.DVDRepositoryImpl;
import utils.DateTime;

@ImplementedBy(DVDRepositoryImpl.class)
public interface IDVDRepository {
    Key<DVD> save(DVD item) throws ISBNAlreadyExistsException;

    List<DVD> findAll();

    DVD findById(String isbn);

    boolean updateBorrowing(String id, DVD dvd, DateTime dateTime, Reader reader);

    boolean updateReturning(String id);

    boolean delete(String id);
}
