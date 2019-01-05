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
import models.DateTime;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller in the library management rest api
 */
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

    @Inject
    private IPublisherRepository publisherRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public WestminsterLibraryManager(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    /**
     * Get all items
     * @return Result of List<Item>
     */
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

    /**
     * Save a DVD
     * @return a result
     */
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

        publisherRepo.save(deserializedDVD.getPublisher());

        Logger.info("success saving dvd");
        return ok("success saving dvd");
    }

    /**
     * Save a book
     * @return a result
     */
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

        publisherRepo.save(deserializedBook.getPublisher());

        return ok(String.valueOf("success saving book"));
    }

    /**
     * Borrow a book
     * @param id
     * @param date
     * @param borrower
     * @return borrowed book as a Result
     */
    @Override
    public Result borrowBook(String id, String date, String time, String borrower) {
        if(id == null || date == null || borrower == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        String[] dateArr = date.split("-");
        String[] timeArr = time.split(":");

        // find id by object id of the book
        Book book = bookRepo.findById(id);

        book.setBorrowedDate(new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]),
                Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                Integer.parseInt(timeArr[2])
                ));

        DateTime dateTime = new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]),
                Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                Integer.parseInt(timeArr[2])
        );

        Reader reader = null;
        try {
            // get reader by id
            reader = readerRepo.findById(borrower);
        }
        catch (Exception e) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        if (reader.getId() == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        bookRepo.updateBorrowing(id, book, dateTime, reader);
        if (book != null)
            Logger.info("success borrowing book");
        return ok(Json.toJson(book));
    }

    /**
     * Borrow a DVD
     * @param id - id
     * @param date - date
     * @param borrower - borrower
     * @return DVD borrowed
     */
    @Override
    public Result borrowDvd(String id, String date, String time, String borrower) {
        if(id == null || date == null || borrower == null){
            return badRequest(Response.generateResponse("Expecting required data", false));
        }

        System.out.println(date);

        String[] dateArr = date.split("-");
        String[] timeArr = time.split(":");

        // find id by object id of the book
        DVD dvd = dvdRepo.findById(id);

        dvd.setBorrowedDate(new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]),
                Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                Integer.parseInt(timeArr[2])
        ));

        DateTime dateTime = new DateTime(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]),
                Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                Integer.parseInt(timeArr[2])
        );

        Reader reader = null;
        try {
            // get reader by id
            reader = readerRepo.findById(borrower);
        }
        catch (Exception e) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        if (reader.getId() == null) {
            return badRequest(Response.generateResponse("Invalid reader id", false));
        }
        dvdRepo.updateBorrowing(id, dvd, dateTime, reader);
        if(dvd != null)
            Logger.info("success borrowing dvd");

        return ok(Json.toJson(dvd));
    }

    /**
     * Return book
     * @param id - id
     * @return a result
     */
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

    /**
     * Return DVD
     * @param id
     * @return a result
     */
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

    /**
     * Delete book
     * @param id
     * @return a result
     */
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

    /**
     * Delete DVD
     * @param id
     * @return a result
     */
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

    /**
     * Get all members
     * @return a result
     */
    @Override
    public Result getAllMembers() {
        List<Reader> members = readerRepo.findAll();
        if (members != null)
            Logger.info("Got all members");

        return ok(Json.toJson(members));
    }

    /**
     * Save a member
     * @return saved member as a Result
     */
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

    /**
     * Get all reservations
     * @return List<Reservation>
     */
    @Override
    public Result getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        if (reservations != null)
            Logger.info("Got all reservations");

        return ok(Json.toJson(reservations));
    }

    /**
     * Get all reservations by isbn
     * @param isbn - isbn
     * @return List<Reservation>
     */
    @Override
    public Result getAllReservationsByIsbn(String isbn) {
        List<Reservation> reservations = reservationRepo.findByIsbn(isbn);
        if (reservations != null)
            Logger.info("Got all reservations by isbn" + isbn);

        return ok(Json.toJson(reservations));
    }

    /**
     * Save a reservation
     * @return saved reservation as Result
     */
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

        if (reader.getId() == null)  {
            return badRequest(Response.generateResponse("Invalid user ID ", false));
        }

        reservation.setReservedReader(reader);

        Key<Reservation> returnedReservation = reservationRepo.save(reservation);

        if (returnedReservation != null) {
            Logger.info("saved the reservation");
        } else {
            return internalServerError(Response.generateResponse("Error occurred ", false));        }

        return ok(Json.toJson(reservation));
    }
}
