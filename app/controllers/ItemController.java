package controllers;

import com.google.inject.Inject;
import models.Book;
import org.mongodb.morphia.Key;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.IItemRepository;
import play.libs.Json;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ItemController  extends Controller {

    @Inject
    private IItemRepository item;

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
        List<Book> items = item.findAll();
        return ok(Json.toJson(items));
    }

    private CompletableFuture<List<Book>> retrieveItemsResponse() {
        List<Book> items =  item.findAll();
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

}
