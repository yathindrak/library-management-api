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
    private int timeInHours;

    // default constructor
    public Reservation() {
    }

    // Json creater
    @JsonCreator
    public Reservation(@JsonProperty("id")String id, @JsonProperty("isbn") String isbn,
                       @JsonProperty("reservedReader") Reader reservedReader, @JsonProperty("timeInHours") int timeInHours) {
        this.id = id;
        this.isbn = isbn;
        this.reservedReader = reservedReader;
        this.timeInHours = timeInHours;
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

    public int getTimeInHours() {
        return timeInHours;
    }

    public void setTimeInHours(int timeInHours) {
        this.timeInHours = timeInHours;
    }
}
