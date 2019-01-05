package models;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "publishers", noClassnameStored = true)
public class Publisher extends Person{

}