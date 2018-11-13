package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public abstract class Item {
    @Id
    private ObjectId id;
    private String isbn;
    private String title;
    private String sector;
//    private DateTime publicationDate;
//    private DateTime borrowedDate;
//    private Reader currentReader;


    @JsonCreator
    public Item() {
        this.id = null;
        this.title = null;
    }

    @JsonCreator
    public Item(ObjectId id, String title) {
        this.id = id;
        this.title = title;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
