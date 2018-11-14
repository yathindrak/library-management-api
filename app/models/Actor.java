package models;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "actors", noClassnameStored = true)
public class Actor extends Person {
}