package main.booklibrary.model;

import java.util.List;

public class Author {

    private int id_author;
    private String first_name;
    private String last_name;
    private List<Book> books;

    public int getId() {
        return id_author;
    }

    public void setId(int id_author) {
        this.id_author = id_author;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return String.format("Author[id_author=%s, Name=%s %s, books=%s]", id_author, first_name, last_name, books);
    }
}
//CREATE TABLE Authors (id_author INT NOT NULL PRIMARY KEY AUTO_INCREMENT, first_name CHAR(20), last_name CHAR(20));
//CREATE TABLE BooksOfAuthor (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, id_author INT NOT NULL, id_book INT NOT NULL);