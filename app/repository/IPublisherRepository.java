package repository;

import com.google.inject.ImplementedBy;
import models.Publisher;
import models.Reservation;
import org.mongodb.morphia.Key;
import repository.implementation.PublisherRepositoryImpl;
import repository.implementation.ReservationRepositoryImpl;

import java.util.List;

import com.google.inject.ImplementedBy;
import models.Reservation;
import org.mongodb.morphia.Key;

import java.util.List;

@ImplementedBy(PublisherRepositoryImpl.class)
public interface IPublisherRepository {
    Key<Publisher> save(Publisher publisher);

    List<Publisher> findAll();
}
