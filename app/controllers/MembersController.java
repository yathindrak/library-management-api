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
import utils.Response;
import java.util.List;

public class MembersController extends Controller {

    @Inject
    private IReaderRepository readerRepo;

    private HttpExecutionContext httpExecutionContext;

    @Inject
    public MembersController(HttpExecutionContext ec) {
        this.httpExecutionContext = ec;
    }

    public Result getAll() {
        List<Reader> members = readerRepo.findAll();
        return ok(Json.toJson(members));
    }

    public Result saveMember() {
        JsonNode json = request().body().asJson();
        if(json == null){
            return badRequest(Response.generateResponse("Expecting Json data", false));
        }

        Reader member = Json.fromJson(json, Reader.class);

        Key<Reader> returnedReader = readerRepo.save(member);

        return ok(Json.toJson(returnedReader));
    }
}
