package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "reservations", noClassnameStored = true)
public class Reservation {
    @Id
    private String id;
    private String isbn;
    private Reader reservedReader;

    public Reservation() {
    }

    @JsonCreator
    public Reservation(@JsonProperty("id")String id, @JsonProperty("isbn") String isbn,
                       @JsonProperty("reservedReader") Reader reservedReader) {
        this.id = id;
        this.isbn = isbn;
        this.reservedReader = reservedReader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Reader getReservedReader() {
        return reservedReader;
    }

    public void setReservedReader(Reader reservedReader) {
        this.reservedReader = reservedReader;
    }
}
