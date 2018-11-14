package models;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "authors", noClassnameStored = true)
public class Author extends Person{

}
