package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.typesafe.config.ConfigFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class Connection {
    public static Datastore datastore;

    //private constructor to avoid client applications to use constructor
    private Connection(){}

    public static Datastore getDatastore(){
        if(datastore == null){
            initDatastore();
        }

        return datastore;
    }

    public static void initDatastore(){
        final Morphia morphia = new Morphia();

        // tell it to fine model class
        morphia.mapPackage("models");

        MongoClientOptions.Builder options = MongoClientOptions.builder();
        options.socketKeepAlive(true);

        MongoClientURI uri = new MongoClientURI("mongodb://trevis-db:trevis123@ds227740.mlab.com:27740/trevis-db", options);

        datastore = morphia.createDatastore(new MongoClient(uri), "trevis-db");

    }
}