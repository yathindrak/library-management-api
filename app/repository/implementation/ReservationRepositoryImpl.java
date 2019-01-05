package repository.implementation;

import database.Connection;
import models.Reservation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import repository.IReservationRepository;

import java.util.List;

/**
 * Class to perform database operations regarding reservations
 */
public class ReservationRepositoryImpl implements IReservationRepository {
    @Override
    public Key<Reservation> save(Reservation reservation) {
        Key<Reservation> savedReservation = Connection.getDatastore().save(reservation);
        return savedReservation;
    }

    @Override
    public Reservation findById(String id) {
        Reservation reservation = null;
        try {
            reservation= Connection.getDatastore().get(Reservation.class, new ObjectId(id));
        } catch (Exception e){
            return new Reservation();
        }

        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = Connection.getDatastore().createQuery(Reservation.class).asList();
        return reservations;
    }

    @Override
    public List<Reservation> findByIsbn(String isbn) {
        List<Reservation> reservations = Connection.getDatastore().find(Reservation.class).filter("isbn", isbn).asList();
        return reservations;
    }
}
