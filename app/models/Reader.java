package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "readers", noClassnameStored = true)
public class Reader extends Person{
    private String mobile;
    private String email;

//    @JsonCreator
//    public Reader() {
//        this.mobile = null;
//        this.email = null;
//    }


    public Reader() {
        super(null, null);
        this.mobile = null;
        this.email = null;
    }

    @JsonCreator
    public Reader(@JsonProperty("id") String id,@JsonProperty("name") String name,
                  @JsonProperty("mobile") String mobile, @JsonProperty("email") String email) {
        super(id, name);
        this.mobile = mobile;
        this.email = email;
    }
}
