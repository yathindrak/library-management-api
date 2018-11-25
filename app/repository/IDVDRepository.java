package repository;

import com.google.inject.ImplementedBy;
import models.DVD;
import models.Reader;
import org.mongodb.morphia.Key;

import java.util.List;
import repository.implementation.DVDRepositoryImpl;
import utils.DateTime;

@ImplementedBy(DVDRepositoryImpl.class)
public interface IDVDRepository {
    Key<DVD> save(DVD item);

    List<DVD> findAll();

    DVD findById(String isbn);

    boolean updateBorrowing(String id, DVD book, DateTime dateTime, Reader reader);

    boolean updateReturning(String id);

    boolean delete(String id);
}
