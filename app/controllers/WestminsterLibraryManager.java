package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.ISBNAlreadyExistsException;
import models.*;
import org.mongodb.morphia.Key;
import play.Logger;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.*;
import play.libs.Json;
import utils.DateTime;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

public class WestminsterLibraryManager extends Controller implements LibraryManager{

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

    @Inject
    private IReservationRepository reservationRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public WestminsterLibraryManager(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    @Override
    public Result getAll() {
        List<Item> itemList = new ArrayList<>();
        List<DVD> dvds = dvdRepo.findAll();
        List<Book> books = bookRepo.findAll();
        itemList.addAll(dvds);
        itemList.addAll(books);
        if (itemList != null)
            Logger.info("Got all items");
        return ok(Json.toJson(itemList));
    }

    @Override
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

        Key<DVD> returned = null;
        try {
            returned = dvdRepo.save(deserializedDVD);
        } catch (ISBNAlreadyExistsException e) {
            return badRequest(Response.generateResponse(e.getMessage(), false));
        }

        Logger.info("success saving dvd");
        return ok("success saving dvd");
    }

    @Override
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

        Key<Book> returned = null;
        try {
            returned = bookRepo.save(deserializedBook);
        } catch (ISBNAlreadyExistsException e) {
            return badRequest(Response.generateResponse(e.getMessage(), false));
        }
        if (returned.getId() != null)
            Logger.info("success saving book");

        return ok(String.valueOf("success saving book"));
    }

    @Override
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

        Reader reader = null;
        try {
            // get reader by id
            reader = readerRepo.findById(borrower);
        }
        catch (Exception e) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        System.out.println(reader.getEmail());
        if (reader.getEmail() == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        bookRepo.updateBorrowing(id, book, dateTime, reader);
        if (book != null)
            Logger.info("success borrowing book");
        return ok(Json.toJson(book));
    }

    @Override
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

        Reader reader = null;
        try {
            // get reader by id
            reader = readerRepo.findById(borrower);
        }
        catch (Exception e) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        if (reader == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }

        dvdRepo.updateBorrowing(id, dvd, dateTime, reader);
        if(dvd != null)
            Logger.info("success borrowing dvd");

        return ok(Json.toJson(dvd));
    }

    @Override
    public Result returnBook(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean isSth = bookRepo.updateReturning(id);
        if(isSth) {
            Logger.info("success returning book");
        }
        return ok(Json.toJson("Success returning the book"));
    }

    @Override
    public Result returnDvd(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean isSth = dvdRepo.updateReturning(id);
        if(isSth) {
            Logger.info("success returning dvd");
        }
        return ok(Json.toJson("Success returning the dvd"));
    }

    @Override
    public Result deleteBook(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean result = bookRepo.delete(id);
        if (result) {
            Logger.info("success deleting book");
            return ok(Json.toJson("success deleting book"));
        } else {
            return badRequest(Response.generateResponse("Invalid data provided", false));
        }
    }

    @Override
    public Result deleteDvd(String id) {
        if(id == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        boolean result = dvdRepo.delete(id);
        if (result) {
            Logger.info("success deleting dvd");
            return ok(Json.toJson("success deleting dvd"));
        } else {
            return badRequest(Response.generateResponse("Invalid data provided", false));
        }
    }

    @Override
    public Result getAllMembers() {
        List<Reader> members = readerRepo.findAll();
        if (members != null)
            Logger.info("Got all members");

        return ok(Json.toJson(members));
    }

    @Override
    public Result saveMember() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Reader member = Json.fromJson(json, Reader.class);

        Key<Reader> returnedReader = readerRepo.save(member);

        if (returnedReader != null)
            Logger.info("saved the member");

        return ok(Json.toJson(returnedReader));
    }

    @Override
    public Result getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        if (reservations != null)
            Logger.info("Got all reservations");

        return ok(Json.toJson(reservations));
    }

    @Override
    public Result saveReservation() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Reservation reservation = Json.fromJson(json, Reservation.class);

        if (reservation.getReservedReader().getId() == null)  {
            return badRequest(Response.generateResponse("Invalid user ID ", false));
        }

        Reader reader = readerRepo.findById(reservation.getReservedReader().getId());

        reservation.setReservedReader(reader);

        Key<Reservation> returnedReservation = reservationRepo.save(reservation);

        if (returnedReservation != null)
            Logger.info("saved the reservation");

        return ok(Json.toJson(returnedReservation));
    }
}
