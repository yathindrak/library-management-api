package repository;

import com.google.inject.ImplementedBy;
import models.Reservation;
import org.mongodb.morphia.Key;
import repository.implementation.ReservationRepositoryImpl;

import java.util.List;

@ImplementedBy(ReservationRepositoryImpl.class)
public interface IReservationRepository {
    Key<Reservation> save(Reservation reservation);

    Reservation findById(String id);

    List<Reservation> findAll();
}
