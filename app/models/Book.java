package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import utils.DateTime;

import java.util.List;

@Entity(value = "books", noClassnameStored = true)
public class Book extends Item {
    private List<Author> author;

    public Book() {
        super(null, null, null, null, null, null, null);
        this.author = null;
    }

    @JsonCreator
    public Book(@JsonProperty("id") String id, @JsonProperty("isbn") String isbn,
                @JsonProperty("title") String title, @JsonProperty("sector") String sector,
                @JsonProperty("publicationDate") DateTime publicationDate,
                @JsonProperty("borrowedDate") DateTime borrowedDate,
                @JsonProperty("currentReader") Reader currentReader,
                @JsonProperty("author") List<Author> author) {
        super(id, isbn, title, sector, publicationDate, borrowedDate, currentReader);
        this.author = author;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }
}
