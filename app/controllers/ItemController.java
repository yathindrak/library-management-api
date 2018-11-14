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
import utils.DateTime;
import utils.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ItemController  extends Controller {

    @Inject
    private IBookRepository book;

    @Inject
    private IDVDRepository dvd;

    @Inject
    private IReaderRepository reader;

    @Inject
    private IAuthorRepository authorRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public ItemController(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    public Result getAll() {
        List<Item> itemList = new ArrayList<>();
        List<DVD> dvds = dvd.findAll();
        List<Book> items = book.findAll();
        itemList.addAll(dvds);
        itemList.addAll(items);
        return ok(Json.toJson(itemList));
    }


    public Result saveDvd() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }
        dvd.save(Json.fromJson(json, DVD.class));
        return ok("insert dvd success");
    }

    public Result saveBook() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Book decerializedBook = Json.fromJson(json, Book.class);

        Reader reader2 = decerializedBook.getCurrentReader();
        Author author2 = decerializedBook.getAuthor();

        Key<Reader> returnedReader = reader.save(reader2);
        System.out.println("Reader: "+returnedReader.getId());
        decerializedBook.getCurrentReader().setId(new ObjectId(String.valueOf(returnedReader.getId())));

        Key<Author> returnedAuthor = authorRepo.save(author2);
        System.out.println("Author: "+returnedAuthor.getId());
        decerializedBook.getAuthor().setId(new ObjectId(String.valueOf(returnedAuthor.getId())));

        Key<Book> returned = book.save(decerializedBook);
        return ok(String.valueOf(returned.getId()));
    }

}
