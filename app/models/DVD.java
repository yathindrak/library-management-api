package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import utils.DateTime;

import java.util.List;

@Entity(value = "dvds", noClassnameStored = true)
public class DVD extends Item {
    private List<String> availLanguages;
    private List<String> availSubtitles;
    private String producer;
    private List<Actor> actors;

    /*
    * This constructor for morphia get method*/
    public DVD() {
        super(null, null, null, null, null, null, null);
        this.availLanguages = null;
        this.availSubtitles = null;
        this.producer = null;
        this.actors = null;
    }

    @JsonCreator
    public DVD(@JsonProperty("id") String id, @JsonProperty("isbn") String isbn,
               @JsonProperty("title") String title, @JsonProperty("sector") String sector,
               @JsonProperty("publicationDate") DateTime publicationDate,
               @JsonProperty("borrowedDate") DateTime borrowedDate,
               @JsonProperty("currentReader") Reader currentReader,
               @JsonProperty("availLanguages") List<String> availLanguages,
               @JsonProperty("availSubtitles") List<String> availSubtitles,
               @JsonProperty("producer") String producer,
               @JsonProperty("actors")List<Actor> actors) {
        super(id, isbn, title, sector, publicationDate, borrowedDate, currentReader);
        this.availLanguages = availLanguages;
        this.availSubtitles = availSubtitles;
        this.producer = producer;
        this.actors = actors;
    }

    public List<String> getAvailLanguages() {
        return availLanguages;
    }

    public void setAvailLanguages(List<String> availLanguages) {
        this.availLanguages = availLanguages;
    }

    public List<String> getAvailSubtitles() {
        return availSubtitles;
    }

    public void setAvailSubtitles(List<String> availSubtitles) {
        this.availSubtitles = availSubtitles;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
