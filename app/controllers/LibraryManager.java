package controllers;

import play.mvc.Result;

/**
 * Describes all controller methods of the library managing api
 */
public interface LibraryManager {
    Result getAll();
    Result saveDvd();
    Result saveBook();
    Result borrowBook(String id, String date, String time, String borrower);
    Result borrowDvd(String id, String date, String time, String borrower);
    Result returnBook(String id);
    Result returnDvd(String id);
    Result deleteBook(String id);
    Result deleteDvd(String id);
    Result getAllMembers();
    Result saveMember();
    Result getAllReservations();
    Result saveReservation();
}
