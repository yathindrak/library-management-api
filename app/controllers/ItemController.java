package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.IAuthorRepository;
import repository.IDVDRepository;
import repository.IBookRepository;
import play.libs.Json;
import repository.IReaderRepository;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

public class ItemController  extends Controller {

    @Inject
    private IBookRepository bookRepo;

    @Inject
    private IDVDRepository dvdRepo;

    @Inject
    private IReaderRepository readerRepo;

    @Inject
    private IAuthorRepository authorRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public ItemController(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    public Result getAll() {
        List<Item> itemList = new ArrayList<>();
        List<DVD> dvds = dvdRepo.findAll();
        List<Book> items = bookRepo.findAll();
        itemList.addAll(dvds);
        itemList.addAll(items);
        return ok(Json.toJson(itemList));
    }


    public Result saveDvd() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }
        dvdRepo.save(Json.fromJson(json, DVD.class));
        return ok("insert dvdRepo success");
    }

    public Result saveBook() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Book deserializedBook = Json.fromJson(json, Book.class);

        Reader reader = deserializedBook.getCurrentReader();
        Author author = deserializedBook.getAuthor();

        Key<Reader> returnedReader = readerRepo.save(reader);
        deserializedBook.getCurrentReader().setId(new ObjectId(String.valueOf(returnedReader.getId())));

        Key<Author> returnedAuthor = authorRepo.save(author);
        deserializedBook.getAuthor().setId(new ObjectId(String.valueOf(returnedAuthor.getId())));

        Key<Book> returned = bookRepo.save(deserializedBook);
        return ok(String.valueOf(returned.getId()));
    }

}
