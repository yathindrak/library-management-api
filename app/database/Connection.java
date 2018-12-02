package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.typesafe.config.ConfigFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.Logger;

/**
 * Singleton class to make the database connection
 */
public class Connection {
    // Data-store
    public static Datastore datastore;

    // private constructor to avoid creating objects
    private Connection(){}

    /**
     * Get the data store
     * @return an instance of the datastore
     */
    public static Datastore getDatastore(){
        if(datastore == null){
            initDatastore();
        }

        return datastore;
    }

    /**
     * Initialize an instance of the datastore, if there is no instance
     */
    public static void initDatastore(){
        try {
            final Morphia morphia = new Morphia();

            // map the model package
            morphia.mapPackage("models");

            MongoClientOptions.Builder options = MongoClientOptions.builder();
            // keep alive
            options.socketKeepAlive(true);

            // variables from application.conf
            String mongo_username = ConfigFactory.load().getString("mongo_username");
            String mongo_pwd = ConfigFactory.load().getString("mongo_pwd");
            String mongo_db = ConfigFactory.load().getString("mongo_db");
            MongoClientURI uri = new MongoClientURI("mongodb://"+mongo_username+":"+mongo_pwd+"@ds137913.mlab.com:37913/"+mongo_db, options);

            // init the data store
            datastore = morphia.createDatastore(new MongoClient(uri), mongo_db);
        } catch (Exception e) {
            Logger.error("Error occurred on the database connection");
        }

    }
}