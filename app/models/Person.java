package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.mongodb.morphia.annotations.Id;

public abstract class Person {
    @Id
    private String _id;
    private String name;

    public Person() {
        this._id = null;
        this.name = null;
    }

    @JsonCreator
    public Person(String id, String name) {
        this._id = id;
        this.name = name;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
