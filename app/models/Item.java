package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public abstract class Item {
    @Id
    private ObjectId id;
    private String name;

    @JsonCreator
    public Item() {
        this.id = null;
        this.name = null;
    }

    @JsonCreator
    public Item(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
