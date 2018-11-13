package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "dvds", noClassnameStored = true)
public class DVD extends Item {
    private String author;

    @JsonCreator
    public DVD() {
        this.author = null;
    }

    @JsonCreator
    public DVD(ObjectId id, String name, String author) {
        super(id, name);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
