package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import utils.DateTime;

@Entity(value = "books", noClassnameStored = true)
public class Book extends Item {
    private Author author;

//    public Book() {
//        this.author = null;
//    }

    @JsonCreator
    public Book(@JsonProperty("id") ObjectId id, @JsonProperty("isbn") String isbn,
                @JsonProperty("title") String title, @JsonProperty("sector") String sector,
                @JsonProperty("publicationDate") DateTime publicationDate,
                @JsonProperty("borrowedDate") DateTime borrowedDate,
                @JsonProperty("currentReader") Reader currentReader, @JsonProperty("author") Author author) {
        super(id, isbn, title, sector, publicationDate, borrowedDate, currentReader);
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
