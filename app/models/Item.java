package models;

import org.mongodb.morphia.annotations.Id;

public abstract class Item {
    @Id
    private String id;
    private String isbn;
    private String title;
    private String sector;
    private DateTime publicationDate;
    private DateTime borrowedDate;
    private Reader currentReader;
    private Publisher publisher;

    // constructor
    public Item(String id, String isbn, String title, String sector, DateTime publicationDate,
                DateTime borrowedDate, Reader currentReader,Publisher publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
        this.publicationDate = publicationDate;
        this.borrowedDate = borrowedDate;
        this.currentReader = currentReader;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public DateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public DateTime getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(DateTime borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Reader getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(Reader currentReader) {
        this.currentReader = currentReader;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
