package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "books", noClassnameStored = true)
public class Book extends Item {
    private String author;

    @JsonCreator
    public Book() {
        this.author = null;
    }

    @JsonCreator
    public Book(ObjectId id, String name, String author) {
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
