package repository.implementation;

import database.Connection;
import models.Actor;
import org.mongodb.morphia.Key;
import repository.IActorRepository;

import java.util.List;

/**
 * Class to perform database operations regarding actors
 */
public class ActorRepositoryImpl implements IActorRepository {
    @Override
    public Key<Actor> save(Actor actor) {
        Key<Actor> savedActor = Connection.getDatastore().save(actor);
        return savedActor;
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = Connection.getDatastore().createQuery(Actor.class).asList();
        return actors;
    }
}
