package repository;

import com.google.inject.ImplementedBy;
import models.Actor;
import org.mongodb.morphia.Key;
import repository.implementation.ActorRepositoryImpl;
import repository.implementation.AuthorRepositoryImpl;

import java.util.List;

@ImplementedBy(ActorRepositoryImpl.class)
public interface IActorRepository {
    Key<Actor> save(Actor actor);

    List<Actor> findAll();
}
