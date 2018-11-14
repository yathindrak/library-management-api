package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import utils.DateTime;

import java.util.List;

@Entity(value = "dvds", noClassnameStored = true)
public class DVD extends Item {
    private String author;
    private List<String> availLanguages;
    private List<String> availSubtitles;
    private String producer;
    private List<Actor> actors;

//    @JsonCreator
//    public DVD() {
//        this.author = null;
//    }

    @JsonCreator
    public DVD(ObjectId id, String isbn, String title, String sector, DateTime publicationDate,
               DateTime borrowedDate, Reader currentReader, String author) {
        super(id, isbn, title, sector, publicationDate, borrowedDate, currentReader);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
