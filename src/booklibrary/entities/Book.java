package booklibrary.entities;

import java.util.List;

public class Book {
    private int id_book;
    private String ISBN;
    private List<Integer> list_id_author;
    private String title;
    private int year;
    private String date_upload;
    private int count_available;

    public Book() {
    }

    public Book(String ISBN, List<Integer> list_id_author, String title, int year, String date_upload, int count_available) {
        this.ISBN = ISBN;
        this.list_id_author = list_id_author;
        this.title = title;
        this.year = year;
        this.date_upload = date_upload;
        this.count_available = count_available;
    }

    public Book(int id_book, String ISBN, List<Integer> list_id_author, String title, int year, String date_upload, int count_available) {
        this.id_book = id_book;
        this.ISBN = ISBN;
        this.list_id_author = list_id_author;
        this.title = title;
        this.year = year;
        this.date_upload = date_upload;
        this.count_available = count_available;
    }


    public int getId() {
        return id_book;
    }

    public void setId(int id_book) {
        this.id_book = id_book;
    }

    public List<Integer> getList_id_author() {
        return list_id_author;
    }

    public void setList_id_author(List<Integer> list_id_author) {
        this.list_id_author = list_id_author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(String date_upload) {
        this.date_upload = date_upload;
    }

    public int getCount_available() {
        return count_available;
    }

    public void setCount_available(int count_available) {
        this.count_available = count_available;
    }

    @Override
    public String toString() {
        return String.format("Book[id_book=%s, isbn=%s, id author=%s, title=%s, year=%s, date upload=%s, count available=%s]", id_book, ISBN, list_id_author.toString(), title, year, date_upload, count_available);
    }

    public String getFields() {
        return String.format("ISBN='%s', title='%s', year=%s, date_upload='%s', count_available=%s", ISBN, title, year, date_upload, count_available);
    }

    public String getStringListIdAuthor() {
        String temp = "";
        for (Integer id : list_id_author) {
            temp += id + " ";
        }
        return temp;
    }
}

//CREATE TABLE Books (id_book INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ISBN CHAR(25), title CHAR(120), year INT, date_upload CHAR(10), count_available INT);
