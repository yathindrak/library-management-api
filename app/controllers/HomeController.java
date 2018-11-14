package controllers;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import com.google.inject.Inject;
import repository.IBookRepository;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private IBookRepository item;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public HomeController(HttpExecutionContext ec) {
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


//    public CompletionStage<Result> index() {
//        // Use a different task with explicit EC
//        return calculateResponse().thenApplyAsync(answer -> {
//            // uses Http.Context
////            ctx().flash().put("info", "Response updated!");
//            return ok("answer was " + answer);
//        }, httpExecutionContext.current());
//    }
//
//    private CompletableFuture<Key<Item>> calculateResponse() {
//        Item it = new Book();
//        it.setName("iu");
//        ((Book) it).setAuthor("hh");
//        Key<Item> i =  item.save(it);
//        return CompletableFuture.completedFuture(i);
//    }

}
