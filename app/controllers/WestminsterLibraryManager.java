package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.*;
import org.mongodb.morphia.Key;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.*;
import play.libs.Json;
import utils.DateTime;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

public class WestminsterLibraryManager extends Controller {

    @Inject
    private IBookRepository bookRepo;

    @Inject
    private IDVDRepository dvdRepo;

    @Inject
    private IReaderRepository readerRepo;

    @Inject
    private IAuthorRepository authorRepo;

    @Inject
    private IActorRepository actorRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public WestminsterLibraryManager(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    public Result getAll() {
        List<Item> itemList = new ArrayList<>();
        List<DVD> dvds = dvdRepo.findAll();
        List<Book> books = bookRepo.findAll();
        itemList.addAll(dvds);
        itemList.addAll(books);
        return ok(Json.toJson(itemList));
    }


    public Result saveDvd() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        DVD deserializedDVD = Json.fromJson(json, DVD.class);

        // get reader by id
        Reader reader = new Reader();

        List<Actor> actors = deserializedDVD.getActors();

        for (int i=0; i< actors.size(); i++) {
            actorRepo.save(actors.get(i));
        }

        deserializedDVD.setCurrentReader(reader);

        dvdRepo.save(deserializedDVD);
        return ok("insert dvdRepo success");
    }

    public Result saveBook() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Book deserializedBook = Json.fromJson(json, Book.class);

        // get reader by id
        Reader reader = new Reader();

        List<Author> authors = deserializedBook.getAuthor();

        for (int i=0; i< authors.size(); i++) {
            authorRepo.save(authors.get(i));
        }
        deserializedBook.setCurrentReader(reader);

        Key<Book> returned = bookRepo.save(deserializedBook);
        return ok(String.valueOf(returned.getId()));
    }

    public Result borrowBook(String id, String date, String borrower) {
        if(id == null || date == null || borrower == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        String[] dateArr = date.split("-");

        // find id by object id of the book
        Book book = bookRepo.findById(id);

        book.setBorrowedDate(new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2])));

        DateTime dateTime = new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
        book.getCurrentReader().setName(borrower);

        // get reader by id
        Reader reader = readerRepo.findById(borrower);

        if (reader == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        bookRepo.updateBorrowing(id, book, dateTime, reader);
        return ok(Json.toJson(book));
    }

    public Result borrowDvd(String id, String date, String borrower) {
        if(id == null || date == null || borrower == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        String[] dateArr = date.split("-");

        // find id by object id of the book
        DVD dvd = dvdRepo.findById(id);

        dvd.setBorrowedDate(new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2])));

        DateTime dateTime = new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));

        // get reader by id
        Reader reader = readerRepo.findById(borrower);

        if (reader == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }

        dvdRepo.updateBorrowing(id, dvd, dateTime, reader);
        return ok(Json.toJson(dvd));
    }

    public Result returnBook(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean isSth = bookRepo.updateReturning(id);
        System.out.println(isSth);
        return ok(Json.toJson("Success returning the book"));
    }

    public Result returnDvd(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean isSth = dvdRepo.updateReturning(id);
        System.out.println(isSth);
        return ok(Json.toJson("Success returning the dvd"));
    }

    public Result deleteBook(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean result = bookRepo.delete(id);
        if (result) {
            return ok(Json.toJson("Success"));
        } else {
            return badRequest(Response.generateResponse("Invalid data provided", false));
        }
    }

    public Result deleteDvd(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean result = dvdRepo.delete(id);
        if (result) {
            return ok(Json.toJson("Success"));
        } else {
            return badRequest(Response.generateResponse("Invalid data provided", false));
        }
    }
}
