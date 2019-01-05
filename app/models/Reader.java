package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "readers", noClassnameStored = true)
public class Reader extends Person{

    private String mobile;
    private String email;

    // constructor
    public Reader() {
        super(null, null);
        this.mobile = null;
        this.email = null;
    }

    // Json creater
    @JsonCreator
    public Reader(@JsonProperty("id") String id,@JsonProperty("name") String name,
                  @JsonProperty("mobile") String mobile, @JsonProperty("email") String email) {
        super(id, name);
        this.mobile = mobile;
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
