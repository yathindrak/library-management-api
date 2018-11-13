package controllers;

import com.google.inject.Inject;
import models.Book;
import models.DVD;
import models.Item;
import org.mongodb.morphia.Key;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.IDVDRepository;
import repository.IItemRepository;
import play.libs.Json;
import utils.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ItemController  extends Controller {

    @Inject
    private IItemRepository item;

    @Inject
    private IDVDRepository dvd;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public ItemController(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

//    public CompletionStage<Result> index() {
//        // Use a different task with explicit EC
//        return saveBook().thenApplyAsync()
//    }
//
//    private CompletionStage<String> saveBook() {
//        return CompletableFuture.completedFuture(item.save(new Book(null, "awesome item", "awesome author")));
//    }
//    public Result index() {
////        List<Item> items =  item.findAll();
////        return ok(Json.toJson(items));
//        item.save(new Book(null, "awesome item", "awesome author"));
//        return ok("insert item success");
//    }

    public Result getAll() {
        // Use a different task with explicit EC
//        return retrieveItemsResponse().thenApplyAsync(items -> {
//            // uses Http.Context
////            ctx().flash().put("info", "Response updated!");
//            return ok(String.valueOf(items));
//        }, httpExecutionContext.current());

        DateTime dateTime = new DateTime(2010,2,8);
        DateTime dateTime1 = new DateTime(2010,2,9);

        System.out.println(dateTime.compareTo(dateTime1));
        System.out.println(dateTime);

        List<Item> itemList = new ArrayList<>();
//        retrieveItemsResponse().thenApplyAsync(answer -> {
//            List<Book> books = answer;
//            itemList.addAll(books);
//            return retrieveDvdsResponse().thenApplyAsync(ans -> {
//                List<DVD> dvds = ans;
//                itemList.addAll(dvds);
//                return ok(Json.toJson(ans));
//            }, httpExecutionContext.current());
//        }, httpExecutionContext.current());



        List<DVD> dvdz = dvd.findAll();
        List<Book> items = item.findAll();
        itemList.addAll(dvdz);
        itemList.addAll(items);
        return ok(Json.toJson(itemList));
    }

    private CompletableFuture<List<Book>> retrieveItemsResponse() {
        List<Book> items =  item.findAll();
//        Key<Item> i =  item.save(new Book(null, "awesome item", "awesome author"));
        return CompletableFuture.completedFuture(items);
    }

    private CompletableFuture<List<DVD>> retrieveDvdsResponse() {
        List<DVD> items =  dvd.findAll();
//        Key<Item> i =  item.save(new Book(null, "awesome item", "awesome author"));
        return CompletableFuture.completedFuture(items);
    }

    public CompletionStage<Result> save() {
        // Use a different task with explicit EC
        return retrieveSaveResponse().thenApplyAsync(answer -> {
            // uses Http.Context
//            ctx().flash().put("info", "Response updated!");
            return ok(String.valueOf(answer));
        }, httpExecutionContext.current());
    }

    private CompletableFuture<Key<Book>> retrieveSaveResponse() {
        Book it = new Book(null, "ui", "i");
        Key<Book> i =  item.save(it);
        return CompletableFuture.completedFuture(i);
    }

    public CompletionStage<Result> saveDvd() {
        // Use a different task with explicit EC
        return retrieveDSaveResponse().thenApplyAsync(answer -> {
            // uses Http.Context
//            ctx().flash().put("info", "Response updated!");
            return ok(String.valueOf(answer));
        }, httpExecutionContext.current());
    }

    private CompletableFuture<Key<DVD>> retrieveDSaveResponse() {
        DVD it = new DVD(null, "hsdi", "idsf");
        Key<DVD> i =  dvd.save(it);
        return CompletableFuture.completedFuture(i);
    }

}
